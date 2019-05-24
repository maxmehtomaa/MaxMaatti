package com.choicely.maxmaatti.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.slide_out);
            finish();
        });


        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.slide_right_to_left);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    /**
     * Back button disabled for this activity temporary
     */
    @Override
    public void onBackPressed() {

    }
}
