package com.project.alihammoud.enigma;

public class Questions {

    private String questions;
    private String h1, h2, h3;
    private String A1, A2, A3;

    public Questions(){
    }


    public Questions(String questions, String h1, String h2, String h3, String a1, String a2, String a3) {
        this.questions = questions;
        this.h1 = h1;
        this.h2 = h2;
        this.h3 = h3;
        this.A1 = a1;
        this.A2 = a2;
        this.A3 = a3;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public String getA1() {
        return A1;
    }

    public void setA1(String a1) {
        A1 = a1;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public String getA3() {
        return A3;
    }

    public void setA3(String a3) {
        A3 = a3;
    }
}
