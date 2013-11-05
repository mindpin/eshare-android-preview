package com.eshare_android_preview.model;

import com.eshare_android_preview.model.database.FavouriteDBHelper;
import com.eshare_android_preview.model.database.NoteDBHelper;
import com.eshare_android_preview.model.interfaces.ILearningResource;

/**
 * Created by fushang318 on 13-11-4.
 */
public abstract class LearningResource implements ILearningResource {
    private Boolean has_note;
    private Boolean is_faved;

    public Note build_note(String content, byte[] bytes){
        return new Note(this.getClass().getName(), content, bytes, this);
    }

    @Override
    public boolean add_to_fav(){
        Favourite fav = new Favourite(get_note_foreign_key_id(), this.getClass().getName());
        return fav.save();
    }

    @Override
    public boolean remove_from_fav(){
        Favourite favourite = Favourite.find_by_id_and_kind(get_note_foreign_key_id(), this.getClass().getName());
        if(favourite != null){
            return favourite.destroy();
        }
        return true;
    }

    @Override
    public boolean has_note() {
        if(this.has_note == null){
            this.has_note = NoteDBHelper.has_note_from(get_note_foreign_key_id(), this.getClass().getName());
        }
        return this.has_note;
    }

    @Override
    public boolean is_faved() {
        if(this.is_faved == null){
            this.is_faved = FavouriteDBHelper.find(get_note_foreign_key_id(), this.getClass().getName()) != null;
        }
        return this.is_faved;
    }

    public abstract String get_note_foreign_key_id();
}
