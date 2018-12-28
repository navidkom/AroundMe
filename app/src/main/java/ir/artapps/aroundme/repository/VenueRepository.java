package ir.artapps.aroundme.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.artapps.aroundme.data.db.VenueLocalDataSource;
import ir.artapps.aroundme.entities.GetVenueResponseModel;
import ir.artapps.aroundme.entities.Group;
import ir.artapps.aroundme.entities.Item;
import ir.artapps.aroundme.entities.SearchVenuesesResponseModel;
import ir.artapps.aroundme.entities.Venue;
import ir.artapps.aroundme.entities.VenueFoursquareEntity;
import ir.artapps.aroundme.entities.VenuesPageEntity;
import ir.artapps.aroundme.entities.mapper.VenueMapper;
import ir.artapps.aroundme.data.remote.VenueRemoteDataSource;
import ir.artapps.aroundme.util.DistanceUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueRepository {


    public void getVenuesAroundMe(final Application application, final double lat, final double lang, final int pageNum, final boolean hasNetwork, boolean localOnly, final MutableLiveData<VenuesPageEntity> data) {

        // get locally cached venues
        if (!hasNetwork || localOnly) {
            VenueLocalDataSource.getInstance(application).getAllVenuesAroundMe(lat, lang, data);
            return;
        }

        // get venues from FourSquare API and cache them in local db
        VenueRemoteDataSource.getVenues(lat, lang, pageNum, new Callback<SearchVenuesesResponseModel>() {
            @Override
            public void onResponse(Call<SearchVenuesesResponseModel> call, Response<SearchVenuesesResponseModel> response) {

                if (response != null && response.code() == 200) {
                    if (response.body() != null && response.body().getResponse() != null
                            && response.body().getResponse().getGroups() != null) {

                        List<Venue> venueList = new ArrayList<>();
                        for (Group group : response.body().getResponse().getGroups() ) {
                            for (Item item :group.getItems()) {
                                Venue tempVenue = VenueMapper.VenueForsquareToVenue(item.getVenue());
                                tempVenue.setDistance(DistanceUtil.distance(tempVenue.getLatitude(), lat, tempVenue.getLongitude(), lang));
                                VenueLocalDataSource.getInstance(application).insert(tempVenue);
                                venueList.add(tempVenue);
                            }
                        }

                        boolean isEndOflist = false;
                        if(response.body().getResponse().getTotalResults() != null) {
                            if (response.body().getResponse().getTotalResults() <= (pageNum + 1) * VenueRemoteDataSource.PAGE_SIZE){
                                isEndOflist = true;
                            }
                        }

                        Collections.sort(venueList);
                        data.setValue(new VenuesPageEntity(venueList, pageNum, isEndOflist));
                    }
                } else {
                    getVenuesAroundMe(application, lat, lang, 0, hasNetwork, true, data);
                }
            }

            @Override
            public void onFailure(Call<SearchVenuesesResponseModel> call, Throwable t) {
                getVenuesAroundMe(application, lat, lang,0, hasNetwork, true, data);
            }
        });
    }

    public void getVenueDetail( String venueId, final MutableLiveData<Venue> data) {

        VenueRemoteDataSource.getVenueDetail(venueId, new Callback<GetVenueResponseModel>() {
            @Override
            public void onResponse(Call<GetVenueResponseModel> call, Response<GetVenueResponseModel> response) {

                if (response != null && response.code() == 200) {
                    if (response.body() != null && response.body().getResponse() != null && response.body().getResponse().getVenue() != null) {
                        data.setValue( VenueMapper.VenueForsquareToVenue(response.body().getResponse().getVenue()));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetVenueResponseModel> call, Throwable t) {

            }
        });
    }
}
