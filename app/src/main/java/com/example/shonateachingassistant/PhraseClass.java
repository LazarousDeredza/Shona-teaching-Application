package com.example.shonateachingassistant;

public class PhraseClass {
    String  id, english,shona;

    public PhraseClass(String id, String english, String shona) {
        this.id = id;
        this.english = english;
        this.shona = shona;
    }

    public PhraseClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getShona() {
        return shona;
    }

    public void setShona(String shona) {
        this.shona = shona;
    }
}
