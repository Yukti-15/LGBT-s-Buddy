package com.umeeds.app.model.serachmodel;

public class SpecialCaseModel {
    String specialCase;
    boolean selected;

    public SpecialCaseModel(String specialCase, boolean selected) {
        this.specialCase = specialCase;
        this.selected = selected;
    }

    public String getSpecialCase() {
        return specialCase;
    }

    public void setSpecialCase(String specialCase) {
        this.specialCase = specialCase;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
