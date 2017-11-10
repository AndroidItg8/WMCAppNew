package itg8.com.wmcapp.complaint;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
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
import itg8.com.wmcapp.common.ProgressRequestBody;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.registration.RegistrationModel;
import itg8.com.wmcapp.utility.FetchAddressIntentService;
import itg8.com.wmcapp.utility.compressor.Compressor;
import itg8.com.wmcapp.utility.easyimg.DefaultCallback;
import itg8.com.wmcapp.utility.easyimg.EasyImage;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = AddComplaintFragment.class.getSimpleName();
    private static final int RC_STORAGE_CAMERA = 100;
    @BindView(R.id.edtAddress)
    EditText edtAddress;
    @BindView(R.id.edtDescription2)
    EditText edtDescription2;
    @BindView(R.id.rdoShowIdentity)
    RadioButton rdoShowIdentity;
    @BindView(R.id.rdoHideIdentity)
    RadioButton rdoHideIdentity;
    @BindView(R.id.rgIdentity)
    RadioGroup rgIdentity;
    @BindView(R.id.radioButton)
    RadioButton rdoCurrentAddress;
    @BindView(R.id.radioButton2)
    RadioButton rdoOtherAddress;
    @BindView(R.id.rgLocation)
    RadioGroup rgLocation;
    Unbinder unbinder;
    @BindView(R.id.imgAdd)
    ImageView imgAdd;
    @BindView(R.id.imgMoreMenu)
    ImageView imgMoreMenu;
    @BindView(R.id.imgPreview)
    ImageView imgPreview;
    @BindView(R.id.frmPreview)
    FrameLayout frmPreview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

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
    private int cityId=0;


    public AddComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddComplaintFragment newInstance(String param1, String param2) {
        AddComplaintFragment fragment = new AddComplaintFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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


        rdoCurrentAddress.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mResultReceiver.setResultListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mResultReceiver.setResultListener(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) getActivity()).setDialogCallbackListener(this);

        if (context instanceof CommonCallback.OnImagePickListener) {
            listener = (CommonCallback.OnImagePickListener) context;
        }
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
        if (v.getId() == R.id.imgMoreMenu) {
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
        if (TextUtils.isEmpty(edtDescription2.getText().toString())) {
            edtDescription2.setError("Please provide some description");
            edtDescription2.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            edtAddress.setError("Please provide address");
            edtAddress.requestFocus();
            return;
        }
        if (selectedFile == null) {
            Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }
        address = edtAddress.getText().toString();
        description = edtDescription2.getText().toString();
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

    private void provideToServer(String description, String address, int cityId, String identity, double latitude, double longitude) {
        ProgressRequestBody prb = new ProgressRequestBody(selectedFile, this);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", selectedFile.getName(), prb);
        RequestBody lat = createPartFromString(String.valueOf(latitude));
        RequestBody lang = createPartFromString(String.valueOf(longitude));
        RequestBody addr = createPartFromString(String.valueOf(address));
        RequestBody desc = createPartFromString(String.valueOf(description));
        //TODO changes: temporary city id;
        RequestBody city = createPartFromInt(cityId);
        RequestBody ident = createPartFromString(identity);
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
                        hideProgress();
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

    private void hideProgress() {
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
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(getActivity(), permissions)) {
            canAccessCamera = true;
            canAccessLocation = true;
        } else {
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
        for (String perm :
                perms) {
            if (!perm.equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                canAccessCamera = false;
            }
            if (!perm.equalsIgnoreCase(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                canAccessCamera = false;
            }
            if (!perm.equalsIgnoreCase(Manifest.permission.ACCESS_FINE_LOCATION)) {
                canAccessLocation = false;
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        canAccessCamera = false;
        canAccessLocation = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        edtAddress.setText(result);
        this.latlang = mLocation;
        if(city!=null)
        {
            if (mDAOCity != null) {
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        CityModel cityModel = mDAOCity.getCity(city,CityModel.FIELD_NAME);
                        if(cityModel!=null)
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
                                AddComplaintFragment.this.cityId=integer;
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
                edtAddress.setText(lastAddressResult);
            }
        } else {
            edtAddress.setText(null);
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
