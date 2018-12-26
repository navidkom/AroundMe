package ir.artapps.aroundme.view;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.data.VenueManager;
import ir.artapps.aroundme.data.entities.Venue;
import ir.artapps.aroundme.util.GeneralUtils;
import ir.artapps.aroundme.util.LocationUtil;
import ir.artapps.aroundme.util.PermissionUtil;

/**
 * Created by navid on 22,December,2018
 */
public class MainActivity extends AppCompatActivity implements Observer<List<Venue>>,MainRecyclerViewAdapter.OnItemClickListener {


    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 105;
    private LocationUtil locationUtil;
    private RecyclerView recyclerView;
    private List<Venue> venueList;
    private MainRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final MutableLiveData<List<Venue>> venuesLiveData = new MutableLiveData<>();
        venuesLiveData.observe(this, this);

        final MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
        locationLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                VenueManager.getInstance().getVenuesAroundMe(getApplication(),
                        location.getLatitude(), location.getLongitude(),
                        GeneralUtils.isNetworkAvailable(MainActivity.this),
                        false, venuesLiveData);
            }
        });

        locationUtil = new LocationUtil(this, locationLiveData);
    }

    protected void onResume() {
        super.onResume();

        if (!PermissionUtil.isLocationPermissionAllow(this)) {
            PermissionUtil.showLocationPermissionDialog(this, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        } else {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationUtil.removeLocationListener();
    }

    private void startLocationUpdates() {
        locationUtil.startLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                }
                return;
            }
        }
    }

    @Override
    public void onChanged(@Nullable List<Venue> venues) {

        venueList = venues;
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        } else {
            adapter = new MainRecyclerViewAdapter(venueList);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        VenueDetailFragment detailFragment = VenueDetailFragment.newInstance(venueList.get(position));
        detailFragment.show(getSupportFragmentManager(), position + "");
    }
}
