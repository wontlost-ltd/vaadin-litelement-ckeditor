package com.wontlost.ckeditor;

import com.google.gson.annotations.Expose;

class Autosave {

    int waitingTime;

    @Expose(serialize = false, deserialize = false)
    String name;

    @Expose(serialize = false, deserialize = false)
    String arguments;

    @Expose(serialize = false, deserialize = false)
    String body;

    Autosave(int waitingTime, String name, String arguments, String body) {
        this.waitingTime = waitingTime;
        this.name = name;
        this.arguments = arguments;
        this.body = body;
    }

    public String toString() {
        return "{ waitingTime:" + waitingTime + "," +
                  name +" ( "+arguments+" ) { " + body + " } "+
                "}";
    }
    
}
