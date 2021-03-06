package com.choicely.maxmaatti.model;

import java.util.Date;

/**
 * This is a model class for account events
 *
 */
public class AtmEvent {

    private String event_type;
    private int balance_change;

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    private Date event_time;
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
