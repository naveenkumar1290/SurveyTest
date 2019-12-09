package com.cs.nks.easycouriers.model;

public class Patient {
  private   String _name;
    private   String _id;
    private   String _contact;
    private   String _mail;
    private   String _blood_group;
    private   String _address;

    public Patient(String _name, String _id, String _contact, String _mail, String _blood_group, String _address) {
        this._name = _name;
        this._id = _id;
        this._contact = _contact;
        this._mail = _mail;
        this._blood_group = _blood_group;
        this._address = _address;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_contact() {
        return _contact;
    }

    public void set_contact(String _contact) {
        this._contact = _contact;
    }

    public String get_mail() {
        return _mail;
    }

    public void set_mail(String _mail) {
        this._mail = _mail;
    }

    public String get_blood_group() {
        return _blood_group;
    }

    public void set_blood_group(String _blood_group) {
        this._blood_group = _blood_group;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }
}
