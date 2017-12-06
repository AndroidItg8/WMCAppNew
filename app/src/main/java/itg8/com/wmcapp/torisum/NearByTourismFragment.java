package itg8.com.wmcapp.torisum;


import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NearByTourismFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearByTourismFragment extends Fragment implements OnMapReadyCallback ,EasyPermissions.PermissionCallbacks, OnRecyclerviewClickListener<TorisumModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_LOCATION = 234;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    private List<TorisumModel> list;
    private boolean canAccessLocation;
    private LatLng lastLatLng;
    private LatLng p1;


    public NearByTourismFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tourismList Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NearByTourismFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NearByTourismFragment newInstance(List<TorisumModel> tourismList, String param2) {
        NearByTourismFragment fragment = new NearByTourismFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) tourismList);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_by_tourism, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        unbinder = ButterKnife.bind(this, view);
        checkStoragePerm();
        getLatitudeLongitude();
        setRecyclerView();
        return view;
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TorisumNearAdapter(getActivity(),list, this));
    }

    private void getLatitudeLongitude() {
        Observable.create(new ObservableOnSubscribe<LatLng>() {
            @Override
            public void subscribe(ObservableEmitter<LatLng> e) throws Exception {
                e.onNext(getLocationFromAddress());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LatLng>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LatLng latLng) {
                        if (latLng != null && mMap != null) {
                            lastLatLng = latLng;
                            mMap.addMarker(new MarkerOptions().position(latLng).title("Hii"));
                            mMap.setMinZoomPreference(17);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
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




    private LatLng getLocationFromAddress()
    {
        String strAddress = null;
        for (TorisumModel model : list) {
            strAddress = model.getAddress();

            if (strAddress == null) {
                return null;
            }
            Geocoder coder = new Geocoder(getActivity());
            List<Address> address;
          p1 = null;

            try {
                address = coder.getFromLocationName(strAddress, 5);
                if (address == null) {
                    return null;
                }
                Address location = address.get(0);
//            p1.=location.getLatitude();
//            location.getLongitude();

                if (location != null)

                    p1 = new LatLng((double) (location.getLatitude()),
                            (double) (location.getLongitude()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    return p1;

}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @AfterPermissionGranted(RC_LOCATION)
    private void checkStoragePerm() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(getActivity(), permissions)) {

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
            }if (!perm.equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                canAccessLocation = false;
            }

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        canAccessLocation = false;
    }

    @Override
    public void onClick(int position, TorisumModel model) {

    }
}
