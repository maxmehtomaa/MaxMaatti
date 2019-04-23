package com.choicely.maxmaatti.db;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        final DocumentReference docRef = db.collection("accounts").document(loggedInAccountId);

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            DocumentSnapshot documentSnapshot = transaction.get(docRef);

            int balance = documentSnapshot.getLong("account_balance").intValue() + depositAmount;

            if (depositAmount < 0) {
                return null;
            } else {
                transaction.update(docRef, "account_balance", balance);
            }

            return null;
        })
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Deposit success!"))
                .addOnFailureListener(e -> Log.d(TAG, "Deposit failure", e));
    }

    public void withdrawal(int withdrawAmount) {
        final DocumentReference docRef = db.collection("accounts").document(loggedInAccountId);

        db
                .runTransaction((Transaction.Function<Void>) transaction -> {
                    DocumentSnapshot documentSnapshot = transaction.get(docRef);

                    int balance = documentSnapshot.getLong("account_balance").intValue() - withdrawAmount;

                    if (balance < 0) {
                        return null;
                    } else {
                        transaction.update(docRef, "account_balance", balance);
                    }

                    return null;
                })
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Withdraw success!"))
                .addOnFailureListener(e -> Log.d(TAG, "Withdraw failure!", e));
    }

    public void transaction(String accountId, int transactionAmount) {
        final DocumentReference docRef = db.collection("accounts").document(loggedInAccountId);

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
