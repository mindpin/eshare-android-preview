package com.eshare_android_preview.model;

import android.graphics.drawable.Drawable;

import com.eshare_android_preview.application.EshareApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kaid on 10/22/13.
 */
public class User implements Serializable {
    static ArrayList<User> users;
    static final String AVATAR_DIR = "images/avatars/";
    public String avatar_path;
    public String username;
    public String bio;

    public User(String username, String avatar) {
        this.username = username;
        this.avatar_path = AVATAR_DIR + avatar;
        this.bio = "大家好，我是" + username + "。";
    }

    public Drawable getAvatarDrawable() {
        Drawable avatar;
        try {
            InputStream stream = EshareApplication.context.getAssets().open(avatar_path);
            avatar = Drawable.createFromStream(stream, null);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return avatar;
    }

    private static void fetch() {
        users = new ArrayList<User>();
        JSONArray data;

        try {
            InputStream json =  EshareApplication.context.getAssets().open("users.json");
            byte[] buffer = new byte[json.available()];
            json.read(buffer);
            json.close();
            data = new JSONArray(new String(buffer, "UTF-8"));
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);

                User user = new User(obj.getString("name"), obj.getString("avatar"));
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> all() {
        if (!(users == null)) return users;
        fetch();
        return users;
    }
}
