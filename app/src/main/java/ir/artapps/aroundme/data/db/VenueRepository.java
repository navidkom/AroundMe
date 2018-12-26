package ir.artapps.aroundme.data.db;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.util.DistanceUtil;

public class VenueRepository {

    private VenuesDAO venuesDAO;

    private static VenueRepository instance;

    public static VenueRepository getInstance(Application application) {
        if (instance == null) {
            instance = new VenueRepository(application);
        }

        return instance;
    }

    public VenueRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        venuesDAO = db.getVenuesDAO();
    }

    public void getAllVenuesAroundMe(double lat, double lang, MutableLiveData<List<Venue>> data) {
        new getAllVenuesAroundMeAsyncTask(venuesDAO, lat, lang, data).execute();
    }

    public void insert(Venue venue) {
        new insertAsyncTask(venuesDAO).execute(venue);
    }

    private static class insertAsyncTask extends AsyncTask<Venue, Void, List<Venue>> {

        private VenuesDAO mAsyncTaskDao;

        insertAsyncTask(VenuesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Venue> doInBackground(final Venue... params) {
            mAsyncTaskDao.insertVenue(params[0]);
            return null;
        }
    }

    private static class getAllVenuesAroundMeAsyncTask extends AsyncTask<Void, Void, List<Venue>> {

        private VenuesDAO mAsyncTaskDao;
        private double lat;
        private double lang;
        private MutableLiveData<List<Venue>> data;

        private final double radious = 0.1;

        getAllVenuesAroundMeAsyncTask(VenuesDAO dao, double lat, double lang, MutableLiveData<List<Venue>> data) {
            this.mAsyncTaskDao = dao;
            this.lat = lat;
            this.lang = lang;
            this.data = data;
        }

        @Override
        protected List<Venue> doInBackground(final Void... params) {

            List<Venue> venues = mAsyncTaskDao.getVenueByLatlong(lat - radious, lat + radious, lang - radious, lang + radious);

            for (Venue venue : venues) {
                venue.setDistance(DistanceUtil.distance(venue.getLatitude(), lat, venue.getLongitude(), lang));
            }

            Collections.sort(venues);

            return venues;
        }

        @Override
        protected void onPostExecute(List<Venue> venues) {
            data.setValue(venues);
        }
    }

}