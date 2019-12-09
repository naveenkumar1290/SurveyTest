package com.cs.nks.easycouriers.model;

public class BranchLocation {
  private   String _text;
    private   String _id;
    private  String _lat;
    private  String _lng;


    public BranchLocation(String _text, String _id, String _lat, String _lng) {
        this._text = _text;
        this._id = _id;
        this._lat = _lat;
        this._lng = _lng;

    }



    public String get_lat() {
        return _lat;
    }

    public void set_lat(String _lat) {
        this._lat = _lat;
    }

    public String get_lng() {
        return _lng;
    }

    public void set_lng(String _lng) {
        this._lng = _lng;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String toString() {
        return _text;
    }


}
