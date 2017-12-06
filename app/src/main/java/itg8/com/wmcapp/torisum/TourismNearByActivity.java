package itg8.com.wmcapp.torisum;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.widget.CustomFontTextView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TourismNearByActivity extends AppCompatActivity implements OnMapReadyCallback, OnRecyclerviewClickListener<TorisumModel>, EasyPermissions.PermissionCallbacks {


    private static final int RC_LOCATION = 234;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    @BindView(R.id.lbl_show_list)
    CustomFontTextView lblShowList;


    private GoogleMap mMap;
    private LatLng lastLatLng;
    private LatLangConatiner p1;
    private boolean canAccessLocation;
    private BottomSheetBehavior<View> behavior;
    private int actionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_near_by);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        View bottomSheet = coordinator.findViewById(R.id.ll_bottom);
        behavior = BottomSheetBehavior.from(bottomSheet);
//        behavior.setPeekHeight(400);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                Logs.d("onStateChanged" + newState);
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {

                    CommonMethod.showHideItem(lblShowList, recyclerView);
                    TypedValue tv = new TypedValue();
                    if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                    {
                        actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());

                        behavior.setPeekHeight(2 * actionBarHeight);
                    }
                }else
                {
                    CommonMethod.showHideItem(recyclerView,lblShowList);
                    behavior.setPeekHeight(actionBarHeight);
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Logs.d("onSlide" + slideOffset);
//


            }
        });
        CommonMethod.showHideItem(recyclerView, lblShowList);
        if (getIntent().hasExtra(CommonMethod.FROM_TOURISM)) {
            List<TorisumModel> list = getIntent().getParcelableArrayListExtra(CommonMethod.FROM_TOURISM);
            setRecyclerView(list);
            getModel(list);
        }
        checkStoragePerm();
         lblShowList.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


             }
         });

    }

    private void getModel(List<TorisumModel> list) {
        getLatitudeLongitude(list);

    }

    private void setRecyclerView(List<TorisumModel> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new TorisumNearAdapter(getApplicationContext(), list, this));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void getLatitudeLongitude(final List<TorisumModel> list) {
        Observable.create(new ObservableOnSubscribe<LatLangConatiner>() {
            @Override
            public void subscribe(ObservableEmitter<LatLangConatiner> e) throws Exception {
                for (TorisumModel model : list) {
                    e.onNext(getLocationFromAddress(model));
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LatLangConatiner>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LatLangConatiner latLng) {
                        if (latLng.latLng != null)
                            setMap(latLng.latLng, latLng.model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void setMap(LatLng latLng, TorisumModel model) {
        if (latLng != null && mMap != null) {
            lastLatLng = latLng;
            mMap.addMarker(new MarkerOptions().position(latLng).title(model.getName()));
            mMap.setMinZoomPreference(9);
            mMap.setMaxZoomPreference(19);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    private LatLangConatiner getLocationFromAddress(TorisumModel strAddress) {
        if (strAddress == null) {
            return null;
        }
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        p1 = new LatLangConatiner();
        p1.model = strAddress;
        try {
            address = coder.getFromLocationName(strAddress.getAddress(), 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
//            p1.=location.getLatitude();
//            location.getLongitude();

            if (location != null) {

                lastLatLng = new LatLng((double) (location.getLatitude()),
                        (double) (location.getLongitude()));
                p1.latLng = lastLatLng;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;

    }

    @Override
    public void onClick(int position, TorisumModel model) {
//        getLatitudeLongitude(model);

    }

    @AfterPermissionGranted(RC_LOCATION)
    private void checkStoragePerm() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            canAccessLocation = true;

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_no_permission), RC_LOCATION, permissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        for (String perm :
                perms) {

            if (!perm.equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                canAccessLocation = false;
            }
            if (!perm.equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                canAccessLocation = false;
            }

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        canAccessLocation = false;
    }

    private static class LatLangConatiner {
        LatLng latLng;
        TorisumModel model;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==android.R.id.home)
           onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
