package ir.artapps.aroundme.data.remote;

import ir.artapps.aroundme.entities.GetVenueResponseModel;
import ir.artapps.aroundme.entities.SearchVenuesesResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by navid on 20,December,2018
 */
public interface FoursquareApi {

    String VEN_ID_KEY    = "venues_Id";
    String LATLANG_KEY   = "ll";
    String CLIENT_ID_KEY = "client_id";
    String SECRET_KEY    = "client_secret";
    String VERSION_KEY   = "v";
    String QUERY_LIMIT   = "limit";
    String QUERY_OFFSET  = "offset";
    String QUERY_RADIOS  = "radius";
    String QUERY_SORT    = "sortByDistance";

    @GET("venues/explore")
    Call<SearchVenuesesResponseModel> exploreVenues
            (@Query(LATLANG_KEY) String latlang,
             @Query(CLIENT_ID_KEY) String clientId,
             @Query(SECRET_KEY) String secret,
             @Query(VERSION_KEY) String version,
             @Query(QUERY_LIMIT) int pageSize,
             @Query(QUERY_OFFSET) int offset,
             @Query(QUERY_RADIOS) int radios,
             @Query(QUERY_SORT) Integer sortFlag);

    @GET("venues/{" + VEN_ID_KEY + "}")
    Call<GetVenueResponseModel> getVenue(
            @Path(VEN_ID_KEY) String venueId,
            @Query(CLIENT_ID_KEY) String clientId,
            @Query(SECRET_KEY) String secret,
            @Query(VERSION_KEY) String version);

}
