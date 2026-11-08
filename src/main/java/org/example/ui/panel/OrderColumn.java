package org.example.ui.panel;

public enum OrderColumn {
    ORDER_ID("order_id"),
    CREATED_AT("created_at"),
    ORDERED_AT("ordered_at"),
    STATUS("status");

    private final String title;

    OrderColumn(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Integer getIndex(){
        return this.ordinal();
    }
}
