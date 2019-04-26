package adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.db.AtmEvent;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EventAdapter extends FirestoreRecyclerAdapter<AtmEvent, EventAdapter.EventHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public EventAdapter(@NonNull FirestoreRecyclerOptions<AtmEvent> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder eventHolder, int i, @NonNull AtmEvent atmEvent) {

    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class EventHolder extends RecyclerView.ViewHolder {
        TextView eventTypeText;
        TextView balanceChangeText;
        TextView dateText;
        TextView descriptionText;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventTypeText = itemView.findViewById(R.id.activity_account_events_event_type_text);
            balanceChangeText = itemView.findViewById(R.id.activity_account_events_balance_change_text);
            dateText = itemView.findViewById(R.id.activity_account_events_date_text);
            descriptionText = itemView.findViewById(R.id.activity_account_events_message_text);
        }
    }
}
