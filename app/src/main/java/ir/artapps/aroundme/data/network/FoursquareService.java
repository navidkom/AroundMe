package ir.artapps.aroundme.data.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by navid on 22,December,2018
 */
public class FoursquareService {

    public static String BASE_URL = "https://api.foursquare.com/v2/";
    public static String CLIENT_ID = "1HGHBXIUORNUDJBKSLYO2TA4GT0ADA2FMLW3HES1HIKV3FVR";
    public static String CLIENT_SECRET = "VAJHWESJANMGTPBDR4QEBXRTKBADBB0O4UT5DG4CTQYLH0AK";
    public static String VERSION = "20180101";

    private static FoursquareApi createService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FoursquareService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FoursquareApi.class);
    }

    public static void getVenues(String latitude, String longitude, Callback<SearchVenuesesResponseModel> callback){
        String latlang = String.format("%s,%s" , latitude, longitude);
        Call<SearchVenuesesResponseModel> call = createService().searchVenues(latlang, CLIENT_ID, CLIENT_SECRET, VERSION);
        call.enqueue(callback);
    }
}
