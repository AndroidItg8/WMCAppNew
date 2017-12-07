package itg8.com.wmcapp.suggestion;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonCallback;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.ProgressRequestBody;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.registration.RegistrationModel;
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
 * Use the {@link SuggestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestionFragment extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks, ProgressRequestBody.UploadCallbacks, CommonCallback.OnDialogClickListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_STORAGE_CAMERA = 123;
    @BindView(R.id.imgAdd)
    ImageView imgAdd;
    @BindView(R.id.imgPreview)
    ImageView imgPreview;
    @BindView(R.id.imgMoreMenu)
    ImageView imgMoreMenu;
    @BindView(R.id.frmPreview)
    FrameLayout frmPreview;

    Unbinder unbinder;
    @BindView(R.id.txt_title)
    EditText txtTitle;
    @BindView(R.id.input_layout_title)
    TextInputLayout inputLayoutTitle;
    @BindView(R.id.txt_rating_desc)
    EditText txtRatingDesc;
    @BindView(R.id.input_layout_des)
    TextInputLayout inputLayoutDes;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.fab_login)
    FloatingActionButton fabLogin;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    @BindView(R.id.frame)
    FrameLayout frame;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String title;
    private String description;
    private File selectedFile;
    private CommonCallback.OnImagePickListener listener;
    CommonMethod.onSetToolbarTitle titleListener;
    CommonMethod.OnBackPressListener mBackPressListener;
    private boolean canAccessCamera;


    public SuggestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestionFragment newInstance(String param1, String param2) {
        SuggestionFragment fragment = new SuggestionFragment();
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
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        unbinder = ButterKnife.bind(this, view);
        imgAdd.setOnClickListener(this);
        fabLogin.setOnClickListener(this);
        imgMoreMenu.setOnClickListener(this);
        titleListener.onSetTitle(getString(R.string.suggestion));
        checkStoragePerm();
        return view;
    }

    private void uploadToServer() {
        title = txtTitle.getText().toString();
        description = txtRatingDesc.getText().toString();
        if (Validate()) {
            showProgress();
            provideToServer(title, description);
        }
    }

    private void showProgress() {
        progressView.setVisibility(View.VISIBLE);
    }

    private boolean Validate() {
        title = txtTitle.getText().toString();
        description = txtRatingDesc.getText().toString();
        boolean isValidate = true;


        if (TextUtils.isEmpty(txtRatingDesc.getText().toString())) {
            txtRatingDesc.setError(getString(R.string.empty));
            txtRatingDesc.requestFocus();
            isValidate = false;
        }
        if (TextUtils.isEmpty(txtTitle.getText().toString())) {
            txtTitle.setError(getString(R.string.empty));
            txtTitle.requestFocus();
            isValidate = false;
        }
        if (selectedFile == null) {
            Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
            isValidate = false;
        }


        return isValidate;
    }


    private void provideToServer(final String title, String description) {
        ProgressRequestBody prb = new ProgressRequestBody(selectedFile, this);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", selectedFile.getName(), prb);

        RequestBody ti = createPartFromString(String.valueOf(title));
        RequestBody desc = createPartFromString(String.valueOf(description));
        //TODO changes: temporary city id;
        Observable<ResponseBody> call = MyApplication.getInstance().getRetroController().addSuggestion(getString(R.string.url_add_suggestion), part, ti, desc);
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
                        CommonMethod.clearText(txtTitle);
                        CommonMethod.clearText(txtRatingDesc);
                        mBackPressListener.onBackPress();





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
        progressView.setVisibility(View.GONE);
    }

    private RequestBody createPartFromString(String val) {
        return RequestBody.create(MediaType.parse("text/plain"), val);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    @Override
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
        } else if (v.getId() == R.id.fab_login) {
            uploadToServer();
        }
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

    private void showDialog() {
        if (listener != null)
            listener.onImagePickClick();
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
    public void onProgressUpdate(int percentage) {
        progressView.setProgress(percentage);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onFinish() {
        progressView.setVisibility(View.GONE);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) getActivity()).setDialogCallbackListener(this);

        if (context instanceof CommonCallback.OnImagePickListener) {
            listener = (CommonCallback.OnImagePickListener) context;
        }
        mBackPressListener = (CommonMethod.OnBackPressListener) context;
        titleListener = (CommonMethod.onSetToolbarTitle) context;
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
}
