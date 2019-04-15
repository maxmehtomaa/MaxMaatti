package com.choicely.maxmaatti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DepositActivity extends AppCompatActivity {

    private EditText depositAmount;
    private Button depositButton;
    private Account account;
    private int balance;
    private Context context;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);


        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;


        account = new Account();

        balance = account.getBalance();


        depositAmount = findViewById(R.id.deposit_amount);
        depositButton = findViewById(R.id.deposit_button);

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(depositAmount.getText().toString());
                CharSequence text = "You deposited " + number + " euros!";
                account.setBalance(balance + number);
                Toast.makeText(context, text, duration).show();
                depositAmount.setText(null);
            }
        });
    }
}
