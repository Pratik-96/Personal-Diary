package com.example.firebase_login;



public class noteModel {
    String TITLE;
    String CONTEXT;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    String timestamp;

    public noteModel() {
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCONTEXT() {
        return CONTEXT;
    }

    public void setCONTEXT(String CONTEXT) {
        this.CONTEXT = CONTEXT;
    }


}
