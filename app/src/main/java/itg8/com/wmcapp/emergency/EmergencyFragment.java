package itg8.com.wmcapp.emergency;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.prabhag.model.ContactModel;
import itg8.com.wmcapp.widget.CustomFontTextView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmergencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyFragment extends Fragment implements View.OnClickListener , EasyPermissions.PermissionCallbacks {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String AMBULANCE = "tel:"+"102";
    private static final String HOSPITAL = "tel:"+"9823778532";
    private static final String FIRE = "tel:"+"101";
    private static final String POLICE = "tel:"+"100";
    private static final int RC_CALL = 123;
    @BindView(R.id.img_hospital)
    ImageView imgHospital;
    @BindView(R.id.name)
    CustomFontTextView name;
    @BindView(R.id.card_hospital)
    CardView cardHospital;
    @BindView(R.id.img_fire)
    ImageView imgFire;
    @BindView(R.id.lbl_fire)
    CustomFontTextView lblFire;
    @BindView(R.id.card_fire)
    CardView cardFire;
    @BindView(R.id.img_ambulance)
    ImageView imgAmbulance;
    @BindView(R.id.lbl_ambulance)
    CustomFontTextView lblAmbulance;
    @BindView(R.id.card_ambulance)
    CardView cardAmbulance;
    @BindView(R.id.img_police)
    ImageView imgPolice;
    @BindView(R.id.lbl_police)
    CustomFontTextView lblPolice;
    @BindView(R.id.card_police)
    CardView cardPolice;
    @BindView(R.id.ll_emergency)
    LinearLayout llEmergency;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean hasCallPermission=false;


    public EmergencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergencyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyFragment newInstance(String param1, String param2) {
        EmergencyFragment fragment = new EmergencyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        unbinder = ButterKnife.bind(this, view);
        checkCallPermission();

        initView();
        return view;
    }

    private void initView() {
        cardAmbulance.setOnClickListener(this);
        cardFire.setOnClickListener(this);
        cardHospital.setOnClickListener(this);
        cardPolice.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        String from = null;

        switch (view.getId())
        {
            case R.id.card_ambulance:
                from = AMBULANCE;
                break;
            case R.id.card_hospital:
                from = HOSPITAL;
                break;
                case R.id.card_fire:
                    from = FIRE;
                break;

                case R.id.card_police:
                    from = POLICE;
                break;
        }
        callGranted(from);
    }

    private void callGranted(String from) {
        if(!hasCallPermission)
            return;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(from));
        startActivity(callIntent);
    }

    @AfterPermissionGranted(RC_CALL)
    private void checkCallPermission() {
        if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.CALL_PHONE)){
            hasCallPermission=true;
        }else {
            EasyPermissions.requestPermissions(this,getString(R.string.rationale_call_permission),RC_CALL,Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }






    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        hasCallPermission=true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        hasCallPermission=false;
    }
}
