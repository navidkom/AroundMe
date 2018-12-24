package ir.artapps.aroundme.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
import ir.artapps.aroundme.data.entities.Venue;

/**
 * Created by navid on 21,December,2018
 */

@Dao
interface VenuesDAO {

    @Query("SELECT * FROM venue WHERE latitude between :latFrom AND :latTo AND longitude between :longFrom AND :longTo")
    LiveData<List<Venue>> getVenueByLatlong( double latFrom, double latTo, double longFrom, double longTo );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVenue(Venue venue);
}
