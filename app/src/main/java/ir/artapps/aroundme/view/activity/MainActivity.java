package ir.artapps.aroundme.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;

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
    private VenueViewModel venueViewModel;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MainRecyclerViewAdapter adapter;
    LinearLayoutManager linearLayoutManager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        venueViewModel = ViewModelProviders.of(this).get(VenueViewModel.class);

        adapter = new MainRecyclerViewAdapter(venueViewModel.getVenueList(), this);
        recyclerView = findViewById(R.id.activity_main_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !venueViewModel.isLoading() && !venueViewModel.isLastPage()) {
                    venueViewModel.getVenuesNextPage(getApplication(), MainActivity.this);
                    venueViewModel.setLoading(true);
                }
            }
        });

        refreshLayout = findViewById(R.id.activity_main_refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        venueViewModel.getLocationLiveData().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                refreshLayout.setRefreshing(true);
                getData(location);
            }
        });

        venueViewModel.getVenuesLiveData().observe(this, new Observer<VenuesPageEntity>() {
            @Override
            public void onChanged(@Nullable VenuesPageEntity venuesPageEntity) {
                List<Venue> venues = venuesPageEntity.getVenues();

                if (venueViewModel.isLoading()) {
                    venueViewModel.setLoading(false);
                }

                if (venuesPageEntity.getPage() == 0) {
                    updateRecyclerView(venues);
                } else if (venuesPageEntity.getPage() == venueViewModel.getPage() + 1) {
                    addToRecyclerView(venues);
                }
                venueViewModel.setPage(venuesPageEntity.getPage());
                venueViewModel.setLastPage(venuesPageEntity.isEndOfList());
            }
        });

        venueViewModel.initLocationRepository(this, this);
    }

    protected void onResume() {
        super.onResume();

        if (!PermissionUtil.isLocationPermissionAllow(this)) {
            PermissionUtil.showLocationPermissionDialog(this, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    venueViewModel.startLocationUpdates();
                }
                return;
            }
        }
    }

    private void getData(Location location) {
        venueViewModel.getVenuesFirstPage(getApplication(), this, location);
    }

    private void updateRecyclerView(List<Venue> venues) {
        venueViewModel.setVenueList(venues);
        adapter = new MainRecyclerViewAdapter(venueViewModel.getVenueList(), this);
        recyclerView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
    }

    private void addToRecyclerView(List<Venue> venues) {
        int firstPos = venueViewModel.getVenueList().size();
        venueViewModel.getVenueList().addAll(venues);
        adapter.notifyItemRangeInserted(firstPos, venues.size());
    }

    @Override
    public void onItemClick(View view, int position) {
        VenueDetailFragment detailFragment = VenueDetailFragment.newInstance(venueViewModel.getVenueList().get(position));
        detailFragment.show(getSupportFragmentManager(), position + "");
    }

    @Override
    public void onRefresh() {
        Location location = venueViewModel.getLastLocation();
        if (location != null) {
            venueViewModel.setLastPage(false);
            getData(location);
        }
    }
}
