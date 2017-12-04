package itg8.com.wmcapp.prabhag;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.ReceiveBroadcastReceiver;
import itg8.com.wmcapp.common.SentBroadCastReceiver;
import itg8.com.wmcapp.prabhag.model.MemberList;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WardMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WardMemberFragment extends Fragment implements ContactRvAdapter.OnContactClickListener, EasyPermissions.PermissionCallbacks {
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
    @BindView(R.id.lblbasicInfo)
    TextView lblbasicInfo;
    @BindView(R.id.lbl_name)
    TextView lblName;
    @BindView(R.id.lbl_address)
    TextView lblAddress;
    @BindView(R.id.rel_data)
    RelativeLayout relData;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean hasCallPermission;
    private List<MemberList> contactList;
    private Context mContext;
    private boolean hasSMSPermission;
    private Snackbar snackbar;
    private CommonMethod.onSetToolbarTitle listener;


    public WardMemberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param memberList
     * @return A new instance of fragment WardMemberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WardMemberFragment newInstance(List<MemberList> memberList) {
        WardMemberFragment fragment = new WardMemberFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) memberList);
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

        checkCallPermission();
        init();
        listener.onSetTitle(getString(R.string.ward_member));

        return view;
    }

    private void init() {
//        Picasso.with(mContext)
//                .load(CommonMethod.BASE_URL + contactList.get(0).getFileupload().get(0).getFilepath())
//                .networkPolicy(NetworkPolicy.OFFLINE)
//                .error(R.drawable.bpkuti)
//                .into(((imgProfile, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//                        // Try again online if cache failed
//                        Picasso.with(mContext)
//                                .load(CommonMethod.BASE_URL + contactList.get(holder.getAdapterPosition()).getFileupload().get(0).getFilepath())
//                                .into(imgProfile);
//                    }
//                });


        lblName.setText(CommonMethod.checkEmpty(contactList.get(0).getMemberName()));
        txtProfileName.setText(CommonMethod.checkEmpty(contactList.get(0).getMemberName()));
        lblAddress.setText(CommonMethod.checkEmpty(contactList.get(0).getAddress()));
        txtFromDate.setText(CommonMethod.getFormattedDateTime(contactList.get(0).getAdddate()));
        CommonMethod.setUserPicaso(mContext, contactList.get(0).getProfilePic(),imgProfile);

        rvContact.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        ContactRvAdapter adapter = new ContactRvAdapter(contactList, this);
        rvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        rvContact.setAdapter(adapter);

    }

    @AfterPermissionGranted(RC_CALL)
    private void checkCallPermission() {
        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS)) {
            hasCallPermission = true;
            hasSMSPermission = true;
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_call_permission), RC_CALL, Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCallClicked(MemberList model) {
        //TODO implement: change below code as per model data after tel;
        if (!hasCallPermission) {
            showSnackerbar(getString(R.string.rationale_no_permission));
        }else {

            String telNo = "tel:" + model.getMobileNo();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(telNo));
            startActivity(callIntent);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        hasCallPermission = true;
        hasSMSPermission = true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        hasCallPermission = false;
        hasSMSPermission = false;
    }


    @Override
    public void onMessageClicked(MemberList model) {
        if (hasSMSPermission)
        // SendSMS(model.getMobileNo(), generateSMSText(model));
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("smsto:" + model.getMobileNo()));
            intent.putExtra("sms_body", "");
            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                startActivity(intent);
            }
        } else
       showSnackerbar(getString(R.string.rationale_no_permission));

    }




    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (CommonMethod.onSetToolbarTitle) mContext;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null) {
            mContext = null;

            listener = null;
        }
    }

    private void showSnackerbar(String message) {
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        int color = 0;

        color = Color.WHITE;
        hideSnackbar();
        textView.setTextColor(color);
        textView.setMaxLines(2);
        snackbar = Snackbar.make(rvContact, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSnackbar();

            }
        });
        snackbar.show();
    }

    private void hideSnackbar() {
        snackbar.dismiss();
    }

}
