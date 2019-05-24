package com.choicely.maxmaatti.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.DatabaseController;

import java.util.Locale;

/**
 * This is a user interface for checking balance
 */
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

    /**
     * Shows balance in TextView
     */
    public void showBalance() {
        DatabaseController.getInstance().fetchAccountBalance(balance -> {
            balanceView.setText(String.format(Locale.ENGLISH, "%d€", balance));
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.slide_right_to_left);
    }
}
