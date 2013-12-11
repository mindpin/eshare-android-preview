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
           css("codefill.css") +
           css("hl.css") +
          "<style>" +
            "code {" +
              "font-family:\"Courier New\", monospace !important;" +
              "overflow:visible;" +
              "background:none !important;" +
              "padding:0 0 0 0 !important;" +
              "word-wrap:break-word;" +
            "}" +
          "</style>" +
          // Weinre 远程调试器
          //"<script src=\"http://192.168.1.9:8080/target/target-script-min.js#anonymous\"></script>" +
        "</head>" +
        "<body>" +
           htmlSnippet +
           js("hl.js") +
           "<script>hljs.initHighlightingOnLoad();</script>" +
           js("codefill.js") +
        "</body>" +
        "</html>";
    }
}
