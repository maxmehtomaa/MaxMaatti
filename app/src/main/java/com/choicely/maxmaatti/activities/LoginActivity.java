package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.R;
import com.google.api.Distribution;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This is a user interface for login functionality
 */
public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button aboutButton;
    private EditText cardNumberText;
    private EditText pinCodeText;
    private TextView loginFailed;
    private TextView loginText;
    private Context context;
    private ProgressBar progressBar;
    private Animation bottomAnimation;
    private Animation topAnimation;

    private static final String TAG = "LoginActivity";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = FirebaseFirestore.getInstance();

        context = getApplicationContext();

        loginText = findViewById(R.id.login_text);
        loginButton = findViewById(R.id.login_button);
        cardNumberText = findViewById(R.id.card_number_field);
        pinCodeText = findViewById(R.id.pin_code_field);
        loginFailed = findViewById(R.id.login_failed);
        progressBar = findViewById(R.id.progress_bar);
        aboutButton = findViewById(R.id.about_popup_button);

        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AboutActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });

        progressBar.setVisibility(View.INVISIBLE);
        loginButton.setEnabled(true);

        bottomAnimation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.frombottom);
        topAnimation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fromtop);

        loginButton.setAnimation(bottomAnimation);
        loginText.setAnimation(topAnimation);

        loginButton.setOnClickListener(v -> {
            final String cardNumber = cardNumberText.getText().toString();
            final String pinCode = pinCodeText.getText().toString();

            if (!TextUtils.isEmpty(cardNumber) && !TextUtils.isEmpty(pinCode)) {
                DatabaseController.getInstance().login(cardNumber, pinCode, new DatabaseController.OnLoginListener() {
                    @Override
                    public void loginSuccess() {
                        onLoginSuccess();
                    }

                    @Override
                    public void loginFailed() {
                        onLoginFailed();
                    }
                });
            }
        });
    }

    /**
     * If login failed
     */
    private void onLoginFailed() {
        Log.d(TAG, "Failed to login. Please try again!");
        loginFailed.setText("Failed to login. Please try again.");
    }

    /**
     * If login is successful, start FeaturesActivity
     */
    private void onLoginSuccess() {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);
        loginButton.setClickable(false);
        Log.d(TAG, "Logged in successfully ");
        Intent intent = new Intent(context, FeaturesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        overridePendingTransition(R.anim.fadein, R.anim.slide_out);
        finish();
    }

    /**
     * Back button disabled for this activity temporary
     */
    @Override
    public void onBackPressed() {

    }

}


