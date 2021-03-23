package com.umeeds.app.model.usermodel;

public class ReligiousModel {
    private String id;
    private String name;
    private String sortorder;
    private String status;
    private boolean selected;


    public ReligiousModel(String id, String name, String sortorder, String status) {
        this.id = id;
        this.name = name;
        this.sortorder = sortorder;
        this.status = status;
    }

    public ReligiousModel(String id, String name, String sortorder, String status, boolean selected) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
