package com.example.shonateachingassistant;

public class ObjectClass {

    private  String engName,shonaName,img,category;

    public ObjectClass(String engName, String shonaName, String img, String category) {
        this.engName = engName;
        this.shonaName = shonaName;
        this.img = img;
        this.category = category;
    }

    public ObjectClass() {
    }



    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getShonaName() {
        return shonaName;
    }

    public void setShonaName(String shonaName) {
        this.shonaName = shonaName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
