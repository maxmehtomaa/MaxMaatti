package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.model.Account;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText cardNumberText;
    private EditText pinCodeText;
    private TextView loginFailed;
    private Context context;

    private static final String TAG = "LoginActivity";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        context = getApplicationContext();

        loginButton = findViewById(R.id.login_button);
        cardNumberText = findViewById(R.id.card_number_field);
        pinCodeText = findViewById(R.id.pin_code_field);
        loginFailed = findViewById(R.id.login_failed);

        loginButton.setOnClickListener(v -> {
            DatabaseController.getInstance().login(cardNumberText.getText().toString(), pinCodeText.getText().toString(), new DatabaseController.OnLoginListener() {
                @Override
                public void loginSuccess() {
                    Log.d(TAG, "Logged in succesfully ");
                    Intent intent = new Intent(context, FeaturesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    finish();
                }

                @Override
                public void loginFailed() {
                    Log.d(TAG, "Failed to login. Please try again!");
                    loginFailed.setText("Failed to login. Please try again.");
                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {

    }
}


