package com.choicely.maxmaatti.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.DatabaseController;

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
            final String depositAmount = depositAmountText.getText().toString();


            if (!TextUtils.isEmpty(depositAmount)) {

                int number = Integer.parseInt(depositAmount);
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
            } else {
                CharSequence text = "Enter the amount you want to deposit!";
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