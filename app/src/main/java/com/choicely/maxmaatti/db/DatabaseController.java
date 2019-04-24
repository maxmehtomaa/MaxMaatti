package com.choicely.maxmaatti.db;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;

import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;

public class DatabaseController {

    private static final String TAG = "DatabaseController";
    private String loggedInAccountId;

    private FirebaseFirestore db;

    private static DatabaseController instance;


    private DatabaseController() {
        db = FirebaseFirestore.getInstance();
    }

    public interface OnLoginListener {
        void loginSuccess();

        void loginFailed();
    }

    public interface BalanceChangeListener {
        void onSuccess(int balance);

        void onFailure();
    }

    public interface BalanceListener {
        void onBalance(int balance);
    }

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    public void login(String accountId, String pinCode, OnLoginListener onLoginListener) {
        db.collection("accounts").document(accountId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());

                            Long pin = documentSnapshot.getLong("account_password");

                            Long givenPin = Long.parseLong(pinCode);

                            if (givenPin.equals(pin)) {
                                loggedInAccountId = accountId;
                                onLoginListener.loginSuccess();
                            } else {
                                onLoginListener.loginFailed();
                            }
                        } else {
                            onLoginListener.loginFailed();
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    public void deposit(int depositAmount) {
        if (depositAmount < 0) {
            Log.w(TAG, "Tried to deposit negative amount");
            return;
        }
        internalBalanceChange(depositAmount, new BalanceChangeListener() {
            @Override
            public void onSuccess(int balance) {

            }

            @Override
            public void onFailure() {

            }
        });
    }


    public void withdrawal(int withdrawAmount) {
        if (withdrawAmount > 0) {
            withdrawAmount = -withdrawAmount;
        }
        internalBalanceChange(withdrawAmount, new BalanceChangeListener() {
            @Override
            public void onSuccess(int balance) {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void internalBalanceChange(int amount, BalanceChangeListener balanceChangeListener) {
        final DocumentReference docRef = db.collection("accounts").document(loggedInAccountId);
        AtomicInteger balance = new AtomicInteger();
        db
                .runTransaction((Transaction.Function<Void>) transaction -> {
                    DocumentSnapshot documentSnapshot = transaction.get(docRef);

                    balance.set(documentSnapshot.getLong("account_balance").intValue() + amount);

                    if (balance.get() < 0) {
                        balanceChangeListener.onFailure();
                        // TODO: add listener, onFailure
                        return null;
                    } else {
                        transaction.update(docRef, "account_balance", balance.get());
                    }

                    return null;
                })
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, String.format("Balance change[%s] success!", amount));
                    balanceChangeListener.onSuccess(balance.intValue());
                    // TODO: add listener, onSuccess(balance)
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, String.format("Balance change[%s] failed!", amount), e);
                    balanceChangeListener.onFailure();
                    // TODO: add listener, onFailure

                });
    }

    public void transaction(String accountId, int transactionAmount) {
        final DocumentReference targetDocRef = db.collection("accounts").document(accountId);

        final DocumentReference myDocRef = db.collection("accounts").document(loggedInAccountId);
        AtomicInteger balance = new AtomicInteger();
        db
                .runTransaction((Transaction.Function<Void>) transaction -> {
                    DocumentSnapshot documentSnapshot = transaction.get(myDocRef);
                    DocumentSnapshot targetDocumentSnapshot = transaction.get(targetDocRef);

                    balance.set(documentSnapshot.getLong("account_balance").intValue() - transactionAmount);
                    int targetBalance = targetDocumentSnapshot.getLong("account_balance").intValue() + transactionAmount;


                    if (balance.get() < 0) {
                        //balanceChangeListener.onFailure();
                        // TODO: add listener, onFailure
                        return null;
                    } else {
                        transaction.update(myDocRef, "account_balance", balance.get());
                        transaction.update(targetDocRef, "account_balance", targetBalance);
                    }

                    return null;
                })
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, String.format("Balance change[%s] success!", transactionAmount));
                    //balanceChangeListener.onSuccess(balance.intValue());
                    // TODO: add listener, onSuccess(balance)
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, String.format("Balance change[%s] failed!", transactionAmount), e);
                    //balanceChangeListener.onFailure();
                    // TODO: add listener, onFailure

                });
    }


    public void fetchAccountBalance(BalanceListener callback) {
        db.collection("accounts")
                .document(loggedInAccountId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());

                            int balance = documentSnapshot.getLong("account_balance").intValue();
                            callback.onBalance(balance);
                        }
                    }
                });
    }
    

    public void addAccount(String accountId, int balance, int pinCode) {
        Map<String, Object> account = new HashMap<>();
        account.put("account_balance", balance);
        account.put("account_password", pinCode);
        // account = new Account(accountId, balance, pinCode);

        db.collection("accounts").document(accountId)
                .set(account)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document succesfully written! "))
                .addOnFailureListener(e -> Log.d(TAG, "Error writing document: " + e));
    }
}
