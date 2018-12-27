package ir.artapps.aroundme.view.fragment;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.VenueManager;
import ir.artapps.aroundme.data.entities.Category;
import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.data.entities.VenueFoursquareEntity;
import ir.artapps.aroundme.util.ImageUtil;
import ir.artapps.aroundme.view.customview.VenueDetailCustomView;

/**
 * Created by navid on 28,December,2018
 */
public class VenueDetailFragment extends BaseDialogFragment implements OnMapReadyCallback, Observer<VenueFoursquareEntity> {

    private static final String VENUE_MODEL_ARG_KEY = "venueModelKey";

    private final float                   MAP_ZOOM_LEVEL = 14.0f;
    private       Venue                   venue;
    private       ImageView               photoImageView;
    private       ViewGroup               linearLayout;
    private       AppBarLayout            appBarLayout;
    private       CollapsingToolbarLayout collapsingToolbarLayout;


    public static VenueDetailFragment newInstance(Venue venue) {
        VenueDetailFragment venueDetailFragment = new VenueDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(VENUE_MODEL_ARG_KEY, venue);
        venueDetailFragment.setArguments(bundle);
        return venueDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_venue_detail, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(VENUE_MODEL_ARG_KEY)) {
            venue = getArguments().getParcelable(VENUE_MODEL_ARG_KEY);
        }

        initToolbar();
        initViews(view);
        setData();

        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.fragment_venue_detail_map_fragment);
        mapFragment.getMapAsync(this);

        MutableLiveData<VenueFoursquareEntity> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.observe(this, this);

        VenueManager.getInstance().getVenueDetail(venue.getId(), mutableLiveData);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment f = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_venue_detail_map_fragment);
        if (f != null) {
            getFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(venue.getLatitude(), venue.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location)
                .title(venue.getName()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, MAP_ZOOM_LEVEL));

        if (checkMapPermission()) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.fragment_venue_detail_toolbar);
        if (toolbar != null) {
            toolbar.setTitle(venue.getName());
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            toolbar.setNavigationIcon(R.drawable.ic_nav_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    protected void initViews(@NonNull View view) {
        linearLayout = view.findViewById(R.id.fragment_venue_detail_linear_container);
        photoImageView = view.findViewById(R.id.fragment_venue_detail_venue_image_view);
        collapsingToolbarLayout = view.findViewById(R.id.fragment_venue_detail_collapsing_toolbar_layout);
        appBarLayout = view.findViewById(R.id.fragment_venue_detail_app_bar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        appBarLayout.setExpanded(false);
    }

    private void setData() {

        final String ltrChar = "\u200e";
        final String nlChar  = "\n";
        final String virChar = ", ";

        if (venue.getCategories() != null && venue.getCategories().size() > 0) {
            StringBuilder categoryBuilder = new StringBuilder();
            for (Category cat : venue.getCategories()) {
                if (cat.name != null) {
                    categoryBuilder.append(ltrChar);
                    categoryBuilder.append(cat.name);
                    categoryBuilder.append(nlChar);
                }
            }
            if (categoryBuilder.length() > 0) {
                categoryBuilder.deleteCharAt(categoryBuilder.length() - 1);
            }
            addViewToMainView(categoryBuilder.toString(), R.drawable.ic_label_24);
        }

        if (venue.getRating() != null) {
            addViewToMainView(venue.getRating().toString(), R.drawable.ic_star_24);
        }

        if (venue.getLocation() != null) {
            StringBuilder addressBuilder = new StringBuilder();
            if (venue.getLocation().getCountry() != null) {
                addressBuilder.append(ltrChar);
                addressBuilder.append(venue.getLocation().getCountry());
            }
            if (venue.getLocation().getCity() != null) {
                addressBuilder.append(ltrChar);
                addressBuilder.append(virChar);
                addressBuilder.append(venue.getLocation().getCity());
            }
            if (venue.getLocation().getAddress() != null) {
                addressBuilder.append(nlChar);
                addressBuilder.append(ltrChar);
                addressBuilder.append(venue.getLocation().getAddress());
            }
            if (venue.getLocation().getCrossStreet() != null) {
                addressBuilder.append(nlChar);
                addressBuilder.append(ltrChar);
                addressBuilder.append(venue.getLocation().getCrossStreet());
            }
            if (addressBuilder.length() > 0) {
                addViewToMainView(addressBuilder.toString(), R.drawable.ic_location_24);
            }
        }

        if (venue.getContact() != null) {
            if (venue.getContact().getFormattedPhone() != null) {
                addViewToMainView(venue.getContact().getFormattedPhone(), R.drawable.ic_phone_24);
            }
        }
    }

    private void addViewToMainView(String text, int drawableResource) {
        linearLayout.addView(new VenueDetailCustomView(getContext(), text, getResources().getDrawable(drawableResource)));
    }

    @Override
    public void onChanged(@Nullable VenueFoursquareEntity venueFoursquareEntity) {
        if (venueFoursquareEntity.getBestPhoto() != null && venueFoursquareEntity.getBestPhoto().getImageUrl() != null) {
            ImageUtil.setImage(venueFoursquareEntity.getBestPhoto().getImageUrl(), new ImageUtil.ImageCallback() {
                @Override
                public void bitmapIsReady(Bitmap bitmap) {
                    photoImageView.setImageBitmap(bitmap);
                    appBarLayout.setExpanded(true);
                }
            });
        }
    }

    private boolean checkMapPermission() {
        return !((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
    }
}
