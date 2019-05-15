package com.cs.nks.easycouriers.model;
public class ImagePath {

    private String Id;
    private String path;

    public ImagePath(String id, String path) {
        Id = id;
        this.path = path;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}