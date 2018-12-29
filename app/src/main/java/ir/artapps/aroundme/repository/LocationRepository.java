package ir.artapps.aroundme.repository;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import ir.artapps.aroundme.util.DistanceUtil;

public class LocationRepository implements LifecycleObserver {

    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private static Location preLocation;
    private static Location lastLocation;
    private Context context;
    private double LOCATION_UPDATE_MIM_DISTANCE = 25;

    public LocationRepository(final Context context, final MutableLiveData<Location> locationLiveData) {

        this.context = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
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
                    lastLocation = location;

                    if (preLocation != null && DistanceUtil.distance(preLocation.getLatitude(), location.getLatitude(), preLocation.getLongitude(), location.getLongitude()) < LOCATION_UPDATE_MIM_DISTANCE) {
                        return;
                    }

                    preLocation = location;

                    locationLiveData.setValue(location);

                }
            }
        };
    }

    public Location getLastLocation() {
        preLocation = lastLocation;
        return lastLocation;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void startLocationUpdates() {

        if (context == null) {
            return;
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void removeLocationListener() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        context = null;
    }
}
