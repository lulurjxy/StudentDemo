
package com.example.studentdemo.been;

public class MenuModel {
    private int icon;
    private String content;

    public MenuModel() {
        super();
    }

    public MenuModel(int icon, String content) {
        this.icon = icon;
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
