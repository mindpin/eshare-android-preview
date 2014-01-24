package com.eshare_android_preview.test.model.knowledge;

import android.test.AndroidTestCase;

import com.eshare_android_preview.http.api.UserAuthHttpApi;

public class ConceptTest extends AndroidTestCase{
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void test_concepts(){
		try {
            UserAuthHttpApi.user_authenticate("admin@edu.dev","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
