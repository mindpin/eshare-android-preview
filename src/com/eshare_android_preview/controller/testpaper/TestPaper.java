package com.eshare_android_preview.controller.testpaper;

import android.os.Parcel;
import android.os.Parcelable;

import com.eshare_android_preview.http.api.TestSuccessHttpApi;
import com.eshare_android_preview.http.c.UserData;
import com.eshare_android_preview.http.i.knowledge.ICanbeLearned;
import com.eshare_android_preview.http.i.knowledge.IUserKnowledgeNet;
import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.http.i.question.IQuestionLoader;
import com.eshare_android_preview.http.model.LearnedItem;
import com.eshare_android_preview.http.model.TestSuccess;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fushang318 on 13-12-6.
 */
public class TestPaper implements Parcelable {
    // TODO 1 改成单例

    public IQuestionLoader loader;
    public TestResult test_result;
    public List<IQuestion> questions;
    private int current_index = 0;

    public TestPaper(IQuestionLoader loader){
        this.loader = loader;
        this.test_result = new TestResult(3, 10);
    }

    public IQuestion get_next_question(){
        if (null == questions) get_questions_from_http();

        IQuestion question = questions.get(current_index);
        current_index += 1;
        return question;
    }

    private void get_questions_from_http() {
        questions = loader.get_questions_remote();
        current_index = 0;
    }

    public TestPaper(Parcel parcel){
        String loader_id   = parcel.readString();
        String loader_class_name = parcel.readString();
        this.loader = UserData.instance().get_current_knowledge_net(false).get_iquestion_loader(loader_class_name, loader_id);
        this.test_result = (TestResult)parcel.readSerializable();

        List<IQuestion> temp = new ArrayList<IQuestion>();
        parcel.readList(temp, null);
        if(temp.size() != 0){
            questions = temp;
        }

        this.current_index = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.loader.get_id());
        dest.writeString(this.loader.get_type());
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

    // 练习通过，发起请求
    public TestSuccess do_pass() {
        IUserKnowledgeNet net = UserData.instance().get_current_knowledge_net(false);
        try {
            TestSuccess test_success = TestSuccessHttpApi.build_test_success(net.get_id(), loader.get_id());
            for(LearnedItem item : test_success.learned_items){
                ICanbeLearned target = net.find_learn_target(item.id, item.type);
                target.set_learned();
            }
            return test_success;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
