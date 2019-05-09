package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.R;

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
