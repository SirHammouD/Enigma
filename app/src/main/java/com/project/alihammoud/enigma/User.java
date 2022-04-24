package com.project.alihammoud.enigma;

public class User {

    private String name;
    private String password;
    private String email;
    private String img;


    public User(){

    }

    public User(String name, String password, String email, String img) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.img=img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
