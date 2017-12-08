package itg8.com.wmcapp.complaint;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonCallback;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.ProgressRequestBody;
import itg8.com.wmcapp.common.ReceiveBroadcastReceiver;
import itg8.com.wmcapp.common.SentBroadCastReceiver;
import itg8.com.wmcapp.complaint.model.ComplaintCategoryModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.registration.RegistrationModel;
import itg8.com.wmcapp.utility.FetchAddressIntentService;
import itg8.com.wmcapp.utility.compressor.Compressor;
import itg8.com.wmcapp.utility.easyimg.DefaultCallback;
import itg8.com.wmcapp.utility.easyimg.EasyImage;
import itg8.com.wmcapp.utility.easyimg.EasyImageFiles;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddComplaintFragment extends Fragment implements EasyPermissions.PermissionCallbacks, View.OnClickListener, CommonCallback.OnDialogClickListner, CommonMethod.ResultListener, CompoundButton.OnCheckedChangeListener, ProgressRequestBody.UploadCallbacks {
    public static final String TAG = AddComplaintFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_STORAGE_CAMERA = 100;
    private static final String FILE_PATH = "FILE_PATH";
    private static final String DESCRIPTION = "Description";
    private static final String ADDRESS = "ADDRESS";
    private static final String IDENTITY = "identity";
    @BindView(R.id.imgAdd)
    ImageView imgAdd;
    @BindView(R.id.imgPreview)
    ImageView imgPreview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgMoreMenu)
    ImageView imgMoreMenu;
    @BindView(R.id.frmPreview)
    FrameLayout frmPreview;
    @BindView(R.id.edt_add_complaint)
    EditText edtAddComplaint;
    @BindView(R.id.edtAddress)
    TextInputLayout edtAddress;
    @BindView(R.id.edt_desc_complaint)
    EditText edtDescComplaint;
    @BindView(R.id.edtDescription2)
    TextInputLayout edtDescription2;
    @BindView(R.id.rdoShowIdentity)
    RadioButton rdoShowIdentity;
    @BindView(R.id.rdoHideIdentity)
    RadioButton rdoHideIdentity;
    @BindView(R.id.rgIdentity)
    RadioGroup rgIdentity;
    @BindView(R.id.radioButton)
    RadioButton radioButton;
    @BindView(R.id.radioButton2)
    RadioButton radioButton2;
    @BindView(R.id.rgLocation)
    RadioGroup rgLocation;
    @BindView(R.id.btn_submit)
    FloatingActionButton btnSubmit;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    @BindView(R.id.frame)
    FrameLayout frame;
     CommonMethod.OnBackPressListener onBackPressListener;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean canAccessCamera = false;
    private CommonCallback.OnImagePickListener listener;
    private AddressResultReceiver mResultReceiver;
    private boolean canAccessLocation;
    private String lastAddressResult;
    private LatLng latlang;
    private File selectedFile;
    private CityTableManipulate mDAOCity;
    private int cityId = 0;
    private boolean canSendSMS;
    private Context mContext;
    private SentBroadCastReceiver receiver;
    private ReceiveBroadcastReceiver receiveBroadcast;


    String description = null;
    String address = null;
    String identity;
    double latitude;
    double longitude;
    private String fileName;
    private AlertDialog alertDialog;

    public CommonMethod.OnMoveComplaintListener mComplaintlistener;

    private boolean isDestroyed = false;
    private Unbinder unbinder;
    private boolean canPhoneState;
    private String[] permissions;
    private ComplaintCategoryModel modelCategory;


    public AddComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param categoryModel Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddComplaintFragment newInstance(ComplaintCategoryModel categoryModel, String param2) {
        AddComplaintFragment fragment = new AddComplaintFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, categoryModel);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            modelCategory = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedFile != null)
            outState.putString(FILE_PATH, selectedFile.getAbsolutePath());
        if (description != null)
            outState.putString(DESCRIPTION, description);
        if (address != null) {
            outState.putString(ADDRESS, address);
        }
        if (rdoShowIdentity == null)
            return;
        if (rdoShowIdentity.isChecked())
            identity = "YES";
        else
            identity = "NO";
        outState.putString(IDENTITY, identity);
        Logs.d("onSaveInstanceState");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            Logs.d("onViewStateRestored");

            if (savedInstanceState.getString(FILE_PATH, null) != null)
                selectedFile = new File(savedInstanceState.getString(FILE_PATH, ""));
            if (savedInstanceState.getString(DESCRIPTION, null) != null)
                edtDescComplaint.setText(savedInstanceState.getString(DESCRIPTION, ""));
            if (savedInstanceState.getString(ADDRESS, null) != null)
                edtAddComplaint.setText(savedInstanceState.getString(ADDRESS, ""));
            if (savedInstanceState.getString(IDENTITY, null) != null) {
                String identity = savedInstanceState.getString(IDENTITY, null);
                if (identity.equalsIgnoreCase("YES")) {
                    rdoShowIdentity.setChecked(true);
                } else {
                    rdoHideIdentity.setChecked(true);
                }
            }
        }
    }

    //this is comment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
        checkStoragePerm();
        isDestroyed = true;

        EasyImage.configuration(getActivity())
                .setImagesFolderName(getString(R.string.app_name))
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(false);

        imgAdd.setOnClickListener(this);
        imgMoreMenu.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        mResultReceiver = new AddressResultReceiver(new Handler());
        mDAOCity = new CityTableManipulate(getActivity());

        Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);
        intent.putExtra(CommonMethod.RECEIVER, mResultReceiver);
        getActivity().startService(intent);


        radioButton.setOnCheckedChangeListener(this);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mResultReceiver.setResultListener(this);
        IntentFilter filter = new IntentFilter(CommonMethod.SENT);
        receiver = new SentBroadCastReceiver();
        getActivity().registerReceiver(receiver, filter);
        receiveBroadcast = new ReceiveBroadcastReceiver();
        getActivity().registerReceiver(receiveBroadcast, filter);


    }

    @Override
    public void onPause() {
        super.onPause();
        mResultReceiver.setResultListener(null);
        getActivity().unregisterReceiver(receiver);
        getActivity().unregisterReceiver(receiveBroadcast);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) getActivity()).setDialogCallbackListener(this);

        if (context instanceof CommonCallback.OnImagePickListener) {
            listener = (CommonCallback.OnImagePickListener) context;
        }
        mComplaintlistener = (CommonMethod.OnMoveComplaintListener) context;
        onBackPressListener = (CommonMethod.OnBackPressListener) context;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                Logs.d("OnImagePicked :");
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void onPhotosReturned(List<File> imageFiles) {
        if (imageFiles != null && imageFiles.size() > 0) {
            File f = imageFiles.get(imageFiles.size() - 1);
            Log.d(TAG, "imageFile : " + f.getAbsolutePath() + " size : " + (f.length() / 1024) + " MB");
            Picasso.with(getActivity()).load(f).into(imgPreview);

            try {
                File compressImage = new Compressor(getContext()).compressToFile(f);
                Log.d(TAG, "imageFile : " + compressImage.getAbsolutePath() + " size : " + (compressImage.length() / 1024) + " MB");
                selectedFile = compressImage;
            } catch (IOException e) {
                e.printStackTrace();
            }
            showPreviewImage();
        }

    }


    public void onClick(View v) {
        if (v == imgMoreMenu) {
            final PopupMenu popup = new PopupMenu(getActivity(), v);
            popup.getMenuInflater().inflate(R.menu.popmenu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.menu_remove) {
                        clearImage();
                    } else if (item.getItemId() == R.id.menu_replace) {
                        replaceImage();
                    }

                    return true;
                }

            });

            popup.show();
        } else if (v.getId() == R.id.imgAdd) {
            showDialog();
        } else if (v.getId() == R.id.btn_submit) {
            uploadToServer();
        }
    }

    private void uploadToServer() {
        String description = null;
        String address = null;
        String identity;
        double latitude;
        double longitude;
        if (TextUtils.isEmpty(edtDescComplaint.getText().toString())) {
            edtDescription2.setError("Please provide some description");
            edtDescription2.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(edtAddComplaint.getText().toString())) {
            edtAddress.setError("Please provide address");
            edtAddress.requestFocus();
            return;
        }
        if (selectedFile == null) {
            Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }
        address = edtAddComplaint.getText().toString();
        description = edtDescComplaint.getText().toString();
        if (rdoShowIdentity.isChecked()) {
            identity = "YES";
        } else {
            identity = "NO";
        }
        if (latlang == null) {
            latitude = 0;
            longitude = 0;
        } else {
            latitude = latlang.latitude;
            longitude = latlang.longitude;
        }

        provideToServer(description, address, cityId, identity, latitude, longitude);


    }

    private void provideToServer(final String description, final String address, final int cityId, final String identity, final double latitude, final double longitude) {
        ProgressRequestBody prb = new ProgressRequestBody(selectedFile, this);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", selectedFile.getName(), prb);
        RequestBody lat = createPartFromString(String.valueOf(latitude));
        RequestBody lang = createPartFromString(String.valueOf(longitude));
        RequestBody addr = createPartFromString(String.valueOf(address));
        RequestBody desc = createPartFromString(String.valueOf(description));
        //TODO changes: temporary city id;
        RequestBody city = createPartFromInt(cityId);
        RequestBody ident = createPartFromString(identity);
        RequestBody category = createPartFromInt(modelCategory.getPkid());
        Observable<ResponseBody> call = MyApplication.getInstance().getRetroController().addComplaint(getString(R.string.url_add_complaint), part, lat, lang, addr, desc, city, ident, category);
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
                        hideProgress();
                        mComplaintlistener.moveComplaint();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if(e instanceof NoConnectivityException) {
                            setDataToModel(latitude, longitude, address, description, cityId, identity, selectedFile);
                        }else
                        {

                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setDataToModel(final double latitude, final double longitude, final String address, final String description, final int cityId, final String identity, File file) {

        final TempComplaintModel model = new TempComplaintModel();
        model.setAdd(address);
        model.setDescription(description);
        model.setCityId(cityId);
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        model.setShowIdentity(identity);
        model.setComplaintName(modelCategory.getCategoryName());
        model.setLastModifiedDate(CommonMethod.formatter.format(Calendar.getInstance().getTime()));

        Logs.d("file:" + file);
        List<File> files = new ArrayList<>();
        files.add(file);
        EasyImageFiles.copyFilesInSeparateThread(getActivity(), files, new CommonMethod.OnImageFileListner() {
            @Override
            public void onGetImageFileSucces(String file) {
                Logs.d("FileName:" + file);
                model.setFilePath(file);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {

                        openDialogue(model);
                    }

                });
            }

            @Override
            public void onGetImageFileFailed(String s) {
                Logs.d("OnFailed:" + s);
                model.setFilePath(null);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        openDialogue(model);


                    }
                });
            }
        });


    }

    private void openDialogue(final TempComplaintModel model) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set title
        alertDialogBuilder.setTitle(getString(R.string.no_internet_title));

        // set dialog message
        alertDialogBuilder
                .setMessage(getString(R.string.message_dialogue))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.immediately), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        Logs.d("immediately ");
                         if(canSendSMS && canPhoneState)
                         {
                             SendSMS("9823778532", generateSMSText(model));
                             dialog.dismiss();
                             mComplaintlistener.moveComplaint();
                         }else
                         {
                             checkStoragePerm();
                         }


                    }
                })
                .setNegativeButton(getString(R.string.later), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        MyApplication.getInstance().saveComplaintModel(model);
                        dialog.dismiss();
                        mComplaintlistener.moveComplaint();


                    }
                });

        // create alert dialog
        alertDialog = alertDialogBuilder.create();

        // show it

        alertDialog.show();


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
        builder = "Dear Sir, " + model.getComplaintName() + "\n  We  have this problem "
                + model.getDescription() + "\n In this location" + model.getAdd();
        return builder;

    }


    private void hideProgress() {
        if (!isDestroyed)
            progressBar.setVisibility(View.GONE);
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
    public void onDetach() {
        super.onDetach();
        listener = null;
        onBackPressListener = null;
    }

    private void showDialog() {
        if (listener != null)
            listener.onImagePickClick();
    }

    private void clearImage() {
        imgPreview.setVisibility(View.INVISIBLE);
        imgAdd.setVisibility(View.VISIBLE);
        selectedFile = null;
    }

    private void showPreviewImage() {
        imgPreview.setVisibility(View.VISIBLE);
        imgAdd.setVisibility(View.INVISIBLE);
    }

    private void replaceImage() {
        clearImage();
        imgAdd.callOnClick();
    }


    @AfterPermissionGranted(RC_STORAGE_CAMERA)
    private void checkStoragePerm() {
     permissions =new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};
        if (EasyPermissions.hasPermissions(getActivity(), permissions[0])
        && (EasyPermissions.hasPermissions(getActivity(), permissions[1]))) {
            canAccessCamera = true;

        }
        if((EasyPermissions.hasPermissions(getActivity(), permissions[2]))){

            canAccessLocation = true;
        }
        if((EasyPermissions.hasPermissions(getActivity(), permissions[3])))
        {canSendSMS = true;

        }
         if((EasyPermissions.hasPermissions(getActivity(), permissions[4])))
         {
             canPhoneState = true;
         }

          if(!canPhoneState || !canSendSMS || !canAccessCamera || !canAccessLocation)
        {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_no_permission), RC_STORAGE_CAMERA, permissions);
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


            if (perms.contains(permissions[0])
                    && perms.contains(permissions[1])) {
                canAccessCamera = isGranted;

            }
            if (perms.contains(permissions[2])) {

                canAccessLocation = isGranted;
            }
            if (perms.contains(permissions[3])) {
                canSendSMS = isGranted;

            }
            if (perms.contains(permissions[4])) {
                canPhoneState = isGranted;
            }

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

         checkPrems(perms,false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDestroyed = true;
    }

    @Override
    public void onOpenCamera() {
        EasyImage.openCamera(this, 0);
    }

    @Override
    public void onSelectFromFileManager() {
        EasyImage.openGallery(this, 1);
    }

    @Override
    public void onResultAddress(String result, LatLng mLocation, final String city) {
        this.lastAddressResult = result;
        edtAddComplaint.setText(result);
        this.latlang = mLocation;
        if (city != null) {
            if (mDAOCity != null) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        CityModel cityModel = mDAOCity.getCity(city, CityModel.FIELD_NAME);
                        if (cityModel != null)
                            e.onNext(cityModel.getID());
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                AddComplaintFragment.this.cityId = integer;
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
        }
//        mResultReceiver.setResultListener(null);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            if (lastAddressResult != null) {
                edtAddComplaint.setText(lastAddressResult);
            }
        } else {
            edtAddComplaint.setText(null);
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        progressBar.setProgress(percentage);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onFinish() {
        progressBar.setVisibility(View.GONE);
    }


    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    private static class AddressResultReceiver extends ResultReceiver {
        CommonMethod.ResultListener listener;
        private String mAddressOutput;

        AddressResultReceiver(Handler handler) {
            super(handler);
        }


        public void setResultListener(CommonMethod.ResultListener listener) {
            this.listener = listener;
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            mAddressOutput = resultData.getString(CommonMethod.RESULT_DATA_KEY);
            LatLng mLocation = resultData.getParcelable(CommonMethod.LOCATION_DATA_EXTRA);
            String city = resultData.getString(CommonMethod.CITY);
            if (city != null)
                Logs.d("CITY: ", city);
            if (listener != null)
                listener.onResultAddress(mAddressOutput, mLocation, city);

            // Show a toast message if an address was found.
            if (resultCode == CommonMethod.SUCCESS_RESULT) {
//                showToast(getString(R.string.address_found));
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
//            updateUIWidgets();
        }


    }


}
