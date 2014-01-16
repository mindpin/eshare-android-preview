package com.eshare_android_preview.controller.testpaper;

import android.os.Parcel;
import android.os.Parcelable;

import com.eshare_android_preview.http.i.question.IQuestion;
import com.eshare_android_preview.http.i.question.IQuestionLoader;
import com.eshare_android_preview.http.model.TestSuccess;

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
        this.loader = (IQuestionLoader)parcel.readSerializable();
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
        dest.writeSerializable(this.loader);
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
        // TODO
        // 1 先实现通过的请求
        // 2 再实现修改变化了的对象
        return null;
    }
}
