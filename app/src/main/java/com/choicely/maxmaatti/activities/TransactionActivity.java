package com.choicely.maxmaatti.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
            final String cardNumber = cardNumberText.getText().toString();
            final String amount = amountText.getText().toString();

            if (!TextUtils.isEmpty(cardNumber) && !TextUtils.isEmpty(amount)) {

                DatabaseController.getInstance()
                        .transaction(cardNumberText.getText().toString(), Integer.parseInt(amountText.getText().toString()), messageText.getText().toString());
                CharSequence text = "Money successfully transferred!";
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                cardNumberText.setText(null);
                amountText.setText(null);
                messageText.setText(null);
            } else {
                CharSequence text = "Enter the card number and amount";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.slide_out);

    }
}
