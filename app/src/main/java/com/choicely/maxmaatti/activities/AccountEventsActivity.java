package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.DatabaseController;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AccountEventsActivity extends AppCompatActivity {

    private TextView balanceChangeText;
    private TextView eventTypeText;
    private TextView messageText;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_events);

        balanceChangeText = findViewById(R.id.activity_account_events_balance_change_text);
        eventTypeText = findViewById(R.id.activity_account_events_event_type_text);
        messageText = findViewById(R.id.activity_transaction_message_text);
        dateText = findViewById(R.id.activity_account_events_date_text);

    }

    public void showEvents() {

    }
}
