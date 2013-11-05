package com.eshare_android_preview.model.interfaces;

import com.eshare_android_preview.model.Note;

/**
 * Created by fushang318 on 13-10-29.
 */
public interface ILearningResource {
    public boolean has_note();
    public boolean is_faved();

    public Note build_note(String content, byte[] bytes);

    public boolean add_to_fav();
    public boolean remove_from_fav();
}
