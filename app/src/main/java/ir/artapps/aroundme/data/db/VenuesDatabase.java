package ir.artapps.aroundme.data.db;

/**
 * Created by navid on 21,December,2018
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import ir.artapps.aroundme.data.entities.Venue;

@TypeConverters(DatabseConverter.class)
@Database(entities = {Venue.class}, version = 1)
abstract class VenuesDatabase extends RoomDatabase{
    abstract VenuesDAO getVenuesDAO();
}
