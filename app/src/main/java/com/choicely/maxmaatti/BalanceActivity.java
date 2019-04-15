package com.choicely.maxmaatti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BalanceActivity extends AppCompatActivity {

    private Account account;
    private TextView balanceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        account = new Account();

        balanceView = findViewById(R.id.balance_view);

        showBalance();
    }


    public void showBalance() {
        balanceView.setText(account.getBalance() + "  €");

    }

}
