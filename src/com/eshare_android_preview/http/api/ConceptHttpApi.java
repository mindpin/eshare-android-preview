package com.eshare_android_preview.http.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.eshare_android_preview.http.base.EshareGetRequest;
import com.eshare_android_preview.http.i.concept.IConcept;
import com.eshare_android_preview.http.model.Concept;

public class ConceptHttpApi {
	public static List<IConcept> net_concepts(String net_id,boolean unlocked, boolean learned) {
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
	
	private static String set_concepts_url(String net_id, String set_id) {
        return String.format("/api/knowledge_nets/%s/knowledge_sets/%s/concepts", net_id, set_id);
    }
	
	private static String node_concepts_url(String net_id, String node_id) {
        return String.format("/api/knowledge_nets/%s/knowledge_nodes/%s/concepts", net_id, node_id);
    }
}
