package com.choicely.maxmaatti.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.adapter.EventAdapter;
import com.choicely.maxmaatti.db.DatabaseController;
import com.choicely.maxmaatti.model.AtmEvent;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

/**
 * User interface for account events.
 * Uses RecyclerView to show events in a list.
 */
public class AccountEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_events);

        db = FirebaseFirestore.getInstance();

        setTitle("Account events");

        setupRecyclerView();
    }

    /**
     * Starts listening to database changes
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (adapter != null) {
            adapter.startListening();
        }
    }

    /**
     * Stops listening to database changes
     */
    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    /**
     * Setups RecyclerView for this activity
     */
    private void setupRecyclerView() {
        final DocumentReference docRef = db.collection("accounts").document(DatabaseController.getInstance().getLoggedInAccountId());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = docRef.collection("events")
                .orderBy("event_time", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<AtmEvent> options = new FirestoreRecyclerOptions.Builder<AtmEvent>()
                .setQuery(query, AtmEvent.class)
                .build();

        adapter = new EventAdapter(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.slide_right_to_left);
    }
}
