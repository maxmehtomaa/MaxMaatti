package adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.maxmaatti.R;
import com.choicely.maxmaatti.model.AtmEvent;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * This is an adapter for events
 */

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
    protected void onBindViewHolder(@NonNull EventHolder holder, int i, @NonNull AtmEvent e) {
        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");


        //Capitalizes event types first letter
        holder.eventTypeText.setText(e.getEvent_type().substring(0, 1).toUpperCase() + e.getEvent_type().substring(1).toLowerCase());

        holder.balanceChangeText.setText(Integer.toString(e.getBalance_change()) + " €");
        holder.descriptionText.setText(e.getDescription());
        holder.dateText.setText(formatter.format(e.getEvent_time()));



        if (e.getBalance_change() < 0) {
            holder.balanceChangeText.setTextColor(Color.RED);
        } else {
            holder.balanceChangeText.setTextColor(Color.GREEN);
        }
    }


    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row, parent, false);
        return new EventHolder(v);
    }

    class EventHolder extends RecyclerView.ViewHolder {
        TextView eventTypeText;
        TextView balanceChangeText;
        TextView dateText;
        TextView descriptionText;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventTypeText = itemView.findViewById(R.id.event_list_row_event_type_text);
            balanceChangeText = itemView.findViewById(R.id.event_list_row_balance_change_text);
            dateText = itemView.findViewById(R.id.event_list_row_date_text);
            descriptionText = itemView.findViewById(R.id.event_list_row_message_text);
        }
    }
}
