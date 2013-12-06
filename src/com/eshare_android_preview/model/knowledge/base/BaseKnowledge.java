package com.eshare_android_preview.model.knowledge.base;

import com.eshare_android_preview.model.TestPaper;

/**
 * Created by menxu on 13-12-5.
 */
public interface BaseKnowledge {
    public String model();
    public String model_id();
    public TestPaper get_test_paper();
}
