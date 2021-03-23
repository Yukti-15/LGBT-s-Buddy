package com.umeeds.app.model.usermodel;

public class CityModel {
    boolean selected;
    private String id;
    private String city;
    private String state;
    private String country;
    private String sortorder;
    private String status;


    public CityModel(String id, String city, String state, String country, String sortorder, String status) {
        this.id = id;
        this.city = city;
        this.state = state;
        this.country = country;
        this.sortorder = sortorder;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
