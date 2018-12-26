package ir.artapps.aroundme.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import ir.artapps.aroundme.GenericCallback;
import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.util.BitmapMemCache;
import ir.artapps.aroundme.util.ImageUtil;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.VenueRecyclerViewViewHolder> {
    private List<Venue> items;

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

        NumberFormat nf = new DecimalFormat("###");
        holder.distance.setText(String.format( "%s m", nf.format(model.getDistance()) ));

        holder.imageView.setImageBitmap(null);

        GenericCallback<Bitmap> bitmapGenericCallback = new GenericCallback<Bitmap>() {
            @Override
            public void response(Bitmap bitmap) {
                holder.imageView.setImageBitmap(bitmap);
            }
        };

        ImageUtil.setImage(model.getIcon(), bitmapGenericCallback);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class VenueRecyclerViewViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView distance;
        public ImageView imageView;

        public VenueRecyclerViewViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.venue_recycler_item_name_text_view);
            distance = v.findViewById(R.id.venue_recycler_item_distance_text_view);
            imageView = v.findViewById(R.id.venue_recycler_item_category_image_view);
        }
    }
}