package com.example.edusos;

public class Student {
    private String id;
    private String name;
    private String gmailAccount;
    private String membershipType;

    public Student() {}
    public Student (String name, String gmailAccount, String membershipType) {
        this.name = name;
        this.gmailAccount = gmailAccount;
        this.membershipType = membershipType;
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

    public String getGmailAccount() {
        return gmailAccount;
    }

    public void setGmailAccount(String gmailAccount) {
        this.gmailAccount = gmailAccount;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}
