package com.wontlost.ckeditor.internal;

import com.wontlost.ckeditor.handler.UploadHandler;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Internal class for managing file uploads.
 * Handles upload queuing, progress tracking, and result callbacks with thread safety.
 *
 * <p>This class is an internal API and should not be used directly by external code.</p>
 */
public class UploadManager {

    private static final Logger logger = Logger.getLogger(UploadManager.class.getName());

    /**
     * Default upload timeout in seconds.
     * Backend timeout is set to 6 minutes, slightly longer than the frontend default of 5 minutes,
     * to avoid race conditions when both sides time out simultaneously.
     */
    private static final long DEFAULT_UPLOAD_TIMEOUT_SECONDS = 360; // 6 minutes

    /**
     * Upload task status
     */
    public enum UploadStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED,
        CANCELLED
    }

    /**
     * Upload task information
     */
    public static class UploadTask {
        private final String uploadId;
        private final String fileName;
        private final String mimeType;
        private final long fileSize;
        private final long startTime;
        private volatile UploadStatus status;
        private volatile String resultUrl;
        private volatile String errorMessage;
        /** Flag indicating whether the callback has been notified, to prevent double notification */
        private volatile boolean notified;

        UploadTask(String uploadId, String fileName, String mimeType, long fileSize) {
            this.uploadId = uploadId;
            this.fileName = fileName;
            this.mimeType = mimeType;
            this.fileSize = fileSize;
            this.startTime = System.currentTimeMillis();
            this.status = UploadStatus.PENDING;
            this.notified = false;
        }

        public String getUploadId() { return uploadId; }
        public String getFileName() { return fileName; }
        public String getMimeType() { return mimeType; }
        public long getFileSize() { return fileSize; }
        public long getStartTime() { return startTime; }
        public UploadStatus getStatus() { return status; }
        public String getResultUrl() { return resultUrl; }
        public String getErrorMessage() { return errorMessage; }
        public boolean isNotified() { return notified; }

        private volatile CompletableFuture<?> future;

        void setStatus(UploadStatus status) { this.status = status; }
        void setResultUrl(String url) { this.resultUrl = url; }
        void setErrorMessage(String error) { this.errorMessage = error; }
        void setNotified(boolean notified) { this.notified = notified; }
        void setFuture(CompletableFuture<?> future) { this.future = future; }
        CompletableFuture<?> getFuture() { return future; }

        /**
         * Get the elapsed upload time in milliseconds
         */
        public long getElapsedTimeMs() {
            return System.currentTimeMillis() - startTime;
        }
    }

    /**
     * Upload result callback interface
     */
    @FunctionalInterface
    public interface UploadResultCallback {
        /**
         * Callback when upload completes
         *
         * @param uploadId upload ID
         * @param url URL on success, null on failure
         * @param error error message on failure, null on success
         */
        void onComplete(String uploadId, String url, String error);
    }

    private final UploadHandler uploadHandler;
    private final UploadHandler.UploadConfig uploadConfig;
    private final UploadResultCallback resultCallback;
    private final Map<String, UploadTask> activeTasks = new ConcurrentHashMap<>();
    /**
     * 已通知的上传守卫集合，防止同一次上传重复回调。
     *
     * <p>元素类型有意为 {@code Object}：正常路径存 {@link UploadTask} 实例本身，
     * early-failure（尚未建立 task）路径退回存 {@code uploadId} 字符串。
     * 以 task 为键可按「代」隔离——uploadId 是前端每实例计数器，组件重挂载后会复用，
     * 两代上传可能同时在途，用裸 ID 会导致两代相互干扰。</p>
     */
    private final Set<Object> notifiedUploadIds = ConcurrentHashMap.newKeySet();
    private final long uploadTimeoutSeconds;

    /**
     * Create an upload manager with default timeout
     *
     * @param uploadHandler upload handler
     * @param uploadConfig upload configuration, uses default if null
     * @param resultCallback result callback
     */
    public UploadManager(UploadHandler uploadHandler,
                        UploadHandler.UploadConfig uploadConfig,
                        UploadResultCallback resultCallback) {
        this(uploadHandler, uploadConfig, resultCallback, DEFAULT_UPLOAD_TIMEOUT_SECONDS);
    }

    /**
     * Create an upload manager with custom timeout
     *
     * @param uploadHandler upload handler
     * @param uploadConfig upload configuration, uses default if null
     * @param resultCallback result callback
     * @param uploadTimeoutSeconds upload timeout in seconds, 0 means no timeout
     */
    public UploadManager(UploadHandler uploadHandler,
                        UploadHandler.UploadConfig uploadConfig,
                        UploadResultCallback resultCallback,
                        long uploadTimeoutSeconds) {
        this.uploadHandler = uploadHandler;
        this.uploadConfig = uploadConfig != null ? uploadConfig : new UploadHandler.UploadConfig();
        this.resultCallback = resultCallback;
        this.uploadTimeoutSeconds = uploadTimeoutSeconds > 0 ? uploadTimeoutSeconds : 0;
    }

    /**
     * Handle a file upload request
     *
     * @param uploadId upload identifier
     * @param fileName file name
     * @param mimeType MIME type
     * @param base64Data Base64-encoded file content
     */
    public void handleUpload(String uploadId, String fileName, String mimeType, String base64Data) {
        logger.log(Level.FINE, "Starting upload: id={0}, file={1}, type={2}, dataLength={3}",
            new Object[]{uploadId, fileName, mimeType, base64Data != null ? base64Data.length() : 0});

        if (uploadHandler == null) {
            logger.log(Level.WARNING, "Upload {0} rejected: no upload handler configured", uploadId);
            notifyError(uploadId, null, "No upload handler configured");
            return;
        }

        // 在 decode 前按 base64 长度估算解码后大小，超过上限直接拒绝，
        // 避免恶意超大 base64 先解码成巨大字节数组导致 OOM/DoS（review 发现）。
        // base64 每 4 字符编码 3 字节，估算 = length/4*3，留足余量。
        if (base64Data != null) {
            long estimatedSize = (long) base64Data.length() / 4 * 3;
            long maxFileSize = uploadConfig.getMaxFileSize();
            if (estimatedSize > maxFileSize) {
                logger.log(Level.INFO,
                    "Upload {0} rejected before decode: estimated {1} bytes exceeds max {2} bytes",
                    new Object[]{uploadId, estimatedSize, maxFileSize});
                notifyError(uploadId, null,
                    "File exceeds maximum allowed size of " + maxFileSize + " bytes");
                return;
            }
        }

        byte[] fileData;
        try {
            fileData = Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Upload {0} rejected: invalid base64 data - {1}",
                new Object[]{uploadId, e.getMessage()});
            notifyError(uploadId, null, "Invalid file data: " + e.getMessage());
            return;
        }

        long fileSize = fileData.length;
        logger.log(Level.FINE, "Upload {0} decoded: {1} bytes", new Object[]{uploadId, fileSize});

        UploadHandler.UploadContext context = new UploadHandler.UploadContext(fileName, mimeType, fileSize);

        // Validate upload
        String validationError = uploadConfig.validate(context);
        if (validationError != null) {
            logger.log(Level.INFO, "Upload {0} rejected by validation: {1}",
                new Object[]{uploadId, validationError});
            notifyError(uploadId, null, validationError);
            return;
        }

        // Create and track upload task
        UploadTask task = new UploadTask(uploadId, fileName, mimeType, fileSize);
        activeTasks.put(uploadId, task);
        task.setStatus(UploadStatus.IN_PROGRESS);
        logger.log(Level.FINE, "Upload {0} task created and tracking started", uploadId);

        // Process upload asynchronously (catch synchronous exceptions and null return values)
        CompletableFuture<UploadHandler.UploadResult> future;
        try {
            logger.log(Level.FINE, "Upload {0} invoking handler", uploadId);
            future = uploadHandler.handleUpload(context, new ByteArrayInputStream(fileData));
            if (future == null) {
                task.setStatus(UploadStatus.FAILED);
                task.setErrorMessage("Upload handler returned null");
                activeTasks.remove(uploadId, task);
                logger.log(Level.WARNING, "Upload {0} failed: handler returned null future", uploadId);
                notifyError(uploadId, task, "Upload handler returned null");
                return;
            }
        } catch (Exception e) {
            task.setStatus(UploadStatus.FAILED);
            String errorMsg = e.getMessage();
            if (errorMsg == null || errorMsg.isEmpty()) {
                errorMsg = e.getClass().getSimpleName() + " occurred during upload initialization";
            }
            task.setErrorMessage(errorMsg);
            activeTasks.remove(uploadId, task);
            logger.log(Level.WARNING, "Upload {0} failed: handler threw {1} - {2}",
                new Object[]{uploadId, e.getClass().getSimpleName(), errorMsg});
            notifyError(uploadId, task, errorMsg);
            return;
        }

        // Apply timeout if configured
        CompletableFuture<UploadHandler.UploadResult> timedFuture;
        if (uploadTimeoutSeconds > 0) {
            timedFuture = future.orTimeout(uploadTimeoutSeconds, TimeUnit.SECONDS);
        } else {
            timedFuture = future;
        }

        // Save Future reference to support cancellation
        task.setFuture(timedFuture);

        // Use handle instead of thenAccept + exceptionally to ensure a single processing path
        timedFuture.handle((result, ex) -> {
            // Synchronize task state updates
            synchronized (task) {
                processCompletion(uploadId, task, result, ex);
            }
            return null;
        });
    }

    /**
     * 处理上传 Future 完成后的状态更新与回调通知（在 task 锁内调用）。
     *
     * 通过卫语句提前返回，将原本嵌套的成功/失败/重复分支拍平为单层分发。
     */
    private void processCompletion(String uploadId, UploadTask task,
                                   UploadHandler.UploadResult result, Throwable ex) {
        // 重复通知防护：已通知或已取消时直接返回
        if (task.isNotified()) {
            logger.log(Level.FINE, "Upload {0} already notified, skipping duplicate callback", uploadId);
            activeTasks.remove(uploadId, task);
            return;
        }

        if (task.getStatus() == UploadStatus.CANCELLED) {
            logger.log(Level.FINE, "Upload {0} was cancelled, ignoring result", uploadId);
            // 取消路径同样是本次上传的终态：与 activeTasks 一起释放去重标记，
            // 避免该 uploadId 永久占位（见文末 retireUpload 的说明）。
            retireUpload(uploadId, task);
            return;
        }

        long elapsedMs = task.getElapsedTimeMs();

        if (ex != null) {
            String errorMsg = resolveFailureMessage(uploadId, ex, elapsedMs);
            task.setStatus(UploadStatus.FAILED);
            task.setErrorMessage(errorMsg);
            notifyResult(uploadId, task, null, errorMsg);
        } else if (result != null && result.isSuccess()) {
            task.setStatus(UploadStatus.COMPLETED);
            task.setResultUrl(result.getUrl());
            logger.log(Level.INFO, "Upload {0} completed successfully in {1}ms, url={2}",
                new Object[]{uploadId, elapsedMs, result.getUrl()});
            notifyResult(uploadId, task, result.getUrl(), null);
        } else {
            String errorMsg = result != null ? result.getErrorMessage() : "Unknown upload error";
            task.setStatus(UploadStatus.FAILED);
            task.setErrorMessage(errorMsg);
            logger.log(Level.WARNING, "Upload {0} failed after {1}ms: {2}",
                new Object[]{uploadId, elapsedMs, errorMsg});
            notifyResult(uploadId, task, null, errorMsg);
        }

        retireUpload(uploadId, task);
    }

    /**
     * 释放一次上传占用的全部登记信息（活跃任务 + 去重标记）。
     *
     * <p>notifiedUploadIds 只用于防止「同一次上传被通知两次」。一旦这次上传到达终态
     * （成功 / 失败 / 取消），其 uploadId 就不应再占位，否则：
     * <ol>
     *   <li>集合只增不减，长会话下持续泄漏内存；</li>
     *   <li>前端 uploadId 形如 {@code upload-<editorId>-<每实例计数器>}，组件重挂载后
     *       计数器归零、ID 会复用；此时旧标记仍在，新上传会被误判为重复通知而直接
     *       跳过回调，表现为文件已存到服务端、前端却永远转圈。</li>
     * </ol>
     *
     * <p>释放时机安全性：调用点均在通知已经发出之后（或该次上传已被判定为取消而
     * 不再通知），因此不会削弱「恰好通知一次」的保证——同一 uploadId 的竞争双方
     * 中必有一方先 add 成功并完成通知，另一方在 add 失败后直接返回。
     */
    private void retireUpload(String uploadId, UploadTask task) {
        // 用两参数 remove：只有当映射仍指向「本次」task 时才删除。
        // 前端 uploadId 是每实例计数器，组件重挂载后会复用同一个 ID；
        // 若旧任务结束时无条件 remove(uploadId)，会把刚登记的新任务一并删掉，
        // 造成新上传丢失登记（后续无法取消、状态查询失效）。
        if (task != null) {
            activeTasks.remove(uploadId, task);
        } else {
            activeTasks.remove(uploadId);
        }

        // 释放该 task 的去重标记。
        // 守卫键是 task 实例本身（见 notifyResult），因此移除只影响「这一代」，
        // 不会误伤同 ID 的其它代——这正是改用 task 作键换来的好处：
        // 释放时机不再需要「该 ID 上是否还有活跃任务」这类非原子判断。
        // 注意集合持有的是强引用，不释放就会一直累积（且泄漏的是整个 UploadTask），
        // 所以这一步是必需的，不能依赖 GC。
        if (task != null) {
            notifiedUploadIds.remove(task);
        }
        // task == null 的 early-failure 路径不在此释放：
        // UploadManagerTest#earlyFailureNotifiesExactlyOnce 要求同一 uploadId 的连续
        // early failure 只通知一次；这类键由 cleanup() 统一清理，属异常路径、量级有限。
    }

    /**
     * 从异常推导失败信息并记录日志。
     * 解包 CompletionException 取根因；TimeoutException 给出超时文案，其余回退到异常消息或类名。
     */
    private String resolveFailureMessage(String uploadId, Throwable ex, long elapsedMs) {
        Throwable cause = (ex instanceof CompletionException && ex.getCause() != null)
            ? ex.getCause() : ex;

        if (cause instanceof TimeoutException) {
            logger.log(Level.WARNING, "Upload {0} timed out after {1}ms", new Object[]{uploadId, elapsedMs});
            return "Upload timed out after " + uploadTimeoutSeconds + " seconds";
        }

        String errorMsg = cause.getMessage();
        if (errorMsg == null || errorMsg.isEmpty()) {
            errorMsg = cause.getClass().getSimpleName() + " occurred during upload";
        }
        logger.log(Level.WARNING, "Upload {0} failed after {1}ms: {2}",
            new Object[]{uploadId, elapsedMs, errorMsg});
        return errorMsg;
    }

    /**
     * Cancel an upload task
     *
     * @param uploadId upload ID
     * @return whether the cancellation succeeded
     */
    public boolean cancelUpload(String uploadId) {
        UploadTask task = activeTasks.get(uploadId);
        if (task == null) {
            logger.log(Level.FINE, "Cancel request for unknown upload: {0}", uploadId);
            return false;
        }

        synchronized (task) {
            // Double notification guard: if already notified, do not cancel
            if (task.isNotified()) {
                logger.log(Level.FINE, "Upload {0} already notified, cancel ignored", uploadId);
                return false;
            }

            if (task.getStatus() == UploadStatus.IN_PROGRESS ||
                task.getStatus() == UploadStatus.PENDING) {
                task.setStatus(UploadStatus.CANCELLED);
                task.setErrorMessage("Upload cancelled");

                // Attempt to cancel the underlying Future
                CompletableFuture<?> future = task.getFuture();
                if (future != null) {
                    future.cancel(true);
                }

                logger.log(Level.FINE, "Upload {0} cancelled after {1}ms",
                    new Object[]{uploadId, task.getElapsedTimeMs()});
                notifyResult(uploadId, task, null, "Upload cancelled");
                retireUpload(uploadId, task);
                return true;
            }

            logger.log(Level.FINE, "Upload {0} cannot be cancelled in status {1}",
                new Object[]{uploadId, task.getStatus()});
        }
        return false;
    }

    /**
     * Get the number of active uploads
     *
     * @return active upload task count
     */
    public int getActiveUploadCount() {
        return activeTasks.size();
    }

    /**
     * Check whether there are any active uploads
     *
     * @return true if there are active uploads
     */
    public boolean hasActiveUploads() {
        return !activeTasks.isEmpty();
    }

    /**
     * Get upload task status
     *
     * @param uploadId upload ID
     * @return upload task, or null if not found
     */
    public UploadTask getUploadTask(String uploadId) {
        return activeTasks.get(uploadId);
    }

    /**
     * Clean up all pending upload tasks
     */
    public void cleanup() {
        // Iterate over a snapshot to avoid ConcurrentModificationException,
        // since cancelUpload() removes entries from activeTasks
        for (String uploadId : new java.util.ArrayList<>(activeTasks.keySet())) {
            cancelUpload(uploadId);
        }
        activeTasks.clear();
        notifiedUploadIds.clear();
    }

    /**
     * Notify upload error (for early failures where the task may not exist)
     */
    private void notifyError(String uploadId, UploadTask task, String error) {
        logger.log(Level.WARNING, "Upload failed for {0}: {1}", new Object[]{uploadId, error});
        notifyResult(uploadId, task, null, error);

        // 有 task 的失败路径（handler 返回 null / 同步抛异常）：本次上传到此终结，
        // 必须释放以 task 为键的守卫，否则整个 UploadTask 会被守卫集合强引用而泄漏。
        // 释放只影响这一代，不会误伤同 ID 的其它代。
        if (task != null) {
            retireUpload(uploadId, task);
        }
        // task == null 的 early-failure 路径**不**释放：
        // UploadManagerTest#earlyFailureNotifiesExactlyOnce 要求同一 uploadId
        // 连续两次 early failure 只通知一次；此时守卫键是字符串 ID，
        // 释放就会破坏该契约。这类键由 cleanup() 统一清理，
        // 且仅为字符串、属异常路径，量级有限。
    }

    /**
     * Notify upload result with double notification guard
     */
    private void notifyResult(String uploadId, UploadTask task, String url, String error) {
        // Double notification guard：add() 原子地「首次插入返回 true」，是唯一的闸门。
        //
        // 守卫键的选择很关键（review 两轮均指向此处）：
        // - 有 task 时用 **task 实例本身** 作键。uploadId 是前端的每实例计数器，
        //   组件重挂载后会复用，两代上传可能同时在途；若用裸 ID 作键，先结束的一代
        //   释放标记就会误伤另一代，而不释放又会让后一代被永久拦截。
        //   以 task 为键则天然按「代」隔离，且随 task 一起被回收，不需要显式释放。
        // - task == null（early-failure，尚未建立 task）时退回用 uploadId 作键，
        //   以维持「同一 ID 的连续 early failure 只通知一次」的既有契约；
        //   这类键由 cleanup() 统一清理，属异常路径、量级有限。
        Object notifiedKey = task != null ? task : uploadId;
        if (!notifiedUploadIds.add(notifiedKey)) {
            logger.log(Level.FINE, "Skipping duplicate notification for upload {0}", uploadId);
            return;
        }
        if (task != null) {
            synchronized (task) {
                task.setNotified(true);
            }
        }

        if (resultCallback != null) {
            try {
                resultCallback.onComplete(uploadId, url, error);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error in upload result callback for " + uploadId, e);
            }
        }
    }
}
