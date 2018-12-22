package ir.artapps.aroundme.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.entities.Venue;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.VenueRecyclerViewViewHolder> {
    private List<Venue> items;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainRecyclerViewAdapter(List<Venue> items) {
        this.items = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VenueRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        VenueRecyclerViewViewHolder vh = new VenueRecyclerViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.venues_recycler_view_view_holder, parent, false));
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(VenueRecyclerViewViewHolder holder, int position) {
        Venue model = items.get(position);
        holder.name.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class VenueRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public VenueRecyclerViewViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.title);
        }
    }
}