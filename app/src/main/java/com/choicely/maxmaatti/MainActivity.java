package com.choicely.maxmaatti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Account account;
    private Button loginButton;
    private EditText cardNumber;
    private EditText pinCode;
    private TextView loginFailed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = new Account();

        loginButton = findViewById(R.id.login_button);
        cardNumber = findViewById(R.id.card_number_field);
        pinCode = findViewById(R.id.pin_code_field);
        loginFailed = findViewById(R.id.login_failed);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(cardNumber.getText().toString(), pinCode.getText().toString());
            }
    });


    }

    public void checkLogin(String cardNumber, String pinCode) {
        if (cardNumber.equals("420232181") && pinCode.equals("2321")) {
            Intent intent = new Intent(this, FeaturesActivity.class);
            this.startActivity(intent);

        } else {
            loginFailed.setText(R.string.login_failed);
        }
    }
}
