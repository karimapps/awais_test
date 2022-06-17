package com.karimapps.mliving.db;

public class MyReminders {

    private String id;
    private String type;
    private String title;
    private String contet;
    private String time;
    private String frequency;


    public MyReminders(String id, String type, String title, String contet, String time, String frequency) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.contet = contet;
        this.time = time;
        this.frequency = frequency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContet() {
        return contet;
    }

    public void setContet(String contet) {
        this.contet = contet;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
