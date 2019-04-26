package com.choicely.maxmaatti.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.AtmEvent;
import com.choicely.maxmaatti.db.DatabaseController;

import java.text.SimpleDateFormat;

public class AccountEventsActivity extends AppCompatActivity {

    private TextView balanceChangeText;
    private TextView eventTypeText;
    private TextView messageText;
    private TextView dateText;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_events);

        setTitle("Account events");

        balanceChangeText = findViewById(R.id.activity_account_events_balance_change_text);
        eventTypeText = findViewById(R.id.activity_account_events_event_type_text);
        messageText = findViewById(R.id.activity_account_events_message_text);
        dateText = findViewById(R.id.activity_account_events_date_text);

        showEvents();
    }

    public void showEvents() {
        DatabaseController.getInstance().getEventsAsync(event -> {
            balanceChangeText.setText(String.valueOf(event.getBalance_change()));
            eventTypeText.setText(event.getEvent_type());
            messageText.setText(event.getDescription());
//            dateText.setText(sdf.format(event.getEventTime()));
        });
    }


}
