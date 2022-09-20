package com.example.shonateachingassistant;

public class SpellingsClass {
    String english,shona;

    public SpellingsClass() {
    }

    public SpellingsClass(String english, String shona) {
        this.english = english;
        this.shona = shona;
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
