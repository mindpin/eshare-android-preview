package com.eshare_android_preview.model;


import java.io.Serializable;

public class Favourates implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public int favourate_id;
    public String kind;

    public Favourates(int favourate_id, String kind) {
        super();
        this.favourate_id = favourate_id;
        this.kind = kind;
    }
    public Favourates(int id, int favourate_id, String kind) {
        super();
        this.id = id;
        this.favourate_id = favourate_id;
        this.kind = kind;
    }


}