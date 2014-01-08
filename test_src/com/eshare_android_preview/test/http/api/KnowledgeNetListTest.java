package com.eshare_android_preview.test.http.api;

import android.test.AndroidTestCase;
import com.eshare_android_preview.http.api.KnowledgeNetHttpApi;
import com.eshare_android_preview.http.model.KnowledgeNet;
import junit.framework.Assert;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNetListTest extends AndroidTestCase{
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    public void test_1(){
        List<KnowledgeNet> net_list = null;
        try {
            net_list = KnowledgeNetHttpApi.net_list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        KnowledgeNet net = net_list.get(0);
        Assert.assertEquals("javascript", net.id);
        Assert.assertEquals("javascript", net.name);
    }
}
