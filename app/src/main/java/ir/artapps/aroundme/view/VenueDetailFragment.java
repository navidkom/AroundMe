package ir.artapps.aroundme.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.entities.Venue;

public class VenueDetailFragment extends DialogFragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private static final String VENUE_MODEL_ARG_KEY = "venueModelKey";

    private Venue venue;
    private SupportMapFragment mapFragment;

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



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(VENUE_MODEL_ARG_KEY)) {
            venue = getArguments().getParcelable(VENUE_MODEL_ARG_KEY);
        }

        Toolbar toolbar = getView().findViewById(R.id.toolbar);
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

        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Nullable
    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View mainView = inflater.inflate(R.layout.fragment_venue_detail, container, false);
//        return mainView;
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        View view;
//        try {
//            view = inflater.inflate(R.layout.fragment_venue_detail, container, false);
//        } catch (InflateException e) {
//            /* map is already there, just return view as it is */
//        }
//        return view;

        View rootView = inflater.inflate(R.layout.fragment_venue_detail, container, false);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return rootView;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(venue.getLatitude(), venue.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location)
                .title(venue.getName()));


        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));

        if(checkMapPermission()) {
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private boolean checkMapPermission() {
        return !((ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
    }
}
