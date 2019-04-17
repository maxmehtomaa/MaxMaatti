package com.choicely.maxmaatti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText cardNumberText;
    private EditText pinCodeText;
    private TextView loginFailed;
    private Context context;

    private static final String TAG = "MainActivity";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        context = getApplicationContext();

        loginButton = findViewById(R.id.login_button);
        cardNumberText = findViewById(R.id.card_number_field);
        pinCodeText = findViewById(R.id.pin_code_field);
        loginFailed = findViewById(R.id.login_failed);

        addAccount();

        loginButton.setOnClickListener(v -> {
            checkCredentials(cardNumberText.getText().toString(), pinCodeText.getText().toString());
           // checkLogin(cardNumberText.getText().toString(), pinCodeText.getText().toString());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void checkCredentials(String accountId, final String pinCode) {
        db.collection("accounts").document(accountId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();

                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + documentSnapshot.getData());
                            String accountId1 = documentSnapshot.getId();
                            Long balance = documentSnapshot.getLong("account_balance");
                            Long pin = documentSnapshot.getLong("account_password");

                            Long givenPin = Long.parseLong(pinCode);

                            if (givenPin.equals(pin)) {
                                Log.d(TAG, "Logged in succesfully ");
                                Intent intent = new Intent(context, FeaturesActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else {
                                Log.d(TAG, "Failed to login. Please try again!");
                                loginFailed.setText("Failed to login. Please try again.");
                            }


                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
        }

    private void addAccount() {
        Map<String, Object> accounts = new HashMap<>();
        accounts.put("account_balance", 2450);
        accounts.put("account_password", 2243);

        db.collection("accounts").document("003")
                .set(accounts).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot succesfully written! ");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document!", e);
                    }
                });
    }
}

