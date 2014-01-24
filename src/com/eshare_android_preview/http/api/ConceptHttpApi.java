package com.eshare_android_preview.http.api;

import com.eshare_android_preview.http.base.EshareGetRequest;
import com.eshare_android_preview.http.i.concept.IConcept;
import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.http.model.Concept;
import com.eshare_android_preview.http.model.Question;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ConceptHttpApi {
	public static List<IConcept> net_concepts(String net_id,Boolean unlocked, Boolean learned) {
		try {
			return new EshareGetRequest<List<IConcept>>(
					"/api/knowledge_nets/" + net_id + "/concepts",
					new BasicNameValuePair("is_learned", learned+""),
					new BasicNameValuePair("is_unlocked", unlocked+"")
			) {
				@Override
				public List<IConcept> on_success(String response_text)throws Exception {
					return Concept.from_json_array(response_text);
				}
			}.go();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IConcept>();
		}
	}

	public static List<IConcept> set_concepts(String net_id, String set_id) {
		try {
			return new EshareGetRequest<List<IConcept>>(set_concepts_url(net_id,set_id)) {
				@Override
				public List<IConcept> on_success(String response_text)throws Exception {
					return Concept.from_json_array(response_text);
				}
			}.go();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IConcept>();
		}
	}

	public static List<IConcept> node_concepts(String net_id, String node_id) {
		try {
			return new EshareGetRequest<List<IConcept>>(node_concepts_url(net_id,node_id)) {
				@Override
				public List<IConcept> on_success(String response_text)throws Exception {
					return Concept.from_json_array(response_text);
				}
			}.go();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IConcept>();
		}
	}

    public static List<IQuestion> get_learned_node_random_questions(String concept_id, int count){
        String url = String.format("/api/concept/%s/learned_node_random_questions?count=%d", concept_id, count);
        try {
            return new EshareGetRequest<List<IQuestion>>(url) {
                @Override
                public List<IQuestion> on_success(String response_text) throws Exception {
                    return new ArrayList<IQuestion>(Question.from_json_array(response_text));
                }
            }.go();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private static String set_concepts_url(String net_id, String set_id) {
        return String.format("/api/knowledge_nets/%s/knowledge_sets/%s/concepts", net_id, set_id);
    }
	
	private static String node_concepts_url(String net_id, String node_id) {
        return String.format("/api/knowledge_nets/%s/knowledge_nodes/%s/concepts", net_id, node_id);
    }
}
