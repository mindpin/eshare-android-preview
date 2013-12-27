package com.eshare_android_preview.model.base;


import java.io.Serializable;
import java.util.Date;

public class Constants {
    public static final String DATABASE_NAME = "eshare";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";
    
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
