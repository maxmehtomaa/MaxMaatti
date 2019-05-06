package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.choicely.maxmaatti.R;

/**
 * This is a user interface for logout functionality
 */
public class LogoutActivity extends AppCompatActivity {

    private Button yesButton;
    private Button noButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        context = getApplicationContext();

        yesButton = findViewById(R.id.yes_button);
        noButton = findViewById(R.id.no_button);

        yesButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        noButton.setOnClickListener(v -> finish());

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
}
