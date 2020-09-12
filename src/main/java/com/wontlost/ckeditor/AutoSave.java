package com.wontlost.ckeditor;

import java.util.function.Consumer;

public abstract class AutoSave implements Consumer<String> {

    public abstract void accept(String s);

}
