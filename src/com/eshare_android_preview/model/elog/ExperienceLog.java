package com.eshare_android_preview.model.elog;

import java.io.Serializable;
import java.util.List;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.model.DayExpInfo;
import com.eshare_android_preview.model.database.ExperienceLogDBHelper;
import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.base.TestPaperTarget;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by menxu on 13-12-5.
 */
public class ExperienceLog implements Serializable {
	public final static String COURSE = "javascript";
	
    public static int[] level_up_exp_nums = {10,15,23};

    public int id;
    public int before_exp;
    public int after_exp;
    public String model_type;
    public String model_id;
    public String data_json;
    public long   created_at;
    public String course;

    public TestPaperTarget model;

    public void setModel() {
        TestPaperTarget base_knowlege = null;
        KnowledgeNet net = KnowledgeNet.find_by_name(this.course);
        if (this.model_type == KnowledgeNode.class.getName()){
            base_knowlege = net.find_node_by_id(this.model_id);
        }else if (this.model_type == KnowledgeCheckpoint.class.getName()){
            base_knowlege = net.find_checkpoint_by_id(this.model_id);
        }
        this.model = base_knowlege;
    }

    public ExperienceLog(int id, int before_exp, int after_exp, String model_type, String model_id, String data_json, long created_at, String course) {
        this.id = id;
        this.before_exp = before_exp;
        this.after_exp = after_exp;
        this.model_type = model_type;
        this.model_id = model_id;
        this.data_json = data_json;
        this.created_at = created_at;
        this.course = course;
        this.setModel();
    }
    
    public static void add(int delta_num,TestPaperTarget model,String data_json){
    	add(COURSE,delta_num,model,data_json);
    }
    
    public static void add(String course,int delta_num,TestPaperTarget model,String data_json){

        ExperienceLog after_exp_elog = ExperienceLogDBHelper.find_last_data(course);
        int before_exp = after_exp_elog == null ? 0:after_exp_elog.after_exp;

        int after_exp = before_exp + delta_num;
        String model_type = model.model();
        String model_id = model.model_id();
        long created_at = System.currentTimeMillis();
        ExperienceLog elog = new ExperienceLog(-1,before_exp,after_exp,model_type,model_id,data_json,created_at,course);
        ExperienceLogDBHelper.create(elog);
    }
    
    public static List<ExperienceLog> all(){
    	return all(COURSE);
    }
    
    public static List<ExperienceLog> all(String coruse){
        return ExperienceLogDBHelper.all(coruse);
    }
    
    public static int get_level_up_exp_num_by(int level){
    	if (level >= level_up_exp_nums.length -1) {
			level = 1;
		}
    	return level_up_exp_nums[level-1];
    }

    public static CurrentState current_state(){
        return current_state(COURSE);
    }
    public static CurrentState current_state(String course){
        CurrentState state = new CurrentState(course);
        state.init_data();
        return state;
    }

    public static int get_exp_num_by_day_by_dev(Calendar c){
        return (int)(Math.random() * 100);
    }

    public static int get_exp_num_by_day(Calendar c){
        return get_exp_num_by_day(COURSE,c);
    }
    
    public static int get_exp_num_by_day(String course,Calendar c){
    	int count= 0;
    	for (ExperienceLog elog : ExperienceLogDBHelper.all(course)) {
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
