package com.eshare_android_preview.model.database.base;

abstract public class BaseModel {
    private boolean is_model_nil = false;

    final public boolean is_nil() {
        return is_model_nil;
    }

    final public void set_nil() {
        this.is_model_nil = true;
    }
}