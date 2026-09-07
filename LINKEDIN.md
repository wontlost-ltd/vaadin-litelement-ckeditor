# LinkedIn 文案 — CKEditor Builder

LinkedIn 不支持 Markdown：反引号、`**粗体**`、三反引号代码块都会原样显示。
下面的正文已按纯文本写就，可直接整段复制粘贴。

建议配 10–15 秒录屏（走一遍向导：选预设 → 勾插件 → 导出 Java 代码），
或直接截「预览与导出」那一屏 —— 生成的代码本身就是最有说服力的画面。

---

## 正文（直接复制以下内容）

Configuring CKEditor 5 in a Vaadin app means picking from 70+ plugins, getting the toolbar order right, and matching plugin versions that must agree exactly. Most people do it by copy-pasting a config from somewhere and editing until it stops throwing.

So we built the thing that should have existed: a visual configurator that hands you working Java.

CKEditor Builder — free, no signup:
https://ckeditor-builder.wontlost.com

Seven steps: editor type, plugins, toolbar layout, styling and language, then preview and export. Start from a preset (Minimal / Standard / Full) or configure every plugin yourself. It exports Java, TypeScript, or JSON.

The output is code you paste into your project — not a config file you then have to figure out how to load.

Four live editor demos on the same site, each a working configuration rather than a screenshot:

• Document editor — the conventional one
• AI-assisted editor
• Collaborative editor — real-time, multiple cursors
• Notion-style editor — slash commands and block editing

It's built with the vaadin-ckeditor add-on, which is itself open source (Apache 2.0) and on Maven Central:

    com.wontlost:ckeditor-vaadin:5.4.0

70+ free plugins, premium plugin support, Lumo theme integration including dark mode, upload handling, HTML sanitisation, autosave, and 10+ languages.

Source: https://github.com/wontlost-ltd/vaadin-ckeditor

If you're integrating a rich text editor into Vaadin, start at the builder. It'll save you an afternoon.

———

在 Vaadin 应用里配置 CKEditor 5，意味着要从 70 多个插件里挑选、把工具栏顺序排对、还要保证几个必须严格一致的插件版本对得上。多数人的做法是从某处复制一份配置，然后一路改到不报错为止。

所以我们做了那个本该存在的东西：一个可视化配置器，直接给你能用的 Java 代码。

CKEditor Builder —— 免费，无需注册：
https://ckeditor-builder.wontlost.com

七个步骤：编辑器类型、插件选择、工具栏布局、样式与语言，最后预览并导出。可以从预设开始（精简 / 标准 / 完整），也可以逐项自定义。支持导出 Java、TypeScript 或 JSON。

导出的是可以直接粘进项目的代码，而不是一份还得研究怎么加载的配置文件。

站点上还有四个可直接操作的编辑器示例，都是真实可用的配置，不是截图：

• 文档编辑器 —— 常规形态
• AI 辅助编辑器
• 协同编辑器 —— 实时协作、多光标
• Notion 风格编辑器 —— 斜杠命令与块级编辑

它基于 vaadin-ckeditor add-on 构建，后者本身开源（Apache 2.0）并已发布至 Maven Central：

    com.wontlost:ckeditor-vaadin:5.4.0

70+ 免费插件、premium 插件支持、与 Lumo 主题联动（含深色模式）、上传处理、HTML 净化、自动保存，以及 10 多种语言。

源码：https://github.com/wontlost-ltd/vaadin-ckeditor

如果你正要在 Vaadin 里集成富文本编辑器，先去 builder 走一遍，能省下一个下午。

#Vaadin #Java #CKEditor #OpenSource #WebDevelopment

---

## 更短的版本

在 Vaadin 里配 CKEditor 5：70 多个插件、工具栏顺序、必须严格一致的版本号。多数人的做法是复制一份配置改到不报错为止。

我们做了个可视化配置器，七步走完直接导出 Java 代码 —— 不是配置文件，是能直接粘进项目的代码。

免费，无需注册：https://ckeditor-builder.wontlost.com

站点上还有四个真实可操作的编辑器示例：常规文档、AI 辅助、实时协同、Notion 风格。

基于开源的 vaadin-ckeditor（Apache 2.0，Maven Central: com.wontlost:ckeditor-vaadin:5.4.0）。

#Vaadin #Java #CKEditor #OpenSource

---

## 排版说明

- LinkedIn 折叠在约前 3 行。第一段刻意描述「痛」而不是先讲产品 ——
  读到「copy-pasting a config until it stops throwing」会心的人，正是目标受众。
- Maven 坐标用 4 空格缩进，与正文分离。LinkedIn 不等宽渲染，但缩进仍有效果。
- 结尾那句「省下一个下午」是刻意的具体化 —— 比「提升效率」更可信。
- 中英之间用「———」分隔。

## 事实核对

正文中的说法均已核实：
- 七步向导、三种预设 —— 取自线上站点实际文案
- 三种导出格式 —— 源码中 JavaCodeGenerator / TypeScriptCodeGenerator / JsonConfigGenerator
- 四个编辑器示例 —— 逐个访问确认返回 200
- 版本号 5.4.0 —— 对应已发布到 Maven Central 的 add-on
