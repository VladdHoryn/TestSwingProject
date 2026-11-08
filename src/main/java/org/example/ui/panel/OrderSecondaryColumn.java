package org.example.ui.panel;

public enum OrderSecondaryColumn {
    PRODUCT_CODE("product_code"),
    PRODUCT_NAME("product_name"),
    QUANTITY("quantity");

    private final String title;

    OrderSecondaryColumn(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIndex(){
        return this.ordinal();
    }
}
