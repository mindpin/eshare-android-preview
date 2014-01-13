package com.eshare_android_preview.http.i.profile;

/**
 * Created by Administrator on 14-1-13.
 */
public interface IUserProfile {
    // 获取登录邮箱
    public String get_email();

    // 获取用户昵称
    public String get_name();

    // 获取用户头像
    public IUserAvatar get_avatar();
}
