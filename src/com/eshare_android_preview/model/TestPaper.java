package com.eshare_android_preview.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.eshare_android_preview.model.knowledge.KnowledgeCheckpoint;
import com.eshare_android_preview.model.knowledge.KnowledgeNet;
import com.eshare_android_preview.model.knowledge.KnowledgeNode;
import com.eshare_android_preview.model.knowledge.KnowledgeSet;
import com.eshare_android_preview.model.knowledge.base.TestPaperTarget;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fushang318 on 13-12-6.
 */
public class TestPaper implements Parcelable {
    public TestPaperTarget target;
    public TestResult test_result;
    private ArrayList<Integer> expect_ids = new ArrayList<Integer>();

    public TestPaper(TestPaperTarget target, TestResult test_result){
        this.target = target;
        this.test_result = test_result;
    }

    public Question get_next_question(){
        Question question = this.target.get_random_question(expect_ids);
        expect_ids.add(question.id);
        return question;
    }

    public static TestPaperTarget find(KnowledgeNet net, String class_name, String id){
        if (KnowledgeNode.class.getName().equals(class_name)) {
            return net.find_node_by_id(id);
        } else if (KnowledgeCheckpoint.class.getName().equals(class_name)) {
            return net.find_checkpoint_by_id(id);
        }
        return null;
    }

    public TestPaper(Parcel parcel){
        String model = parcel.readString();
        String model_id = parcel.readString();
        this.target = find(KnowledgeNet.get_current_net(), model,model_id);
        this.test_result = (TestResult)parcel.readSerializable();
        this.expect_ids = (ArrayList<Integer>)parcel.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(target.model());
        dest.writeString(target.model_id());
        dest.writeSerializable(test_result);
        dest.writeSerializable(expect_ids);
    }

    public static final Parcelable.Creator<TestPaper> CREATOR = new Creator<TestPaper>() {
        @Override
        public TestPaper createFromParcel(Parcel source) {
            return new TestPaper(source);
        }

        @Override
        public TestPaper[] newArray(int size) {
            return new TestPaper[size];
        }
    };

}
