package ir.artapps.aroundme.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.util.DistanceUtil;
import ir.artapps.aroundme.util.ImageUtil;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.VenueRecyclerViewViewHolder> {
    private List<Venue> items;
    private OnItemClickListener mItemClickListener;


    public MainRecyclerViewAdapter(List<Venue> items) {
        this.items = items;
    }

    @Override
    public VenueRecyclerViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VenueRecyclerViewViewHolder vh = new VenueRecyclerViewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.venues_recycler_view_view_holder, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(final VenueRecyclerViewViewHolder holder, int position) {

        Venue model = items.get(position);

        holder.name.setText(model.getName());
        holder.distance.setText(DistanceUtil.distanceToString(model.getDistance()));
        holder.imageView.setImageBitmap(null);
        ImageUtil.setImage(model.getIcon(), holder.imageView );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class VenueRecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView distance;
        public ImageView imageView;

        public VenueRecyclerViewViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.venue_recycler_item_name_text_view);
            distance = v.findViewById(R.id.venue_recycler_item_distance_text_view);
            imageView = v.findViewById(R.id.venue_recycler_item_category_image_view);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}