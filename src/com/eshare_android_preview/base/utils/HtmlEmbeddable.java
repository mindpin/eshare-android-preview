package com.eshare_android_preview.base.utils;

import java.io.IOException;

/**
 * Created by kaid on 11/8/13.
 */
public class HtmlEmbeddable {
    private String htmlSnippet;

    private HtmlEmbeddable(String htmlString) {
        htmlSnippet = htmlString;
    }

    static public HtmlEmbeddable fromString(String str) {
        return new HtmlEmbeddable(str);
    }

    private String css(String cssFile) {
        return "<link href=\"file:///android_asset/css/" + cssFile + "\" rel=\"stylesheet\" />";
    }

    private String js(String jsFile) {
        return "<script src=\"file:///android_asset/js/" + jsFile + "\"></script>";
    }

    public String output() throws IOException {
        return
        "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
           css("coderay_twilight.css") +
          // Weinre 远程调试器
          //"<script src=\"http://192.168.1.9:8080/target/target-script-min.js#anonymous\"></script>" +
        "</head>" +
        "<body>" +
           "<div id='content'>" +
                htmlSnippet +
           "</div>" +
           js("codefill.js") +
        "</body>" +
        "</html>";
    }
}
