package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.R;

public class DepositActivity extends AppCompatActivity {

    /**
     * This is a user interface for deposit
     */

    private EditText depositAmountText;
    private Button depositButton;
    private Context context;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        setTitle("Deposit");

        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        depositAmountText = findViewById(R.id.deposit_amount);
        depositButton = findViewById(R.id.deposit_button);

        /**
         * If deposit amount is less than zero, program doesn't deposit.
         */
        depositButton.setOnClickListener(v -> {
            int number = Integer.parseInt(depositAmountText.getText().toString());
            if (number < 0) {
                CharSequence text = "Negative deposit amount!";
                Toast.makeText(context, text, duration).show();
            } else {
                CharSequence text = "You deposited " + number + " euros!";
                DatabaseController.getInstance().deposit(number);
                Toast toastMessage = Toast.makeText(context, text, duration);
                View toastView = toastMessage.getView();
                toastView.setBackgroundColor(Color.RED);
                toastMessage.show();
                depositAmountText.setText(null);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
}