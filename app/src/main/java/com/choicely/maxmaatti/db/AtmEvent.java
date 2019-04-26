package com.choicely.maxmaatti.db;

import java.util.Date;

public class AtmEvent {

    private String event_type;
    private int balance_change;
//    private Date event_time;
    private String description;

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public int getBalance_change() {
        return balance_change;
    }

    public void setBalance_change(int balance_change) {
        this.balance_change = balance_change;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
