package com.umeeds.app.model.serachmodel;

public class StateLivingInModel {
    boolean selected;
    private String state_id;
    private String state;
    private String code;
    private String country_id;
    private String status;
    private String sortorder;

    public StateLivingInModel(String state_id, String state, String code, String country_id, String status, String sortorder) {
        this.state_id = state_id;
        this.state = state;
        this.code = code;
        this.country_id = country_id;
        this.status = status;
        this.sortorder = sortorder;
    }

    public StateLivingInModel(String state_id, String state, String code, String country_id, String status, String sortorder, boolean selected) {
        this.state_id = state_id;
        this.state = state;
        this.code = code;
        this.country_id = country_id;
        this.status = status;
        this.sortorder = sortorder;
        this.selected = selected;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
