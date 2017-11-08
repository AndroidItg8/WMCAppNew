package itg8.com.wmcapp.torisum;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.google.android.gms.maps.GoogleMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.widget.AutoScrollViewPager;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TorisumDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TorisumDetailsFragment extends Fragment {
    //implements OnMapReadyCallback
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
    @BindView(R.id.lbl_reviews)
    CustomFontTextView lblReviews;
    @BindView(R.id.ll_navi)
    LinearLayout llNavi;
//    @BindView(R.id.map)
//    android.widget.fragment map;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap mMap;
    private TorisumModel torisumModel;

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
//        SupportMapFragment mapFragment = (SupportMapFragment).getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        unbinder = ButterKnife.bind(this, view);
        int[] mDrawables= {R.drawable.bpkuti, R.drawable.garbage};
        ItemPagerAdapter adapter = new ItemPagerAdapter(getActivity(), mDrawables);
        viewPager.setAdapter(adapter);
        viewPager.startAutoScroll(6000);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}
