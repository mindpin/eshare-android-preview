package com.eshare_android_preview.base.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by kaid on 11/11/13.
 */
public class HtmlCodeParser {
    private String html;

    private HtmlCodeParser(String html) {
        this.html = html;
    }

    static public HtmlCodeParser fromString(String html) {
        return new HtmlCodeParser(html);
    }

    public String output() throws IOException {
        Document doc = Jsoup.parse(html);
        Elements pres = doc.select("pre");
        for (Element pre : pres) {
            Element code = pre.children().get(0);

            pre.addClass("language-" + code.className());
            code.removeClass(code.className());
            pre.addClass("line-numbers");
        }
        return doc.html();
    }
}
