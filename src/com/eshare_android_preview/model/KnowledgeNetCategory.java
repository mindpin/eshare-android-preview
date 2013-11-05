package com.eshare_android_preview.model;

import com.eshare_android_preview.R;

import java.util.ArrayList;
import java.util.List;

/**
* Created by fushang318 on 13-11-4.
*/
public class KnowledgeNetCategory {
    public String name;
    public int res_id;

    public KnowledgeNetCategory(String name, int res_id) {
        this.name = name;
        this.res_id = res_id;
    }

    public static List<KnowledgeNetCategory> all(){
        List<KnowledgeNetCategory> categorys = new ArrayList<KnowledgeNetCategory>();
        KnowledgeNetCategory[] cates = {
            new KnowledgeNetCategory("Javascript", R.drawable.lan_js),
            new KnowledgeNetCategory("Rails",      R.drawable.lan_rails),
            new KnowledgeNetCategory("HTML5",      R.drawable.lan_html5),
            new KnowledgeNetCategory("MongoDB",    R.drawable.lan_mongodb),
            new KnowledgeNetCategory("C++",        R.drawable.lan_cpp),
            new KnowledgeNetCategory("PHP",        R.drawable.lan_php),
            new KnowledgeNetCategory(".NET",       R.drawable.lan_dotnet),
            new KnowledgeNetCategory("Sinatra",    R.drawable.lan_sinatra)
        };

        for (int i = 0; i < cates.length; i++) {
            categorys.add(cates[i]);
        }
        return categorys;
    }
}
