package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.choicely.maxmaatti.R;

public class FeaturesActivity extends AppCompatActivity {

    private Button withdrawButton;
    private Button depositButton;
    private Button transactionButton;
    private Button accountEventsButton;
    private Button balanceButton;
    private Button logoutButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);

        withdrawButton = findViewById(R.id.withdraw_button);
        depositButton = findViewById(R.id.deposit_button);
        transactionButton = findViewById(R.id.transaction_button);
        accountEventsButton = findViewById(R.id.acc_events_button);
        balanceButton = findViewById(R.id.balance_button);
        logoutButton = findViewById(R.id.logout_button);

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(context, WithdrawalActivity.class);
                context.startActivity(intent);
            }
        });

        balanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(context, BalanceActivity.class);
                context.startActivity(intent);
            }
        });

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(context, DepositActivity.class);
                context.startActivity(intent);
            }
        });

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(context, TransactionActivity.class);
                context.startActivity(intent);
            }
        });

        accountEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(context, AccountEventsActivity.class);
                context.startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                Intent intent = new Intent(context, LogoutActivity.class);
                context.startActivity(intent);
                finish();
            }
        });
    }
}
