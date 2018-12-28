package ir.artapps.aroundme.data.remote;

import ir.artapps.aroundme.entities.GetVenueResponseModel;
import ir.artapps.aroundme.entities.SearchVenuesesResponseModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by navid on 22,December,2018
 */
public class VenueRemoteDataSource {

    public static String BASE_URL          = "https://api.foursquare.com/v2/";
    public static String CLIENT_ID         = "FFD2COZYISZPKT0SRJIEFG4HBP1MWCQEQ0XOI3PYQDB3S1GQ";
    public static String CLIENT_SECRET     = "45OLR2CXUZ51EDXBEQHY1HR5Z23DO5W4KXA0KGVIZT3NVKZH";
    public static String VERSION           = "20180101";
    public static int    PAGE_SIZE         = 10;
    public static int    RADIOS            = 10000;
    public static int    SOURT_BY_DISTANCE = 1;

    private static FoursquareApi createService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(VenueRemoteDataSource.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FoursquareApi.class);
    }

    public static void getVenues(double latitude, double longitude, int pageNumber, Callback<SearchVenuesesResponseModel> callback) {
        String latlang = String.format("%f,%f", latitude, longitude);

        int offset = pageNumber * PAGE_SIZE;
        int limit = pageNumber + 1 * PAGE_SIZE;

        Call<SearchVenuesesResponseModel> call = createService().exploreVenues(latlang, CLIENT_ID, CLIENT_SECRET, VERSION, limit, offset, RADIOS, SOURT_BY_DISTANCE);
        call.enqueue(callback);
    }

    public static void getVenueDetail(String venueId, Callback<GetVenueResponseModel> callback) {
        Call<GetVenueResponseModel> call = createService().getVenue(venueId, CLIENT_ID, CLIENT_SECRET, VERSION);
        call.enqueue(callback);
    }
}
