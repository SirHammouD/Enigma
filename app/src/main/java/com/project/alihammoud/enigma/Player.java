package com.project.alihammoud.enigma;

public class Player {

    String name;
    String ID;
    String type;
    String img;

    public Player(String name, String ID, String type, String img) {
        this.name = name;
        this.ID = ID;
        this.type = type;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
