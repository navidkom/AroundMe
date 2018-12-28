package ir.artapps.aroundme.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;

import java.util.List;

import ir.artapps.aroundme.entities.Venue;
import ir.artapps.aroundme.repository.LocationRepository;
import ir.artapps.aroundme.repository.VenueRepository;
import ir.artapps.aroundme.util.GeneralUtils;

/**
 * Created by navid on 28,December,2018
 */
public class VenueDetailViewModel extends ViewModel {

    final MutableLiveData<Venue>    venueLiveData = new MutableLiveData<Venue>();
    final VenueRepository venueRepository = new VenueRepository();


    public void getVenue(String venueId){
        venueRepository.getVenueDetail(venueId, venueLiveData);
    }

    public MutableLiveData<Venue> getVenueLiveData(){
        return venueLiveData;
    }
}
