package ir.artapps.aroundme.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.network.FoursquareService;
import ir.artapps.aroundme.data.network.SearchVenuesesResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by navid on 22,December,2018
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FoursquareService.getVenues("44.30", "37.2", new Callback<SearchVenuesesResponseModel>() {
            @Override
            public void onResponse(Call<SearchVenuesesResponseModel> call, Response<SearchVenuesesResponseModel> response) {

            }

            @Override
            public void onFailure(Call<SearchVenuesesResponseModel> call, Throwable t) {

            }
        });
    }
}
