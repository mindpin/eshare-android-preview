package com.eshare_android_preview.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.eshare_android_preview.http.api.QuestionHttpApi;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.model.Question;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 13-12-6.
 */
public class TestPaper implements Parcelable {
    public String node_or_checkpoint_id;
    public TestResult test_result;
    public List<Question> questions;
    private int current_index = 0;

    public TestPaper(String node_or_checkpoint_id, TestResult test_result){
        this.node_or_checkpoint_id = node_or_checkpoint_id;
        this.test_result = test_result;
    }

    public Question get_next_question(){
        if(null == questions){
            try {
                String net_id = UserData.instance().get_current_knowledge_net_id();
                questions = QuestionHttpApi.get_random_questions(net_id, this.node_or_checkpoint_id);
                current_index = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Question question = questions.get(current_index);
        current_index += 1;
        return question;
    }

    public TestPaper(Parcel parcel){
        this.node_or_checkpoint_id = parcel.readString();
        this.test_result = (TestResult)parcel.readSerializable();
        parcel.readList(this.questions, null);
        this.current_index = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.node_or_checkpoint_id);
        dest.writeSerializable(this.test_result);
        dest.writeList(this.questions);
        dest.writeInt(this.current_index);
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
