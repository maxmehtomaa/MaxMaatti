package com.choicely.maxmaatti.db;

import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import org.w3c.dom.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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

    public interface EventListener {
        void event(AtmEvent event);
        //void event(String eventType, int balanceChange, Date date, String description);
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

    public void deposit(final int depositAmount) {
        if (depositAmount < 0) {
            Log.w(TAG, "Tried to deposit negative amount");
            return;
        }
        internalBalanceChange(depositAmount, new BalanceChangeListener() {
            @Override
            public void onSuccess(int balance) {
                createEvent(loggedInAccountId, "deposit", depositAmount, null);
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
        final int eventBalanceChange = withdrawAmount;
        internalBalanceChange(withdrawAmount, new BalanceChangeListener() {
            @Override
            public void onSuccess(int balance) {
                createEvent(loggedInAccountId, "withdrawal", eventBalanceChange, null);
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

    public void transaction(String accountId, int transactionAmount, String message) {
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
                    createEvent(accountId, "transaction", transactionAmount, message);
                    createEvent(loggedInAccountId, "transaction", -transactionAmount, message);
                    //balanceChangeListener.onSuccess(balance.intValue());
                    // TODO: add listener, onSuccess(balance)
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, String.format("Balance change[%s] failed!", transactionAmount), e);
                    //balanceChangeListener.onFailure();te
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


    public void createEvent(String accountId, String eventType, int balanceChange, String description) {
        final CollectionReference collectionReference = db.collection("accounts");
        Map<String, Object> event = new HashMap<>();

        event.put("balance_change", balanceChange);
        event.put("description", description);
        event.put("event_time", FieldValue.serverTimestamp());
        event.put("event_type", eventType);


        collectionReference.document(accountId).collection("events")
                .add(event)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Event successfully created!"))
                .addOnFailureListener(e -> Log.d(TAG, "Failed to create an event", e));

    }

    public void getEventsAsync(EventListener eventListener) {
        final DocumentReference docRef = db.collection("accounts").document(loggedInAccountId);

        docRef.collection("events").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, document.getId() + " => " + document.getData());

                                AtmEvent e = document.toObject(AtmEvent.class);

                                /*
                                String eventType = document.getString("event_type");
                                int balanceChange = document.getLong("balance_change").intValue();
                                Date date = document.getDate("event_time");
                                String description = document.getString("description");

                                e.setEventType(eventType);
                                e.setBalanceChange(balanceChange);
                                e.setEventTime(date.toString());
                                e.setDescription(description);
*/
                                eventListener.event(e);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: " + task.getException());
                        }
                    }
                });
    }


    public void createAccount(String accountId, int balance, int pinCode) {
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
