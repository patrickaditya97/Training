package com.example.my_pc.training;

public class ListItem {
    private String number;
    private String message;

    public ListItem(String number, String message) {
        this.number = number;
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


