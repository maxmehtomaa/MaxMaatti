package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.R;

/**
 * This is a user interface for withdrawal functionality
 */
public class WithdrawalActivity extends AppCompatActivity {

    private Button withdraw20Button;
    private Button withdraw40Button;
    private Button withdraw60Button;
    private Button withdraw100Button;
    private Button withdraw140Button;
    private Button withdraw200Button;
    private Button withdrawOptionalButton;
    private Context context;
    private int duration;
    private EditText withdrawOptionalField;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        setTitle("Withdrawal");
        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        withdraw20Button = findViewById(R.id.withdraw20_button);
        withdraw40Button = findViewById(R.id.withdraw40_button);
        withdraw60Button = findViewById(R.id.withdraw60_button);
        withdraw100Button = findViewById(R.id.withdraw100_button);
        withdraw140Button = findViewById(R.id.withdraw140_button);
        withdraw200Button = findViewById(R.id.withdraw200_button);
        withdrawOptionalButton = findViewById(R.id.withdraw_optional_button);

        withdrawOptionalField = findViewById(R.id.withdraw_optional_field);

        /*
         * Buttons for withdraw
         */
        withdraw20Button.setOnClickListener(v -> {
            CharSequence text = "You withdraw'd 20 euros";
            Toast.makeText(context, text, duration).show();
            DatabaseController.getInstance().withdrawal(20);
        });


        withdraw40Button.setOnClickListener(v -> {
            CharSequence text = "You withdraw'd 40 euros!";
            Toast.makeText(context, text, duration).show();
            DatabaseController.getInstance().withdrawal(40);

        });

        withdraw60Button.setOnClickListener(v -> {
            CharSequence text = "You withdraw'd 60 euros!";
            Toast.makeText(context, text, duration).show();
            DatabaseController.getInstance().withdrawal(60);

        });

        withdraw100Button.setOnClickListener(v -> {
            CharSequence text = "You withdraw'd 100 euros!";
            Toast.makeText(context, text, duration).show();
            DatabaseController.getInstance().withdrawal(100);
        });

        withdraw140Button.setOnClickListener(v -> {
            CharSequence text = "You withdraw'd 140 euros!";
            Toast.makeText(context, text, duration).show();
            DatabaseController.getInstance().withdrawal(140);
        });

        withdraw200Button.setOnClickListener(v -> {
            CharSequence text = "You withdraw'd 200 euros!";
            Toast.makeText(context, text, duration).show();
            DatabaseController.getInstance().withdrawal(200);

        });

        withdrawOptionalButton.setOnClickListener(v -> {
            int number = Integer.parseInt(withdrawOptionalField.getText().toString());

            if (number <= 0) {
                CharSequence negativeValue = "Negative withdraw amount!";
                Toast.makeText(context, negativeValue, duration).show();
            } else {
                CharSequence text = "You withdrawed " + number + " euros!";
                DatabaseController.getInstance().withdrawal(number);
                Toast.makeText(context, text, duration).show();
                withdrawOptionalField.setText(null);
            }
            CharSequence error = "You dont have enough money to withdraw!";

        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
}
