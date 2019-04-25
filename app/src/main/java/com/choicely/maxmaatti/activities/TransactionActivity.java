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
    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        transactionButton = findViewById(R.id.activity_transaction_transfer_button);
        cardNumberText = findViewById(R.id.activity_transaction_card_number_text);
        amountText = findViewById(R.id.activity_transaction_amount_text);
        messageText = findViewById(R.id.activity_transaction_message_text);

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseController.getInstance()
                        .transaction(cardNumberText.getText().toString(), Integer.parseInt(amountText.getText().toString()), messageText.getText().toString());
                cardNumberText.setText(null);
                amountText.setText(null);
                messageText.setText(null);
            }
        });


    }
}
