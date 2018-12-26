package ir.artapps.aroundme.view;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ir.artapps.aroundme.GenericCallback;
import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.VenueManager;
import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.util.GeneralUtils;
import ir.artapps.aroundme.util.LocationUtil;
import ir.artapps.aroundme.util.PermissionUtil;

/**
 * Created by navid on 22,December,2018
 */
public class MainActivity extends AppCompatActivity {


    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 105;
    private LocationUtil locationUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final GenericCallback<List<Venue>> callback1 = new GenericCallback<List<Venue>>() {
            @Override
            public void response(final List<Venue> venues) {

                MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(venues);
                recyclerView.setAdapter(adapter);
            }
        };

        GenericCallback<Location> locationCallback = new GenericCallback<Location>() {
            @Override
            public void response(Location location) {
                VenueManager.getInstance().getVenuesAroundMe(getApplication(),
                        location.getLatitude(), location.getLongitude(),
                        GeneralUtils.isNetworkAvailable(MainActivity.this),
                        false, callback1);
            }
        };

        locationUtil = new LocationUtil(this, locationCallback);
    }

    protected void onResume() {
        super.onResume();

        if (!PermissionUtil.isLocationPermissionAllow(this)) {
            PermissionUtil.showLocationPermissionDialog(this, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        } else {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        locationUtil.startLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                }
                return;
            }
        }
    }
}
