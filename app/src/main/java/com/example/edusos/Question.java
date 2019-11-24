package com.example.edusos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Question implements Parcelable {
    String subject;
    String question;
    ArrayList<String> topics;
    ArrayList<String> answer;

    public Question() {}

    public Question(String subject, String question, ArrayList<String> topics, ArrayList<String> answer) {
        this.subject = subject;
        this.question = question;
        this.topics = topics;
        this.answer = answer;
    }

    public Question(Parcel source) {
        subject = source.readString();
        question = source.readString();
        topics = source.readArrayList(String.class.getClassLoader());
        answer = source.readArrayList(String.class.getClassLoader());
    }

    public String getSubject() {
        return this.subject;
    }

    public String getQuestion() {
        return this.question;
    }

    public ArrayList<String> getTopics() {
        return this.topics;
    }

    public ArrayList<String> getAnswer() {
        return this.answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subject);
        dest.writeString(this.question);
        dest.writeList(this.topics);
        dest.writeList(this.answer);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }

    };

}
