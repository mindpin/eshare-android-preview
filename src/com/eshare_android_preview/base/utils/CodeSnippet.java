package com.eshare_android_preview.base.utils;

/**
 * Created by kaid on 11/8/13.
 */
public class CodeSnippet {
    private String codeSnippet;
    private Boolean lineNumbers;
    private String language;

    private CodeSnippet(String snippet) {
        this.codeSnippet = snippet;
    }

    static public CodeSnippet fromString(String snippet) {
        return new CodeSnippet(snippet);
    }

    public CodeSnippet withLanguage(String language) {
        this.language = language;
        return this;
    }

    public CodeSnippet withLineNumbers() {
        this.lineNumbers = true;
        return this;
    }

    public String output() {
        String lineNumberCls = lineNumbers ? "line-numbers" : "";
        String languageCls = language.equals(null) ?  "" : "language-" + language;
        return
        "<pre class=\"" + lineNumberCls + " " + languageCls + "\">" +
           "<code>" +
              codeSnippet +
           "</code>" +
        "</pre>";
    }
}
