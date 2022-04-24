package com.project.alihammoud.enigma;

public class Data {

    private String title;
    private String desc;
    private String img;

    public Data(){
    }

    public Data(String title, String desc, String img) {
        this.title = title;
        this.desc = desc;
        this.img = img;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
