package com.eshare_android_preview.base.utils;

import android.util.Log;

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

    public String output() {
        return
        "<!DOCTYPE html>" +
        "<html>" +
        "<head>" +
          css("prism.css") +
          "<style>" +
            "code {font-family:\"Courier New\", monospace !important;}" +
          "</style>" +
        "</head>" +
        "<body>" +
          htmlSnippet +
          js("prism.js") +
        "</body>" +
        "</html>";
    }
}
