package com.eshare_android_preview.model.elog;

import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.DayExpInfo;
import com.eshare_android_preview.model.database.ExperienceLogDBHelper;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.eshare_android_preview.model.knowledge.base.TestPaperTarget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

    public TestPaperTarget model;

    public void setModel() {
        TestPaperTarget base_knowlege = null;
        if (this.model_type == KnowledgeNode.class.getName()){
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


    public static void add(int delta_num,TestPaperTarget model,String data_json){

        ExperienceLog after_exp_elog = ExperienceLogDBHelper.find_last_data();
        int before_exp = after_exp_elog == null ? 0:after_exp_elog.after_exp;

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

    public static int get_exp_num_by_day_by_dev(Calendar c){
        return (int)(Math.random() * 100);
    }

    public static int get_exp_num_by_day(Calendar c){
//        TODO by menxu
//        需要返回 c 这一天当中获取的经验总数
    	int count= 0;
    	for (ExperienceLog elog : ExperienceLogDBHelper.all()) {
    		if (BaseUtils.date_all_string(elog.created_at).equals(BaseUtils.date_all_string(c.getTime()))) {
    			count += elog.after_exp - elog.before_exp; 
			}
		}
        return count;
    }

    public static List<DayExpInfo> history_info(){
        ArrayList<DayExpInfo> result = new ArrayList<DayExpInfo>();

        for(int i=-4; i<=0; i++){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i);
            result.add(new DayExpInfo(c));
        }

        return result;
    }
}
