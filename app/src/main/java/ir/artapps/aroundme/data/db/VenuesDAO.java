package ir.artapps.aroundme.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.artapps.aroundme.entities.VenueRoomEntity;

/**
 * Created by navid on 21,December,2018
 */

@Dao
interface VenuesDAO {

    @Query("SELECT * FROM venue WHERE latitude between :latFrom AND :latTo AND longitude between :longFrom AND :longTo")
    List<VenueRoomEntity> getVenueByLatlong(double latFrom, double latTo, double longFrom, double longTo );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVenue(VenueRoomEntity venue);
}
