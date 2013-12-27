package com.eshare_android_preview.model.base;


import java.io.Serializable;
import java.util.Date;

public class Constants {
    public static final String DATABASE_NAME = "eshare";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";
    
    // notes
    public static final String TABLE_NOTES = "notes";
    public static final String TABLE_NOTES__TYPE = "type";
    public static final String TABLE_NOTES__TYPE_ID = "type_id";
    public static final String TABLE_NOTES__CONTENT = "content";
    public static final String TABLE_NOTES__IMG = "img";

    // plans
    public static final String TABLE_COURSE = "courese";
    public static final String TABLE_COURSE__CONTENT = "content";
    public static final String TABLE_COURSE__CHECKED = "checked";

    // favourites
    public static final String TABLE_FAVOURITES = "favourites";
    public static final String TABLE_FAVOURITES__ID = "favourite_id";
    public static final String TABLE_FAVOURITES__KIND = "kind";

    // experience_logs
    public static final String TABLE_EXPERIENCE_LOGS = "experience_logs";
    public static final String TABLE_EXPERIENCE_LOGS__BEFORE_EXP = "before_exp";
    public static final String TABLE_EXPERIENCE_LOGS__AFTER_EXP = "after_exp";
    public static final String TABLE_EXPERIENCE_LOGS__MODEL_TYPE = "model_type";
    public static final String TABLE_EXPERIENCE_LOGS__MODEL_ID = "model_id";
    public static final String TABLE_EXPERIENCE_LOGS__DATA_JSON = "data_json";
    public static final String TABLE_EXPERIENCE_LOGS_CREATED_AT = "created_at";
    public static final String TABLE_EXPERIENCE_LOGS_COURSE = "course";
}
