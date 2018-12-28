package ir.artapps.aroundme.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;

import java.lang.ref.WeakReference;

import ir.artapps.aroundme.entities.VenuesPageEntity;
import ir.artapps.aroundme.repository.LocationRepository;
import ir.artapps.aroundme.repository.VenueRepository;
import ir.artapps.aroundme.util.GeneralUtils;

/**
 * Created by navid on 28,December,2018
 */
public class VenueViewModel extends ViewModel {

    private final MutableLiveData<Location>         locationLiveData = new MutableLiveData<>();
    private final MutableLiveData<VenuesPageEntity> venuesLiveData   = new MutableLiveData<>();
    private final VenueRepository                   venueRepository  = new VenueRepository();
    private       int                               page             = 0;
    private Location location;
    private LocationRepository locationRepository;

    public void initLocationRepository(Context context) {
        if(locationRepository == null){
            locationRepository = new LocationRepository(new WeakReference<>(context), locationLiveData);
        }
    }

    public void getVenuesFirstPage(Application application, Context context, Location location) {
        page = 0;
        this.location = location;
        getVenues(application, context, location, page);
    }

    public void getVenuesNextPage(Application application, Context context) {
        if(location == null){
            return;
        }

        getVenues(application, context, location, page + 1);
    }

    private void getVenues(Application application, Context context, Location location, int page) {
        venueRepository.getVenuesAroundMe(application,
                location.getLatitude(), location.getLongitude(), page,
                GeneralUtils.isNetworkAvailable(context),
                false, venuesLiveData);
    }

    public MutableLiveData<Location> getLocationLiveData() {
        return locationLiveData;
    }

    public MutableLiveData<VenuesPageEntity> getVenuesLiveData() {
        return venuesLiveData;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public Location getLastLocation() {
        return locationRepository.getLastLocation();
    }

    public void startLocationUpdates() {
        locationRepository.startLocationUpdates();
    }

    public void removeLocationListener() {
        locationRepository.removeLocationListener();
    }
}
