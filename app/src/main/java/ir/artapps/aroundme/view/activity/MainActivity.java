package ir.artapps.aroundme.view.activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import ir.artapps.aroundme.view.adapter.MainRecyclerViewAdapter;
import ir.artapps.aroundme.view.fragment.VenueDetailFragment;

/**
 * Created by navid on 22,December,2018
 */
public class MainActivity extends AppCompatActivity implements Observer<List<Venue>>,MainRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 105;
    private LocationUtil locationUtil;
    private RecyclerView recyclerView;
    private List<Venue> venueList;
    private SwipeRefreshLayout refreshLayout;
    private MutableLiveData<List<Venue>> venuesLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.activity_main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout = findViewById(R.id.activity_main_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        venuesLiveData = new MutableLiveData<>();
        venuesLiveData.observe(this, this);

        final MutableLiveData<Location> locationLiveData = new MutableLiveData<>();
        locationLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                getData(location);
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

    private void getData(Location location){
        VenueManager.getInstance().getVenuesAroundMe(getApplication(),
                location.getLatitude(), location.getLongitude(),
                GeneralUtils.isNetworkAvailable(MainActivity.this),
                false, venuesLiveData);
    }

    @Override
    public void onChanged(@Nullable List<Venue> venues) {
        venueList = venues;
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        } else {
            MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(venueList);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        VenueDetailFragment detailFragment = VenueDetailFragment.newInstance(venueList.get(position));
        detailFragment.show(getSupportFragmentManager(), position + "");
    }

    @Override
    public void onRefresh() {
        Location location = locationUtil.getLastLocation();
        if(location != null ) {
            getData(location);
        }
    }
}
