package com.eshare_android_preview.model;


import com.eshare_android_preview.model.database.FavouriteDBHelper;

import java.io.Serializable;
import java.util.List;

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


    public static List<Favourite> all(){
        return FavouriteDBHelper.all();
    }

    public static Favourite find_by_id_and_kind(String favourite_id, String kind) {
        return FavouriteDBHelper.find(favourite_id, kind);
    }

    public boolean destroy(){
        FavouriteDBHelper.cancel(this);
        return true;
    }

    public boolean save(){
        FavouriteDBHelper.create(this);
        return true;
    }

    public boolean is_belong_question(){
        return is_belong(Question.class);
    }

    public boolean is_belong_course(){
        return is_belong(Course.class);
    }
    public boolean is_belong_knowledge_net_node(){
        return is_belong(KnowledgeNetNode.class);
    }

    private boolean is_belong(Class<?> clazz){
        try {
            return Class.forName(this.kind) == clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}