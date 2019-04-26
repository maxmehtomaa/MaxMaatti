package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.model.Account;
import com.choicely.maxmaatti.R;

import java.util.Locale;

public class BalanceActivity extends AppCompatActivity {

    private TextView balanceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        setTitle("Balance");
        balanceView = findViewById(R.id.balance_view);
        showBalance();
    }

    public void showBalance() {
        DatabaseController.getInstance().fetchAccountBalance(balance -> {
            balanceView.setText(String.format(Locale.ENGLISH, "%d€", balance));
        });
    }
}
