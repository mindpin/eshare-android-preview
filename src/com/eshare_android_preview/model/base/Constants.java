package com.eshare_android_preview.model.base;



public class Constants {
    public static final String DATABASE_NAME = "eshare";
    public static final int DATABASE_VERSION = 3;
    public static final String KEY_ID = "_id";
    
    // 账号 数据表的常量
    public static final String TABLE_ACCOUNT_USERS = "account_users";
    public static final String TABLE_ACCOUNT_USERS__USER_ID = "user_id";
    public static final String TABLE_ACCOUNT_USERS__NAME = "name";
    public static final String TABLE_ACCOUNT_USERS__LOGIN = "login";
    public static final String TABLE_ACCOUNT_USERS__EMAIL = "email";
    public static final String TABLE_ACCOUNT_USERS__AVATAR = "avatar";
    public static final String TABLE_ACCOUNT_USERS__COOKIES = "cookies";
    public static final String TABLE_ACCOUNT_USERS__INFO = "info";
    
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
