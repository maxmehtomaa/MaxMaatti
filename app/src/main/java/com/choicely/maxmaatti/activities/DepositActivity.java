package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.model.Account;
import com.choicely.maxmaatti.R;

public class DepositActivity extends AppCompatActivity {

    private EditText depositAmountText;
    private Button depositButton;
    private Context context;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);


        context = getApplicationContext();
        duration = Toast.LENGTH_SHORT;

        depositAmountText = findViewById(R.id.deposit_amount);
        depositButton = findViewById(R.id.deposit_button);

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(depositAmountText.getText().toString());
                CharSequence text = "You deposited " + number + " euros!";
                Toast.makeText(context, text, duration).show();
                depositAmountText.setText(null);
            }
    });
    }
}