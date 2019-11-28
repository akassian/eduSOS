package com.example.edusos;

import java.util.ArrayList;

public class Expert {
    private String id;
    private String name;
    private String googleAccount;
    private String phone;
    private ArrayList<String> subjects;
    private Double ratePerQuestion = 0.0;
    private int questionsAnswered = 0;
    private Double rating;

    public Expert() {}
    public Expert (String name, String googleAccount, String phone, ArrayList<String> subjects, Double ratePerQuestion) {
        this.name = name;
        this.googleAccount = googleAccount;
        this.phone = phone;
        this.subjects = subjects;
        this.ratePerQuestion = ratePerQuestion;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoogleAccount() {
        return googleAccount;
    }

    public void setGoogleAccount(String googleAccount) {
        this.googleAccount = googleAccount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public Double getRatePerQuestion() {
        return ratePerQuestion;
    }

    public void setRatePerQuestion(Double ratePerQuestion) {
        this.ratePerQuestion = ratePerQuestion;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    
}
