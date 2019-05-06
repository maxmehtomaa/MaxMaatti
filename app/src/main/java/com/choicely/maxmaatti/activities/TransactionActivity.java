package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.DatabaseController;

/**
 * This is a user interface for transaction functionality
 */
public class TransactionActivity extends AppCompatActivity {

    private Button transactionButton;
    private EditText cardNumberText;
    private EditText amountText;
    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        setTitle("Transaction");

        transactionButton = findViewById(R.id.activity_transaction_transfer_button);
        cardNumberText = findViewById(R.id.activity_transaction_card_number_text);
        amountText = findViewById(R.id.activity_transaction_amount_text);
        messageText = findViewById(R.id.activity_transaction_message_text);

        /*
        Button for transaction
         */
        transactionButton.setOnClickListener(v -> {
            DatabaseController.getInstance()
                    .transaction(cardNumberText.getText().toString(), Integer.parseInt(amountText.getText().toString()), messageText.getText().toString());
            CharSequence text = "Money successfully transferred!";
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            cardNumberText.setText(null);
            amountText.setText(null);
            messageText.setText(null);
        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
}
