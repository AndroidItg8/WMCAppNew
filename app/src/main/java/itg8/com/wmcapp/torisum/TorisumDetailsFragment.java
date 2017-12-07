package itg8.com.wmcapp.torisum;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
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
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.torisum.model.Fileupload;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.widget.AutoScrollViewPager;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TorisumDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TorisumDetailsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, ItemPagerAdapter.ImageClickedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.viewPager)
    AutoScrollViewPager viewPager;
    @BindView(R.id.lbl_place_name)
    CustomFontTextView lblPlaceName;
    @BindView(R.id.lbl_place_description)
    CustomFontTextView lblPlaceDescription;
    @BindView(R.id.lbl_time)
    CustomFontTextView lblTime;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.lbl_likes)
    CustomFontTextView lblLikes;
    @BindView(R.id.ll_navi)
    LinearLayout llNavi;

    Unbinder unbinder;
    @BindView(R.id.lbl_address)
    CustomFontTextView lblAddress;
    @BindView(R.id.lbl_direction)
    CustomFontTextView lblDirection;
    @BindView(R.id.lbl_share)
    CustomFontTextView lblShare;
    @BindView(R.id.lbl_like)
    CustomFontTextView lblLike;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap mMap;
    private TorisumModel torisumModel;
    private Context mContext;
    private LatLng lastLatLng;

    public TorisumDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TorisumDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TorisumDetailsFragment newInstance(TorisumModel param1, String param2) {
        TorisumDetailsFragment fragment = new TorisumDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            torisumModel = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_torisum_details, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        unbinder = ButterKnife.bind(this, view);
        initView();


        return view;
    }

    private void initView() {
        ItemPagerAdapter adapter = new ItemPagerAdapter(getActivity(), torisumModel.getFileupload(), this);
        viewPager.setAdapter(adapter);
        viewPager.startAutoScroll(20000);
        lblPlaceName.setText(CommonMethod.checkEmpty(torisumModel.getName()));
        lblPlaceDescription.setText(CommonMethod.checkEmpty(torisumModel.getDescription()));
//        lblTime.setText(CommonMethod.getFormattedDateTime(torisumModel.getAddedDate()));
        lblShare.setOnClickListener(this);
        lblLike.setOnClickListener(this);
        lblDirection.setOnClickListener(this);
        lblAddress.setText(CommonMethod.checkEmpty(torisumModel.getAddress()));
        Observable.create(new ObservableOnSubscribe<LatLng>() {
            @Override
            public void subscribe(ObservableEmitter<LatLng> e) throws Exception {
                e.onNext(getLocationFromAddress(torisumModel.getName() + ", " + torisumModel.getAddress()));
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
                            mMap.addMarker(new MarkerOptions().position(latLng).title(torisumModel.getName()));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(torisumModel.getLotitude(), torisumModel.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(sydney).title(torisumModel.getName()));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public LatLng getLocationFromAddress(String strAddress) {
        if (strAddress == null) {
            return null;
        }
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        LatLng p1 = null;

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
        return p1;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lbl_direction:
                CommonMethod.directionShow(getActivity(), generateDirection());
                break;
            case R.id.lbl_share:
                if (lastLatLng != null)
//                    //TODO Do It Properly  Share Itemn Tuorism
                    CommonMethod.shareItem(getActivity(), generateTextToshare(), torisumModel.getName(), getLocalBitmapUri(torisumModel.getFileupload().get(0)));
                break;
            case R.id.lbl_like:
                break;
        }
    }

    public Uri getLocalBitmapUri(Object model) {
        // Extract Bitmap from ImageView drawable
        String path = "";
        if (model instanceof TempComplaintModel) {
            TempComplaintModel complaintModel = (TempComplaintModel) model;
            path = complaintModel.getFilePath();
        } else if (model instanceof ComplaintModel) {
            ComplaintModel complaintModels = (ComplaintModel) model;
            path = complaintModels.getImagePath();

        }

        Uri bmpUri = null;
        File file = new File(path);
        bmpUri = Uri.fromFile(file);
        return bmpUri;
    }

    private String generateDirection() {

//        return  "https://www.google.com/maps/dir/?api=1&&query_place_id=lastLatLng.latitude,lastLatLng.longitude&travelmode=walking";
//        return "http://maps.google.com/maps?daddr="+lastLatLng.latitude+","+lastLatLng.longitude+ " ("+torisumModel.getName()+")";
        if (lastLatLng != null)
            return "http://maps.google.com/maps?q=" + lastLatLng.latitude + "," + lastLatLng.longitude + " (" + torisumModel.getName() + ")&daddr=" + lastLatLng.latitude + "," + lastLatLng.longitude;
        else
            return "";
    }

    private String generateTextToshare() {
        return "Place visit to Wardha\n" + torisumModel.getName() + "\nAddress: " + torisumModel.getAddress() + "\nhttp://maps.google.com/maps?saddr=" + lastLatLng.latitude + "," + lastLatLng.longitude;
    }

    @Override
    public void onItemClicked(int position, Fileupload fileupload) {
        Fragment fragment = ImageZoomFragment.newInstance(fileupload, "");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(TorisumDetailsFragment.class.getSimpleName()).commit();


    }
}
