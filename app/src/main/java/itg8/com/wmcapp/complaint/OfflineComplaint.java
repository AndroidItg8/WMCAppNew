package itg8.com.wmcapp.complaint;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.ProgressRequestBody;
import itg8.com.wmcapp.common.ReceiveBroadcastReceiver;
import itg8.com.wmcapp.common.SentBroadCastReceiver;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.database.ComplaintTableManipute;
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfflineComplaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfflineComplaint extends Fragment implements ComplaintProfilOfflineeAdapter.UnSendItemClickedListner, EasyPermissions.PermissionCallbacks, ProgressRequestBody.UploadCallbacks {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_SEND_SMS = 2345;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Unbinder unbinder;
    @BindView(R.id.rl_no_item)
    RelativeLayout rlNoItem;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private SentBroadCastReceiver receiver;
    private ReceiveBroadcastReceiver receiveBroadcast;
    private boolean canSendSMS;
    private boolean canPhoneState;
    private String[] permissions;
    private ComplaintProfilOfflineeAdapter adapter;


    public OfflineComplaint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OfflineComplaint.
     */
    // TODO: Rename and change types and number of parameters
    public static OfflineComplaint newInstance(String param1, String param2) {
        OfflineComplaint fragment = new OfflineComplaint();
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
        View view = inflater.inflate(R.layout.fragment_offline_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        checkSmsPerm();
        init();

        return view;
    }

    private void init() {
        ComplaintTableManipute complaintTableManipute = new ComplaintTableManipute(mContext);
        List<TempComplaintModel> listTempComplaintModel = complaintTableManipute.getAllComplaint();
        if (listTempComplaintModel != null && listTempComplaintModel.size() > 0) {
            CommonMethod.showHideItem(recyclerView, rlNoItem);
            setRecyclerView(listTempComplaintModel);
        } else {
            CommonMethod.showHideItem(rlNoItem, recyclerView);
        }

    }

    private void setRecyclerView(List<TempComplaintModel> complaintMergeList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ComplaintProfilOfflineeAdapter(getActivity(), complaintMergeList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onSyncItemClicked(int position, TempComplaintModel model) {
        Logs.d("onSyncItemClicked:" + new Gson().toJson(model));

        sendTempCompaliantModel(model, position);


    }

    private void sendTempCompaliantModel(final TempComplaintModel model, final int position) {
        File selectedFile = new File(model.getFilePath());

        adapter.showProgress(position);

        ProgressRequestBody prb = new ProgressRequestBody(selectedFile, this);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", selectedFile.getName(), prb);
        RequestBody lat = createPartFromString(String.valueOf(model.getLongitude()));
        RequestBody lang = createPartFromString(String.valueOf(model.getLatitude()));
        final RequestBody addr = createPartFromString(String.valueOf(model.getAdd()));
        RequestBody desc = createPartFromString(String.valueOf(model.getDescription()));
        //TODO changes: temporary city id;
        RequestBody city = createPartFromInt(model.getCityId());
        RequestBody ident = createPartFromString(model.getShowIdentity());
        Observable<ResponseBody> call = MyApplication.getInstance().getRetroController().addComplaint(getString(R.string.url_add_complaint), part, lat, lang, addr, desc, city, ident);
        call.subscribeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<RegistrationModel>>() {
                    @Override
                    public ObservableSource<RegistrationModel> apply(ResponseBody responseBody) throws Exception {
                        return createRegModelFromResponse(responseBody);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistrationModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegistrationModel o) {
                        if (o != null) {
                            sendComplaintSuccessFully(model, position);

                        } else {
                            adapter.hideProgress(position);
                            Toast.makeText(mContext, "Data can not save.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                            adapter.hideProgress(position);
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

//
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void sendComplaintSuccessFully(TempComplaintModel model, int position) {
        ComplaintTableManipute manipute = new ComplaintTableManipute(mContext);
        manipute.deleteComplaint((int) model.getTblId(), TempComplaintModel.FIELD_TBL_ID);
        model.setSync(true);
        adapter.notifyItemRemoved(position);
        adapter.hideProgress(position);



    }

    private ObservableSource<RegistrationModel> createRegModelFromResponse(ResponseBody body) {
        try {
            String s = body.string();
            JSONObject object = new JSONObject(s);
            if (object.getBoolean("flag")) {
                return Observable.just(new RegistrationModel());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private RequestBody createPartFromString(String val) {
        return RequestBody.create(MediaType.parse("text/plain"), val);
    }

    private RequestBody createPartFromInt(int val) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(val));
    }

    @Override
    public void onShareItemClicked(int position, final TempComplaintModel model, ImageView view) {
        //   CommonMethod.shareItem(getActivity(), generateTextToshare(model), (model.getComplaintName()), getLocalBitmapUri(model.getFilePath()));

        Observable.create(new ObservableOnSubscribe<Uri>() {
            @Override
            public void subscribe(ObservableEmitter<Uri> e) throws Exception {
                e.onNext(getLocalBitmapUri(model.getFilePath()));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Uri>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Uri uri) {
                CommonMethod.shareItem(mContext, (model.getComplaintName()), generateTextToshare(model), uri);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public Uri getLocalBitmapUri(String path) {
        if (!TextUtils.isEmpty(path)) {
            Logs.d("Path:" + path);

            return Uri.parse(path);
        } else {
            return null;
        }


    }

    private String generateTextToshare(TempComplaintModel models) {
        return "This  Complaint \n" + models.getComplaintName() + "\n Description: " + models.getDescription() + "\nAddress:" + models.getCityName();


    }

    @Override
    public void onSMSItemClicked(int position, TempComplaintModel model) {
        if (canPhoneState || canSendSMS) {
            SendSMS("9823857732", generateSMSText(model));

        } else {
            checkSmsPerm();
        }
    }

    private void SendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent(CommonMethod.SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent(CommonMethod.DELIVERED), 0);
        Intent intent = new Intent();
        intent.setAction(CommonMethod.SENT);
        getActivity().sendBroadcast(intent);
        intent.setAction(CommonMethod.DELIVERED);
        getActivity().sendBroadcast(intent);


        try {
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "exception", Toast.LENGTH_LONG).show();
        }
    }


    private String generateSMSText(TempComplaintModel model) {
        String builder;
        builder = "Dear Sir, " + "We have problem in this category" + model.getComplaintName() + "\n  We  have this problem "
                + model.getDescription() + "\n In this location" + model.getAdd();
        return builder;

    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(CommonMethod.SENT);
        receiver = new SentBroadCastReceiver();
        getActivity().registerReceiver(receiver, filter);

        receiveBroadcast = new ReceiveBroadcastReceiver();
        getActivity().registerReceiver(receiveBroadcast, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
        getActivity().unregisterReceiver(receiveBroadcast);
    }

    @AfterPermissionGranted(RC_SEND_SMS)
    private void checkSmsPerm() {
        permissions = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};

        if ((EasyPermissions.hasPermissions(getActivity(), permissions[0]))) {
            canSendSMS = true;

        }
        if ((EasyPermissions.hasPermissions(getActivity(), permissions[1]))) {
            canPhoneState = true;
        }

        if (!canPhoneState || !canSendSMS) {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_no_permission), RC_SEND_SMS, permissions);
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

        checkPrems(perms, true);
    }

    private void checkPrems(List<String> perms, boolean isGranted) {

        if (perms.contains(permissions[0])) {
            canSendSMS = isGranted;

        }
        if (perms.contains(permissions[1])) {
            canPhoneState = isGranted;
        }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        checkPrems(perms, false);
    }


    @Override
    public void onProgressUpdate(int percentage) {
     //   adapter.setProgress(percentage);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onFinish() {
       // adapter.progressBar.setVisibility(View.GONE);
    }
}
