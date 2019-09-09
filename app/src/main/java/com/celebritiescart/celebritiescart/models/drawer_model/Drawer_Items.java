package com.celebritiescart.celebritiescart.models.drawer_model;


public class Drawer_Items {

    private Integer icon;
    private String title;
    private String count;


    public Drawer_Items(Integer icon, String title) {
        this.icon = icon;
        this.title = title;
    }


    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
