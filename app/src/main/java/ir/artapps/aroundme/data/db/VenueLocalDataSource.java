package ir.artapps.aroundme.data.db;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ir.artapps.aroundme.entities.Venue;
import ir.artapps.aroundme.entities.VenueRoomEntity;
import ir.artapps.aroundme.entities.VenuesPageEntity;
import ir.artapps.aroundme.entities.mapper.VenueMapper;
import ir.artapps.aroundme.util.DistanceUtil;

public class VenueLocalDataSource {

    private VenuesDAO venuesDAO;

    private static VenueLocalDataSource instance;

    public static VenueLocalDataSource getInstance(Application application) {
        if (instance == null) {
            instance = new VenueLocalDataSource(application);
        }

        return instance;
    }

    public VenueLocalDataSource(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        venuesDAO = db.getVenuesDAO();
    }

    public void getAllVenuesAroundMe(double lat, double lang, MutableLiveData<VenuesPageEntity> data) {
        new getAllVenuesAroundMeAsyncTask(venuesDAO, lat, lang, data).execute();
    }

    public void insert(Venue venue) {
        new insertAsyncTask(venuesDAO).execute(VenueMapper.VenueToVenueRoom(venue));
    }

    private static class insertAsyncTask extends AsyncTask<VenueRoomEntity, Void, Void> {

        private VenuesDAO mAsyncTaskDao;

        insertAsyncTask(VenuesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final VenueRoomEntity... params) {
            mAsyncTaskDao.insertVenue(params[0]);
            return null;
        }
    }

    private static class getAllVenuesAroundMeAsyncTask extends AsyncTask<Void, Void, List<Venue>> {

        private VenuesDAO mAsyncTaskDao;
        private double lat;
        private double lang;
        private MutableLiveData<VenuesPageEntity> data;

        private final double radios = 0.01;  // radios about 1500 meters

        getAllVenuesAroundMeAsyncTask(VenuesDAO dao, double lat, double lang, MutableLiveData<VenuesPageEntity> data) {
            this.mAsyncTaskDao = dao;
            this.lat = lat;
            this.lang = lang;
            this.data = data;
        }

        @Override
        protected List<Venue> doInBackground(final Void... params) {

            List<VenueRoomEntity> venues = mAsyncTaskDao.getVenueByLatlong(lat - radios, lat + radios, lang - radios, lang + radios);
            List<Venue> mVenues = new ArrayList<>();

            for (VenueRoomEntity venue : venues) {
                Venue mVenue = VenueMapper.VenueRoomToVenue(venue);
                mVenue.setDistance(DistanceUtil.distance(venue.getLatitude(), lat, venue.getLongitude(), lang));
                mVenues.add(mVenue);
            }

            Collections.sort(mVenues);

            return mVenues;
        }

        @Override
        protected void onPostExecute(List<Venue> venues) {
            data.setValue(new VenuesPageEntity(venues, 0));
        }
    }

}