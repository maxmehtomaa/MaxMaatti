package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.choicely.maxmaatti.R;

public class TransactionActivity extends AppCompatActivity {

    private Button transactionButton;
    private EditText cardNumber;
    private EditText amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionButton = findViewById(R.id.transaction_button);
        cardNumber = findViewById(R.id.card_number);
        amount = findViewById(R.id.amount);


    }
}
