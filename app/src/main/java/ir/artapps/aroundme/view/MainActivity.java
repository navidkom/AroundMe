package ir.artapps.aroundme.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.data.network.FoursquareService;
import ir.artapps.aroundme.data.network.SearchVenuesesResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by navid on 22,December,2018
 */
public class MainActivity extends AppCompatActivity {

    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 105;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        final RecyclerView recyclerView = findViewById(R.id.recycler);
        ArrayList<Venue> items = new ArrayList<>();

        final MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        final Callback<SearchVenuesesResponseModel> callback = new Callback<SearchVenuesesResponseModel>() {
            @Override
            public void onResponse(Call<SearchVenuesesResponseModel> call, Response<SearchVenuesesResponseModel> response) {

//                if (response != null) {
//                    MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(response.body().getResponse().getVenues());
//                    recyclerView.setAdapter(adapter);
//                }
            }

            @Override
            public void onFailure(Call<SearchVenuesesResponseModel> call, Throwable t) {

            }
        };


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(10000);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...

                    FoursquareService.getVenues(location.getLatitude(), location.getLongitude(), callback);
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    protected void onResume() {
        super.onResume();
        if (true) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

//            }
            return;
        }

        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null /* Looper */);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    startLocationUpdates();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
