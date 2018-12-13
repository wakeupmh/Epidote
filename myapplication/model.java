package com.example.nicolas.myapplication;

public class model {
    private int image;
    private String title;
    private String desc;

    public model(int image, String title, String desc, final Class<?> activity) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.activity = activity;
    }
    private Class<?> activity;

    public Class<?> getActivity() {
        return activity;
    }
    public void setActivity(Class<?> activity) {
        this.activity = activity;
    }
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
}
