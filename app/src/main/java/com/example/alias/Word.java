package com.example.alias;

public class Word {
    private String eng_word;
    private String trans;
    private String rus_word;

    Word(String eng_word, String trans, String rus_word){
        this.eng_word = eng_word;
        this.trans = trans;
        this.rus_word = rus_word;
    }
    public String getEng() {
        return eng_word;
    }
    public String getTrans() {
        return trans;
    }
    public String getRus() {
        return rus_word;
    }
}
