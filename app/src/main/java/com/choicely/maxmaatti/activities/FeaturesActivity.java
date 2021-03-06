package com.choicely.maxmaatti.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.choicely.maxmaatti.R;

/**
 * This is a user interface for features.
 */

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

        context = getApplicationContext();


        /*
        Button for withdrawal view
         */
        withdrawButton.setOnClickListener(v -> {
            context = v.getContext();
            Intent intent = new Intent(context, WithdrawalActivity.class);
            context.startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.slide_right_to_left);

        });

       /*
       Button for balance view
        */
        balanceButton.setOnClickListener(v -> {
            context = v.getContext();
            Intent intent = new Intent(context, BalanceActivity.class);
            context.startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.slide_out);

        });

        /*
        Button for deposit view
         */
        depositButton.setOnClickListener(v -> {
            context = v.getContext();
            Intent intent = new Intent(context, DepositActivity.class);
            context.startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.slide_right_to_left);

        });

        /*
        Button for transaction view
         */
        transactionButton.setOnClickListener(v -> {
            context = v.getContext();
            Intent intent = new Intent(context, TransactionActivity.class);
            context.startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.slide_right_to_left);

        });
        /*
        Button for account events view
        */
        accountEventsButton.setOnClickListener(v -> {
            context = v.getContext();
            Intent intent = new Intent(context, AccountEventsActivity.class);
            context.startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.slide_out);

        });
        /*
        Button for logout view
         */
        logoutButton.setOnClickListener(v -> {
            //context = v.getContext();
            Intent intent = new Intent(context, LogoutActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.slide_out);
        });
    }

    /**
     * Back button disabled for this activity temporary
     */

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Intent intent = new Intent(context, LogoutActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
