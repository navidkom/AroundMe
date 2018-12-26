package ir.artapps.aroundme.data;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.artapps.aroundme.data.db.VenueRepository;
import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.data.entities.VenueFoursquareEntity;
import ir.artapps.aroundme.data.mapper.VenueMapper;
import ir.artapps.aroundme.data.network.FoursquareService;
import ir.artapps.aroundme.data.network.SearchVenuesesResponseModel;
import ir.artapps.aroundme.util.DistanceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueManager {

    private static VenueManager instance;

    public static VenueManager getInstance() {
        if (instance == null) {
            instance = new VenueManager();
        }
        return instance;
    }

    public void getVenuesAroundMe(final Application application, final double lat, final double lang, final boolean hasNetwork, boolean localOnly, final MutableLiveData<List<Venue>> data) {

        // get locally cached venues
        if (!hasNetwork || localOnly) {
            VenueRepository.getInstance(application).getAllVenuesAroundMe(lat, lang, data);
            return;
        }

        // get venues from FourSquare API and cache them in local db
        FoursquareService.getVenues(lat, lang, new Callback<SearchVenuesesResponseModel>() {
            @Override
            public void onResponse(Call<SearchVenuesesResponseModel> call, Response<SearchVenuesesResponseModel> response) {

                if (response != null && response.code() == 200) {
                    if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().getVenues() != null) {

                        List<Venue> venueList = new ArrayList<>();
                        for (VenueFoursquareEntity venue : response.body().getResponse().getVenues()) {
                            Venue tempVenue = VenueMapper.VenueForsquareToVenue(venue);
                            tempVenue.setDistance(DistanceUtil.distance( tempVenue.getLatitude(), lat, tempVenue.getLongitude(), lang ));
                            VenueRepository.getInstance(application).insert(tempVenue);
                            venueList.add(tempVenue);
                        }

                        Collections.sort(venueList);
//                        data.response(venueList);
                        data.setValue(venueList);
                    }
                } else {
                    getVenuesAroundMe(application, lat, lang,hasNetwork,true, data);
                }
            }

            @Override
            public void onFailure(Call<SearchVenuesesResponseModel> call, Throwable t) {
                getVenuesAroundMe(application, lat, lang,hasNetwork,true, data);
            }
        });

    }
}
