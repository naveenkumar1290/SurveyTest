package com.cs.nks.easycouriers.model;
public class OurServices {

    private String id;
    private String title;
    private String text;
    private Integer img;

    public OurServices(String id, String title, String text, Integer img) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}