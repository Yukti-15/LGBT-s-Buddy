package com.umeeds.app.model.usermodel;

public class CountryModel {
    boolean selected;
    private String country_id;
    private String country;
    private String code;
    private String status;
    private String sortorder;

    public CountryModel(String country_id, String country, String code, String status, String sortorder) {
        this.country_id = country_id;
        this.country = country;
        this.code = code;
        this.status = status;
        this.sortorder = sortorder;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
