package itg8.com.wmcapp.profile;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
import itg8.com.wmcapp.cilty.CityFragment;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonCallback;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.database.BaseDatabaseHelper;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.profile.mvp.ProfileMVp;
import itg8.com.wmcapp.profile.mvp.ProfilePresenterImp;
import itg8.com.wmcapp.signup.LoginActivity;
import itg8.com.wmcapp.utility.compressor.Compressor;
import itg8.com.wmcapp.utility.easyimg.DefaultCallback;
import itg8.com.wmcapp.utility.easyimg.EasyImage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements ProfileMVp.ProfileView, View.OnClickListener, EasyPermissions.PermissionCallbacks, CommonCallback.OnDialogClickListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FROM_INTERNET = 2;
    private static final int FROM_ERROR = 1;
    private static final int RC_STORAGE_CAMERA = 234;
CommonMethod.onSetToolbarTitle titleListener;

    Unbinder unbinder;
    @BindView(R.id.lbl_profile)
    TextView lblProfile;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.input_mobile)
    EditText inputMobile;
    @BindView(R.id.input_layout_mobile)
    TextInputLayout inputLayoutMobile;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_address)
    EditText inputAddress;
    @BindView(R.id.input_layout_address)
    TextInputLayout inputLayoutAddress;
    @BindView(R.id.input_city)
    EditText inputCity;
    @BindView(R.id.input_layout_city)
    TextInputLayout inputLayoutCity;
    @BindView(R.id.rl_registration)
    LinearLayout rlRegistration;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.fab_update)
    FloatingActionButton fabUpdate;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    @BindView(R.id.frame)
    FrameLayout frame;
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
    ProfileModel profileModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProfileMVp.ProfilePresenter presenter;
    private Snackbar snackbar;
    private Dao<CityModel, Integer> mDAOCity = null;
    private List<CityModel> cityList;
    private CityModel cityModel;
    private int id;
    private boolean canAccessCamera = false;
    private File selectedFile;
    private CommonCallback.OnImagePickListener listener;
    private List<ProfileModel> list;
    private String address;
    private boolean isViewDestroyed;
    private boolean hasProfileDownloadStarted;
    private CityTableManipulate manipulate;
     private  ActivityFinishListener mFinishedListener;
    private String filePath;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        Logs.d("LF Fragment: onCreate()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("cityList", (ArrayList<? extends Parcelable>) cityList);
        prepareData();
        outState.putParcelable("listProfile", profileModel);



    }

    private void prepareData() {
        if (!isViewDestroyed) {
            profileModel.setAddressLine1(inputAddress.getText().toString());
//            if(profileModel.getAddressLine2()!= null)
//                profileModel.setAddressLine2();
        }
    }

    private void arrangeData() {
        if (!isViewDestroyed) {
            inputAddress.post(new Runnable() {
                @Override
                public void run() {
                    inputAddress.setText(profileModel.getAddressLine1());
                }
            });

            inputCity.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        setCityNameById(Integer.parseInt(profileModel.getAddressLine2()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
             imgPreview.post(new Runnable() {
                 @Override
                 public void run() {
                     Picasso.with(getActivity()).load(CommonMethod.BASE_URL + profileModel.getPicProfle()).into(imgPreview);

                 }
             });



        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            profileModel = savedInstanceState.getParcelable("listProfile");
            filePath = savedInstanceState.getString("fileName");
            arrangeData();
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        Logs.d("LF Fragment: onCreateView()");
        checkStoragePerm();
        titleListener.onSetTitle(getString(R.string.profile));

        isViewDestroyed = false;

        presenter = new ProfilePresenterImp(this);
       manipulate = new CityTableManipulate(getActivity());
        if (Prefs.getInt(CommonMethod.SELECTED_CITY) > 0) {
            setCityNameById(Prefs.getInt(CommonMethod.SELECTED_CITY));

        }
        if (profileModel== null) {
            hasProfileDownloadStarted = true;
            presenter.onGetProfileList(getString(R.string.url_profile));
        }

        init();
        return view;
    }

    private void setCityNameById(int cityId) {
        cityModel = manipulate.getCity(String.valueOf(cityId), CityModel.FIELD_ID);
        if (cityModel != null && profileModel!= null) {
            Logs.d("CItyModel:"+new Gson().toJson(cityModel));

            profileModel.setAddressLine2(String.valueOf(cityModel.getID()));
            inputCity.post(new Runnable() {
                @Override
                public void run() {
                    inputCity.setText(cityModel.getName());
                }
            });

        }
    }

    private void showL(String method) {
        Logs.d("LF Fragment: " + method + "()");

    }


    private void init() {
        if (profileModel == null)
            presenter.onGetCityList(getString(R.string.url_city));

        EasyImage.configuration(getActivity())
                .setImagesFolderName(getString(R.string.app_name))
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(false);

        imgAdd.setOnClickListener(this);
        imgMoreMenu.setOnClickListener(this);
        fabUpdate.setOnClickListener(this);


    }

    private void showSnackbar(boolean isConnected, int from, String message) {

        int color = 0;
        if (from == FROM_INTERNET) {
            if (!isConnected) {

                color = Color.WHITE;
                hideSnackbar();

            } else {
                color = Color.RED;
            }
        }else
        {
            color = Color.WHITE;
        }
        snackbar = Snackbar
                .make(fabUpdate, message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        textView.setMaxLines(2);
        snackbar.show();


        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSnackbarOkClicked(view);

            }
        });
        snackbar.show();
    }

    private void onSnackbarOkClicked(View view) {
//        presenter.onGetProfileList(getString(R.string.url_profile));
         mFinishedListener.onActivityFinish();

    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    @Override
    public void onSuccess(List<ProfileModel> list) {
        this.list = list;

        profileModel = list.get(0);
        MyApplication.getInstance().setProfileModel(profileModel);
        Prefs.putString(CommonMethod.USER_NAME,profileModel.getFullName());
        Prefs.putString(CommonMethod.USER_MOBILE,profileModel.getContactNumber());

        inputEmail.setText(CommonMethod.checkEmpty(profileModel.getEmailId()));
        inputMobile.setText(CommonMethod.checkEmpty(profileModel.getContactNumber()));
        inputName.setText(CommonMethod.checkEmpty(profileModel.getFullName()));
        if(TextUtils.isEmpty(profileModel.getAddressLine1()))
        inputAddress.setHint(CommonMethod.checkEmpty(profileModel.getAddressLine1()));
        else
            inputAddress.setText(CommonMethod.checkEmpty(profileModel.getAddressLine1()));

        if(profileModel.getAddressLine2()!= null)
            setCityNameById(Integer.parseInt(profileModel.getAddressLine2()));
        if(profileModel.getPicProfle()!= null)
            Picasso.with(getActivity()).load(CommonMethod.BASE_URL + profileModel.getPicProfle()).into(imgPreview);


        inputCity.setOnClickListener(this);
    }

    @Override
    public void onSuccessCityList(final List<CityModel> list) {
        if (!hasProfileDownloadStarted)
            presenter.onGetProfileList(getString(R.string.url_profile));


        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(storeCity(list));
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Logs.d("Stored in db");
                        } else {
                            Logs.d("Fail to store in db");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {


                    }
                });
//        try {
//            saveBrandToDatabase(list);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        this.cityList = list;


    }

    private boolean storeCity(List<CityModel> list) {
        try {
            saveBrandToDatabase(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public void onFail(String message) {
        showSnackbar(false, FROM_ERROR, message);

    }

    @Override
    public void onError(Object t) {
        showSnackbar(false, FROM_ERROR, t.toString());

    }


    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onNoInternetConnect(boolean b) {
        showSnackbar(b, FROM_INTERNET, "No InternetConnection");

    }

    @Override
    public void onInternetConnect(boolean b) {
        // showSnackbar(b);

    }

    @Override
    public void onProgressUpdate(int percentage) {
        progressBar.setProgress(percentage);

    }

    @Override
    public void onFinished() {
         progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onSuccessSave(String status) {
        showSnackbar(false, FROM_ERROR, status);
    }

    @Override
    public ProfileModel getProfileModel() {
        return profileModel;
    }

    @Override
    public File getImageFile() {
        return selectedFile;
    }

    @Override
    public void onStartLoginActivity() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


    private void setError(EditText view, String error) {
        view.setError(error);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logs.d("LF Fragment: onDetach()");
        titleListener =null;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewDestroyed = true;
        showL("onDestroyView");

        presenter.onDestroy();

        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_update:
                profileModel.setEmailId(inputEmail.getText().toString());
                profileModel.setAddressLine1(inputAddress.getText().toString());
                presenter.onUpdateButtonClicked(view);
                break;
            case R.id.input_city:
                callCityFragment();
                break;
            case R.id.imgMoreMenu:
                final PopupMenu popup = new PopupMenu(getActivity(), view);
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
                break;
            case R.id.imgAdd:
                showDialog();
                break;

        }
    }

    private void callCityFragment() {
        prepareData();
        CityFragment fragmentCity = CityFragment.newInstance(cityList, "");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container, fragmentCity);
        ft.addToBackStack(fragmentCity.getClass().getSimpleName());
        ft.commit();
    }

    private void saveBrandToDatabase(List<CityModel> list) throws SQLException {
        try {

            mDAOCity = BaseDatabaseHelper.getBaseInstance().getHelper(getActivity()).getmDAOCity();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (mDAOCity != null) {
            BaseDatabaseHelper.getBaseInstance().clearCityTable();


            for (CityModel model : list) {
                try {
                    int id = mDAOCity.create(model);
//                    cityList = mDAOCity
//                            .queryBuilder()
//                            .where()
//                            .eq(CityModel.FIELD_ID, model.getID())
//                            .query();
//
//                    Logs.d("CityList:" + new Gson().toJson(cityList));


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


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
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(getActivity(), permissions)) {
            canAccessCamera = true;

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

        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        canAccessCamera = false;

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
            Logs.d("imageFile : " + f.getAbsolutePath() + " size : " + (f.length() / 1024) + " MB");
            Picasso.with(getActivity()).load(f).into(imgPreview);

            try {
                File compressImage = new Compressor(getContext()).compressToFile(f);
                Logs.d("imageFile : " + compressImage.getAbsolutePath() + " size : " + (compressImage.length() / 1024) + " MB");
                selectedFile = compressImage;
            } catch (IOException e) {
                e.printStackTrace();
            }
            showPreviewImage();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showL("OnAttach");
        ((ProfileActivity) getActivity()).setDialogCallbackListener(this);

        if (context instanceof CommonCallback.OnImagePickListener) {
            listener = (CommonCallback.OnImagePickListener) context;
        }
        titleListener = (CommonMethod.onSetToolbarTitle) context;
        mFinishedListener = (ActivityFinishListener) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        showL("onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        showL("onResume");

    }


    @Override
    public void onPause() {
        super.onPause();
        showL("onPause");

    }

    @Override
    public void onStart() {
        super.onStart();
        if(profileModel!= null)
        {
            arrangeData();
        }
        showL("onStart");

    }

    @Override
    public void onStop() {
        super.onStop();
        showL("onStop");

    }
     public interface ActivityFinishListener
    {
        void onActivityFinish();
    }

}
