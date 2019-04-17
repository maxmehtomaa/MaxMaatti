package com.choicely.maxmaatti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


    }
}
