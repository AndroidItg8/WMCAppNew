package itg8.com.wmcapp.emergency;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.emergency.model.ContactModel;
import itg8.com.wmcapp.emergency.model.EmergencyModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmergencyDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyDetailsFragment extends Fragment implements EasyPermissions.PermissionCallbacks, OnRecyclerviewClickListener<ContactModel>, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_CALL = 234;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.lbl_name)
    TextView lblName;
    @BindView(R.id.card)
    CardView card;

    // TODO: Rename and change types of parameters
    private String mParam2;
    private EmergencyModel emergencyModel;
    private Context mContext;
    private boolean hasCallPermission = false;


    public EmergencyDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergencyDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyDetailsFragment newInstance(EmergencyModel param1, String param2) {
        EmergencyDetailsFragment fragment = new EmergencyDetailsFragment();
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
            emergencyModel = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emergency_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        checkCallPermission();
        initView();
        return view;
    }

    private void initView() {
        imgBack.setOnClickListener(this);
        lblName.setText(emergencyModel.getDeptName());
        List<ContactModel> list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new EmergencyCallListAdapter(mContext, list, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null)
            mContext = null;

    }

    private void callGranted(ContactModel from) {
        if (!hasCallPermission)
            return;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(from.getNumber()));
        startActivity(callIntent);
    }

    @AfterPermissionGranted(RC_CALL)
    private void checkCallPermission() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CALL_PHONE)) {
            hasCallPermission = true;
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_call_permission), RC_CALL, Manifest.permission.CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        hasCallPermission = true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        hasCallPermission = false;
    }

    @Override
    public void onClick(int position, ContactModel contactModel) {
        contactModel.setCalling(true);

        callGranted(contactModel);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_back:
                getFragmentManager().popBackStack();
                break;
        }

    }
}
