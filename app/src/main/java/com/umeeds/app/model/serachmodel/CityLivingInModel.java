package com.umeeds.app.model.serachmodel;

public class CityLivingInModel {
    boolean selected;
    private String id;
    private String city;
    private String state;
    private String country;
    private String sortorder;
    private String status;

    public CityLivingInModel(String id, String city, String state, String country, String sortorder, String status, boolean selected) {
        this.id = id;
        this.city = city;
        this.state = state;
        this.country = country;
        this.sortorder = sortorder;
        this.status = status;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
