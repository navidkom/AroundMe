package ir.artapps.aroundme.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;

import java.util.List;

import ir.artapps.aroundme.entities.Venue;
import ir.artapps.aroundme.entities.VenuesPageEntity;
import ir.artapps.aroundme.repository.LocationRepository;
import ir.artapps.aroundme.repository.VenueRepository;
import ir.artapps.aroundme.util.GeneralUtils;

/**
 * Created by navid on 28,December,2018
 */
public class VenueViewModel extends ViewModel {

    private final MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
    private final MutableLiveData<VenuesPageEntity> venuesLiveData = new MutableLiveData<>();
    private final VenueRepository venueRepository = new VenueRepository();
    private int page = 0;
    private Location location;
    private LocationRepository locationRepository;
    private List<Venue> venueList;
    boolean isLoading = false;
    boolean isLastPage = false;

    public void initLocationRepository(Context context, LifecycleOwner lifecycleOwner) {
        locationRepository = new LocationRepository(context, locationLiveData);
        lifecycleOwner.getLifecycle().addObserver(locationRepository);
    }

    public void getVenuesFirstPage(Application application, Context context, Location location) {
        page = 0;
        this.location = location;
        getVenues(application, context, location, page);
    }

    public void getVenuesNextPage(Application application, Context context) {
        if (location == null) {
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

    public List<Venue> getVenueList() {
        return venueList;
    }

    public void setVenueList(List<Venue> venueList) {
        this.venueList = venueList;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
}
