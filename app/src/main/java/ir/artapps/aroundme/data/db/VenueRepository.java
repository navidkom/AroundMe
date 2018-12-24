package ir.artapps.aroundme.data.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ir.artapps.aroundme.data.entities.Venue;

public class VenueRepository {

    private VenuesDAO venuesDAO;
    private final double radious = 0.1;

    VenueRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        venuesDAO = db.getVenuesDAO();
    }

    LiveData<List<Venue>> getAllVenuesAroundMe(double lat, double lang) {
        return venuesDAO.getVenueByLatlong(lat - radious , lat + radious , lang - radious, lang + radious);
    }

    public void insert (Venue venue) {
        new insertAsyncTask(venuesDAO).execute(venue);
    }

    private static class insertAsyncTask extends AsyncTask<Venue, Void, Void> {

        private VenuesDAO mAsyncTaskDao;

        insertAsyncTask(VenuesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Venue... params) {
            mAsyncTaskDao.insertVenue(params[0]);
            return null;
        }
    }
}