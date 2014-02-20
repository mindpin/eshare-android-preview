package com.eshare_android_preview.test.http.api;

import android.test.AndroidTestCase;
import com.eshare_android_preview.http.api.KnowledgeNetHttpApi;
import com.eshare_android_preview.http.api.UserAuthHttpApi;
import com.eshare_android_preview.http.logic.user_auth.AccountManager;
import com.eshare_android_preview.http.model.KnowledgeNet;
import com.eshare_android_preview.http.model.KnowledgeNode;
import com.eshare_android_preview.http.model.KnowledgeSet;
import com.eshare_android_preview.http.model.SimpleKnowledgeNet;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 14-1-8.
 */
public class KnowledgeNetTest extends AndroidTestCase{
    @Override
    public void setUp() throws Exception {
        super.setUp();
        try {
            UserAuthHttpApi.user_authenticate("admin@edu.dev","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(AccountManager.is_logged_in(), true);
    }

    public void test_1(){
        List<SimpleKnowledgeNet> net_list = null;
        try {
            net_list = KnowledgeNetHttpApi.net_list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleKnowledgeNet net = net_list.get(0);
        Assert.assertEquals("javascript", net.get_id());
        Assert.assertEquals("javascript", net.get_name());
    }

    public void test_2(){
        KnowledgeNet net = null;
        try {
            net = KnowledgeNetHttpApi.net("javascript");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1, net.children().size());

        KnowledgeSet set_8 = (KnowledgeSet)net.children().get(0);
        Assert.assertEquals("set-8", set_8.get_id()  );

        Assert.assertEquals(1, set_8.children().size());

        Assert.assertEquals(2, set_8.children().get(0).children().size());
    }

    public void test_3(){
        KnowledgeNet net = null;
        try {
            net = KnowledgeNetHttpApi.net("javascript");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<KnowledgeNode> nodes = new ArrayList<KnowledgeNode>();
        KnowledgeSet set_8 = (KnowledgeSet)net.children().get(0);
        try {
            nodes = KnowledgeNetHttpApi.set_nodes("javascript", set_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(5, nodes.size());
        Assert.assertEquals("node-31", nodes.get(0).get_id());
        Assert.assertEquals("字符串", nodes.get(0).get_name());
    }
}
