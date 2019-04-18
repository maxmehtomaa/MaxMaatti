package com.choicely.maxmaatti.model;

public class Account {

    private String accountId;
    private int pinCode;
    private int balance;


    public Account() {

    }

    public Account(String cardNumber, int balance, int pinCode) {
        this.accountId = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
    }

    public String getCardNumber() {
        return this.accountId;
    }

    public void setCardNumber(String cardNumber) {
        this.accountId = cardNumber;
    }

    public int getPinCode() {
        return this.pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
