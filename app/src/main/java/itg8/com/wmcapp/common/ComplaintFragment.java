package itg8.com.wmcapp.common;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.utility.easyimg.DefaultCallback;
import itg8.com.wmcapp.utility.easyimg.EasyImage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment implements EasyPermissions.PermissionCallbacks, View.OnClickListener,CommonCallback.OnDialogClickListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = ComplaintFragment.class.getSimpleName();
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean canAccessCamera = false;
    private CommonCallback.OnImagePickListener listener;


    public ComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintFragment newInstance(String param1, String param2) {
        ComplaintFragment fragment = new ComplaintFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
        checkStoragePerm();

        EasyImage.configuration(getActivity())
                .setImagesFolderName(getString(R.string.app_name))
                .setAllowMultiplePickInGallery(false);
/*
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
*/

        imgAdd.setOnClickListener(this);
        imgMoreMenu.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CommonCallback.OnImagePickListener){
            listener=(CommonCallback.OnImagePickListener)context;
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
       if(imageFiles!=null && imageFiles.size()>0){
           File f=imageFiles.get(imageFiles.size()-1);
           Log.d(TAG, "imageFile : " + f.getAbsolutePath());
            Picasso.with(getActivity()).load(f).into(imgPreview);
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
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    private void showDialog() {
        if(listener!=null)
            listener.onImagePickClick();
    }

    private void clearImage() {
        imgPreview.setVisibility(View.INVISIBLE);
        imgAdd.setVisibility(View.VISIBLE);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onOpenCamera() {
        EasyImage.openCamera(this,0);
    }

    @Override
    public void onSelectFromFileManager() {
        EasyImage.openGallery(this,1);
    }
}
