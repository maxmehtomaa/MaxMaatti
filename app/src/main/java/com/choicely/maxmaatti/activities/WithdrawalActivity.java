package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choicely.maxmaatti.model.Account;
import com.choicely.maxmaatti.R;


public class WithdrawalActivity extends AppCompatActivity {


    private Account account;
    private Button withdraw20Button;
    private Button withdraw40Button;
    private Button withdraw60Button;
    private Button withdraw100Button;
    private Button withdraw140Button;
    private Button withdraw200Button;
    private Button withdrawOptionalButton;
    private Toast toast;
    private Context context;
    private int duration;
    private EditText withdrawOptionalField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        account = new Account();
        final int balance = account.getBalance();
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

        withdraw20Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You withdrawed 20 euros!";
                CharSequence error = "You dont have enough money to withdraw!";
                if (balance < 20 || balance <= 0) {
                    toast = toast.makeText(context, error, duration);
                    toast.show();
                } else {
                    account.setBalance(balance - 20);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        withdraw40Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You withdrawed 40 euros!";
                CharSequence error = "You dont have enough money to withdraw!";
                if (balance < 40 || balance <= 0) {
                    toast = Toast.makeText(context, error, duration);
                    toast.show();
                } else {
                    account.setBalance(balance - 40);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        withdraw60Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You withdrawed 60 euros!";
                CharSequence error = "You dont have enough money to withdraw!";
               if (balance < 60 || balance <= 0) {
                   toast = Toast.makeText(context, error, duration);
                   toast.show();
               }else {
                   account.setBalance(balance - 60);
                   toast = Toast.makeText(context, text, duration);
                   toast.show();
               }
            }
        });

        withdraw100Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You withdrawed 100 euros!";
                CharSequence error = "You dont have enough money to withdraw!";

                if (balance < 100 || balance <= 0) {
                    toast = Toast.makeText(context, error, duration);
                    toast.show();
                } else {
                    account.setBalance(balance - 100);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        withdraw140Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You withdrawed 140 euros!";
                CharSequence error = "You dont have enough money to withdraw!";
                if (balance < 140 || balance <= 0) {
                    toast = Toast.makeText(context, error, duration);
                    toast.show();
                } else {
                    account.setBalance(balance - 140);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        withdraw200Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "You withdrawed 200 euros!";
                CharSequence error = "You dont have enough money to withdraw!";

                if (balance < 200 || balance <= 0) {
                    toast = Toast.makeText(context, error, duration);
                    toast.show();
                } else {
                    account.setBalance(balance - 200);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        withdrawOptionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(withdrawOptionalField.getText().toString());
                CharSequence text = "You withdrawed " + number + " euros!";
                CharSequence error = "You dont have enough money to withdraw!";

                if (balance < number || balance <= 0) {
                    toast = Toast.makeText(context, error, duration);
                    toast.show();
                } else {
                    account.setBalance(balance - number);
                    withdrawOptionalField.setText(null);
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }
}
