package com.choicely.maxmaatti.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.AtmEvent;
import com.choicely.maxmaatti.db.DatabaseController;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;

import adapter.EventAdapter;

public class AccountEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;


    private FirebaseFirestore db;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_events);

        db = FirebaseFirestore.getInstance();

        setTitle("Account events");

        setupRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }


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
}
