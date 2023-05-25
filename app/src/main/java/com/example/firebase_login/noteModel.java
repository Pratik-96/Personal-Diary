package com.example.firebase_login;

import com.google.firebase.Timestamp;

public class noteModel {
    String TITLE;
    String CONTEXT;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    com.google.firebase.Timestamp timestamp;

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
