package ir.artapps.aroundme.repository;

import android.Manifest;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.lang.ref.WeakReference;

import ir.artapps.aroundme.util.DistanceUtil;

public class LocationRepository  {

    private LocationCallback            mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest             mLocationRequest;
    private Location                    preLocation;
    private Location                    lastLocation;
    private WeakReference<Context> context;
    private double LOCATION_UPDATE_MIM_DISTANCE = 25;

    public LocationRepository(final WeakReference<Context> context, final MutableLiveData<Location> locationLiveData) {

        this.context = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context.get());
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

    public Location getLastLocation(){
        preLocation = lastLocation;
        return lastLocation;
    }

    public void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(context.get(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context.get(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback,
                null);

    }

    public void removeLocationListener() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}