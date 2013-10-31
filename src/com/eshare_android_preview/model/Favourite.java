package com.eshare_android_preview.model;


import java.io.Serializable;

public class Favourite implements Serializable {
    private static final long serialVersionUID = 3112128L;
    public int id;
    public String favourite_id;
    public String kind;



    public Favourite(String favourite_id, String kind) {
        super();
        this.favourite_id = favourite_id;
        this.kind = kind;
    }
    public Favourite(int id, String favourite_id, String kind) {
        super();
        this.id = id;
        this.favourite_id = favourite_id;
        this.kind = kind;
    }


}