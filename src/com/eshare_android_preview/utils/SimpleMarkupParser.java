package com.eshare_android_preview.utils;

import java.util.regex.Pattern;

/**
 * Created by kaid on 11/21/13.
 */
public class SimpleMarkupParser {
    private String string;
    final static private String image_pattern = "!\\[(\\S*)\\]";
    final static private String image_replace = "<img src=\"$1\">";
    final static private String code_pattern  = "```(\\w*)(.*)```";
    final static private String code_replace  = "<pre><code class=\"$1\">$2</code></pre>";

    private SimpleMarkupParser(String string) {
        this.string = string;
    }

    public static SimpleMarkupParser from_string(String string) {
        return new SimpleMarkupParser(string);
    }

    private String process(String input, String pattern, String replace) {
        return Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL).matcher(input).replaceAll(replace);
    }

    public String output() {
        String output = string;
        output = process(output, image_pattern, image_replace);
        output = process(output, code_pattern, code_replace);
        return output;
    }
}
