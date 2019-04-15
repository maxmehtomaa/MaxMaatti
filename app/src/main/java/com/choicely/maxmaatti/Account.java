package com.choicely.maxmaatti;

public class Account {

    private String cardNumber;
    private int pinCode;
    private static int balance;

    public Account() {
        this.cardNumber = "420232181";
        this.pinCode = 2321;

    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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
