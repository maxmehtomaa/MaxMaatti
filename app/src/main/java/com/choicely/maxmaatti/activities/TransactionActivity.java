package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.DatabaseController;

public class TransactionActivity extends AppCompatActivity {

    private Button transactionButton;
    private EditText cardNumberText;
    private EditText amountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionButton = findViewById(R.id.transaction_button);
        cardNumberText = findViewById(R.id.card_number);
        amountText = findViewById(R.id.amount);

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
