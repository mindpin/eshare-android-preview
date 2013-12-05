package com.eshare_android_preview.model.elog;

import com.eshare_android_preview.model.database.ExperienceLogDBHelper;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.eshare_android_preview.model.knowledge.base.BaseKnowledge;

import java.io.Serializable;
import java.util.List;

/**
 * Created by menxu on 13-12-5.
 */
public class ExperienceLog implements Serializable {
    public static int[] level_up_exp_nums = {10,15,23};

    public int id;
    public int before_exp;
    public int after_exp;
    public String model_type;
    public String model_id;
    public String data_json;
    public long   created_at;

    public BaseKnowledge model;

    public void setModel() {
        BaseKnowledge base_knowlege = null;
        if (this.model_type == KnowledgeSet.class.getName()){
            base_knowlege = KnowledgeSet.find(this.model_id);
        }else if (this.model_type == KnowledgeNode.class.getName()){
            base_knowlege = KnowledgeNode.find(this.model_id);
        }else if (this.model_type == KnowledgeCheckpoint.class.getName()){
            base_knowlege = KnowledgeCheckpoint.find(this.model_id);
        }
        this.model = base_knowlege;
    }

    public ExperienceLog(int id, int before_exp, int after_exp, String model_type, String model_id, String data_json, long created_at) {
        this.id = id;
        this.before_exp = before_exp;
        this.after_exp = after_exp;
        this.model_type = model_type;
        this.model_id = model_id;
        this.data_json = data_json;
        this.created_at = created_at;
        this.setModel();
    }


    public static void add(int delta_num,BaseKnowledge model,String data_json){
        int before_exp = ExperienceLogDBHelper.find_last_data().after_exp;
        int after_exp = before_exp + delta_num;
        String model_type = model.model();
        String model_id = model.model_id();
        long created_at = System.currentTimeMillis();
        ExperienceLog elog = new ExperienceLog(-1,before_exp,after_exp,model_type,model_id,data_json,created_at);
        ExperienceLogDBHelper.create(elog);
    }

    public static List<ExperienceLog> all(){
        return ExperienceLogDBHelper.all();
    }


    public static CurrentState current_state(){
        CurrentState state = new CurrentState();
        state.init_data();
        return state;
    }
}
