package ir.artapps.aroundme.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.artapps.aroundme.data.entities.Venue;

/**
 * Created by navid on 21,December,2018
 */

@Dao
interface  VenuesDAO {

    @Query("SELECT * FROM venue WHERE id = :query")
    List<Venue> getVenueById(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllVenues(List<Venue> venues);

    @Query("DELETE FROM venue")
    void clear();
}
