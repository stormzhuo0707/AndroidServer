package com.example.pojo;

public class WeixinList {
    private int id;
    private String titleimg;
    private String title;
    private String content;
    private String time;
    private String showcode;
    private String number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleimg() {
        return titleimg;
    }

    public void setTitleimg(String titleimg) {
        this.titleimg = titleimg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShowcode() {
        return showcode;
    }

    public void setShowcode(String showcode) {
        this.showcode = showcode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", titleimg='" + titleimg + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", showcode='" + showcode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
