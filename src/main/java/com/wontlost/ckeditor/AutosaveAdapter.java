package com.wontlost.ckeditor;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

class AutosaveAdapter implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> tokenType) {
        final TypeAdapter<T> adapter = gson.getDelegateAdapter(this, tokenType);

        return new TypeAdapter<T>() {
            @Override
            public T read(JsonReader reader) throws IOException {
                return adapter.read(reader);
            }

            @Override
            public void write(JsonWriter writer, T value) throws IOException {
                JsonElement tree = adapter.toJsonTree(value);

                if (value instanceof Autosave) {
                    String dom = value.toString();
                    JsonObject jo = (JsonObject) tree;
                    jo.addProperty("autosave", dom );
                }
                gson.getAdapter(JsonElement.class).write(writer, tree);
            }
        };
    }
}
