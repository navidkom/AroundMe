package ir.artapps.aroundme.data.network;

import io.reactivex.Observable;
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

    @GET("venues/search")
    Call<SearchVenuesesResponseModel> searchVenues(@Query(LATLANG_KEY) String latlang, @Query(CLIENT_ID_KEY) String clientId, @Query(SECRET_KEY) String secret, @Query(VERSION_KEY) String version);

    @GET("venues/{" + VEN_ID_KEY + "}")
    Observable getVenue(@Path(VEN_ID_KEY) String latitude, @Query(CLIENT_ID_KEY) String clientId, @Query(SECRET_KEY) String secret, @Query(VERSION_KEY) String version);

}
