package ir.artapps.aroundme.view.activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ir.artapps.aroundme.R;
import ir.artapps.aroundme.entities.Venue;
import ir.artapps.aroundme.entities.VenuesPageEntity;
import ir.artapps.aroundme.util.PermissionUtil;
import ir.artapps.aroundme.view.adapter.MainRecyclerViewAdapter;
import ir.artapps.aroundme.view.fragment.VenueDetailFragment;
import ir.artapps.aroundme.viewmodel.VenueViewModel;

/**
 * Created by navid on 22,December,2018
 */
public class MainActivity extends AppCompatActivity implements MainRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 105;
    VenueViewModel venueViewModel = new VenueViewModel();
    boolean        isLoading      = false;
    boolean        isLastPage     = false;
    private RecyclerView                 recyclerView;
    private List<Venue>                  venueList;
    private SwipeRefreshLayout           refreshLayout;
    private MutableLiveData<List<Venue>> venuesLiveData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        recyclerView = findViewById(R.id.activity_main_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading && !isLastPage) {
                    venueViewModel.getVenuesNextPage(getApplication(), MainActivity.this);
                    isLoading = true;
                }
            }
        });

        refreshLayout = findViewById(R.id.activity_main_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        venueViewModel.getLocationLiveData().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                getData(location);
            }
        });

        venueViewModel.getVenuesLiveData().observe(this, new Observer<VenuesPageEntity>() {
            @Override
            public void onChanged(@Nullable VenuesPageEntity venuesPageEntity) {
                List<Venue> venues = venuesPageEntity.getVenues();

                if (isLoading) {
                    isLoading = false;
                }

                if (venuesPageEntity.getPage() == 0) {
                    updateRecyclerView(venues);
                } else if (venuesPageEntity.getPage() == venueViewModel.getPage() + 1) {
                    addToRecyclerView(venues);
                }
                venueViewModel.setPage(venuesPageEntity.getPage());
                isLastPage = venuesPageEntity.isEndOfList();
            }
        });

        venueViewModel.initLocationRepository(this);
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
        venueViewModel.removeLocationListener();
    }

    private void startLocationUpdates() {
        venueViewModel.startLocationUpdates();
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

    private void getData(Location location) {
        venueViewModel.getVenuesFirstPage(getApplication(), this, location);
    }

    private void updateRecyclerView(List<Venue> venues) {
        venueList = venues;
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(venueList);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
    }

    private void addToRecyclerView(List<Venue> venues) {
        int firstPos = venueList.size();
        venueList.addAll(venues);
        recyclerView.getAdapter().notifyItemRangeInserted(firstPos, venues.size());
    }

    @Override
    public void onItemClick(View view, int position) {
        VenueDetailFragment detailFragment = VenueDetailFragment.newInstance(venueList.get(position));
        detailFragment.show(getSupportFragmentManager(), position + "");
    }

    @Override
    public void onRefresh() {
        Location location = venueViewModel.getLastLocation();
        if (location != null) {
            isLastPage = false;
            getData(location);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
