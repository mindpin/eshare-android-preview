# eshare-android-preview

## 搭建开发环境

### 升级 Android Studio 到最新版本
Android Studio 最新版本对 Gradle 的支持逐渐完善，所以建议升级最新版本

选择 Help -> Check for Update 检查更新并升级

### 安装 Gradle 1.10

本工程需要 Gradle 1.10 版本(低于该版本不能正常工作): [下载地址](http://services.gradle.org/distributions/gradle-1.10-bin.zip)
下载解压到任意目录

### 把工程导入 Android Studio
请使用 File -> Import Project 载入工程

如果提示配置 Gradle，请把 Gradle 指定为上一步解压的 Gradle 1.10

### 增加 http_site.xml

文件的位置是 app\src\main\res\values\http_site.xml

内容如下

```
<resources>
    <string name="http_site">http://4ye-evaluation.mindpin.com</string>
</resources>
```

这样就搭建完毕了