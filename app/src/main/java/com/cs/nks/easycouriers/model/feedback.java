package com.cs.nks.easycouriers.model;
public class feedback {

    private String Id;
    private String text;
    private boolean Okay;
    private boolean Gud;
    private boolean VeryGud;
    private boolean Bad;
    private boolean VeryBad;

    public feedback(String id, String text, boolean okay, boolean gud, boolean veryGud, boolean bad, boolean veryBad) {
        Id = id;
        this.text = text;
        Okay = okay;
        Gud = gud;
        VeryGud = veryGud;
        Bad = bad;
        VeryBad = veryBad;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isOkay() {
        return Okay;
    }

    public void setOkay(boolean okay) {
        Okay = okay;
    }

    public boolean isGud() {
        return Gud;
    }

    public void setGud(boolean gud) {
        Gud = gud;
    }

    public boolean isVeryGud() {
        return VeryGud;
    }

    public void setVeryGud(boolean veryGud) {
        VeryGud = veryGud;
    }

    public boolean isBad() {
        return Bad;
    }

    public void setBad(boolean bad) {
        Bad = bad;
    }

    public boolean isVeryBad() {
        return VeryBad;
    }

    public void setVeryBad(boolean veryBad) {
        VeryBad = veryBad;
    }
}