package com.kodilla.stockpricemonitorwithalert.service;

public class UserObserver implements Observer {
    private String email;

    public UserObserver(String email) {
        this.email = email;
    }

    @Override
    public void update(double stockPrice) {
        System.out.println("Sending email to " + email + ": Stock price has changed to " + stockPrice);
    }
}
