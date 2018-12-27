package ir.artapps.aroundme.view.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.artapps.aroundme.R;

/**
 * Created by navid on 28,December,2018
 */
public class VenueDetailCustomView extends LinearLayout {

    TextView  textView;
    ImageView imageView;

    public VenueDetailCustomView(Context context) {
        super(context);
        init(context);
    }

    public VenueDetailCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VenueDetailCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public VenueDetailCustomView(Context context, String text, Drawable drawable) {
        super(context);
        init(context);
        setText(text);
        setImage(drawable);
    }

    void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.venue_detail_custom_view, this);
            textView = findViewById(R.id.venue_detail_custom_view_text_view);
            imageView = findViewById(R.id.venue_detail_custom_view_image_view);
        }

    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }
}
