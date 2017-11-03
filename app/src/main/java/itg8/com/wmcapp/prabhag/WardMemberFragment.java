package itg8.com.wmcapp.prabhag;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.prabhag.model.ContactModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WardMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WardMemberFragment extends Fragment implements ContactRvAdapter.OnContactClickListener,EasyPermissions.PermissionCallbacks {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_CALL = 103;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;
    @BindView(R.id.txtProfileName)
    TextView txtProfileName;
    @BindView(R.id.txtFromDate)
    TextView txtFromDate;
    @BindView(R.id.ll_profileSummery)
    LinearLayout llProfileSummery;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.lblContactNumber)
    TextView lblContactNumber;
    @BindView(R.id.rvContact)
    RecyclerView rvContact;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<ContactModel> contactList;
    private boolean hasCallPermission;


    public WardMemberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WardMemberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WardMemberFragment newInstance(List<ContactModel> modelList) {
        WardMemberFragment fragment = new WardMemberFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) modelList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contactList = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ward_member, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvContact.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        ContactRvAdapter adapter=new ContactRvAdapter(contactList,this);
        rvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContact.setAdapter(adapter);
        checkCallPermission();
        return view;
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMessageClicked(ContactModel model) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        //TODO implementation put contact number after provided for sms
        Uri data = Uri.parse("sms:");
        smsIntent.setData(data);
//        smsIntent.putExtra("address", "1234567890");
//        smsIntent.putExtra("sms_body", "From WMC");
//        startActivity(Intent.createChooser(smsIntent, "SMS:"));
        startActivity(smsIntent);
    }

    @Override
    public void onCallClicked(ContactModel model) {
        //TODO implement: change below code as per model data after tel;
        if(!hasCallPermission)
            return;
        String telNo="tel:"+"123456789";
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(telNo));
        startActivity(callIntent);
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
