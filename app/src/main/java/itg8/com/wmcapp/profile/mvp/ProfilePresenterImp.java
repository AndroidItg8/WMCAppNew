package itg8.com.wmcapp.profile.mvp;


import android.view.View;

import java.io.File;
import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.common.ProgressRequestBody;
import itg8.com.wmcapp.profile.ProfileModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Android itg 8 on 10/14/2017.
 */

public class ProfilePresenterImp extends BaseWeakPresenter<ProfileMVp.ProfileView> implements  ProfileMVp.ProfilePresenter, ProfileMVp.ProfileListener, ProgressRequestBody.UploadCallbacks {

    private final ProfileMVp.ProfileModule module;

    public ProfilePresenterImp(ProfileMVp.ProfileView profileView) {
        super(profileView);
         module = new ProfileModuleImp();
        Logs.d("Token Header: "+ Prefs.getString(CommonMethod.HEADER));
    }

    @Override
    public void onDestroy() {
        module.onDestroy();
        if(hasView())
        {
           detachView();
        }
    }

    @Override
    public void onGetProfileList(String url) {
        if(hasView()) {
             showProgress();
            module.onGetProfileListFromServer(MyApplication.getInstance().getRetroController(),url, this);
        }
    }

    @Override
    public void onSuccess(List<ProfileModel> list) {
        if(hasView())
        {
            hideProgress();
            if(list.size()>0)
                getView().onSuccess(list);
            else
                getView().onFail("Download Failed");

        }
    }

    @Override
    public void onGetCityList(String url) {
        if(hasView()) {
            showProgress();
            module.onGetCityListFromServer(MyApplication.getInstance().getRetroController(),url, this);
        }
    }

    @Override
    public void onSuccessCity(List<CityModel> list) {
        if(hasView())
        {
            hideProgress();
            getView().onSuccessCityList(list);

        }

    }

    @Override
    public void onFail(String message) {
        if(hasView())
        {
            hideProgress();
            getView().onFail(message);

        }



    }

    @Override
    public void onError(Object t) {
        if(hasView())
        {    hideProgress();
            getView().onError(t);

        }


    }

    @Override
    public void showProgress() {
        if(hasView())
        {
            getView().showProgress();

        }


    }

    @Override
    public void hideProgress() {
        if(hasView())
        {
            getView().hideProgress();

        }
    }
    @Override
    public void onNoInternetConnect(boolean b) {
        if(hasView())
        {
            hideProgress();
            getView().onNoInternetConnect(b);
        }

    }

    @Override
    public void onSaveSuccess(String status) {
        if(hasView())
        {
            hideProgress();
            getView().onSuccessSave(status);
        }

    }

    @Override
    public void onStartLoginActivity() {
        if(hasView())
        {
            hideProgress();
            getView().onStartLoginActivity();
        }

    }


    @Override
    public void onUpdateButtonClicked(View view) {
        if (hasView()) {
            ProfileModel profileModel = getView().getProfileModel();
            File selectedFile = getView().getImageFile();
            ProgressRequestBody prb = new ProgressRequestBody(selectedFile, this);
            MultipartBody.Part file= null;
            if(selectedFile != null)
                    file= MultipartBody.Part.createFormData("file", selectedFile.getName(), prb);


            RequestBody address = createPartFromString(profileModel.getAddressLine1());
            RequestBody name = createPartFromString(profileModel.getFullName());
            RequestBody email = createPartFromString(profileModel.getEmailId());
            RequestBody mobile = createPartFromString(profileModel.getContactNumber());
            RequestBody profileId = createPartFromInt(profileModel.getPkid());
            RequestBody userName = createPartFromString(profileModel.getUserName());
            RequestBody appUser = createPartFromString("AppUser");
            //TODO changes: temporary city id;
            RequestBody city = createPartFromInt(Integer.parseInt(profileModel.getAddressLine2()));

            getView().showProgress();
                module.onSendToServer((MyApplication.getInstance().getRetroController()), view.getContext().getString(R.string.url_edit_profile),name,email,mobile, profileId,city,address,file,userName, appUser,this);


        }
    }

    private RequestBody createPartFromString(String val) {
        return RequestBody.create(MediaType.parse("text/plain"), val);
    }

    private RequestBody createPartFromInt(int val) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(val));
    }

    @Override
    public void onProgressUpdate(int percentage) {
        if(hasView())
        {
            getView().onProgressUpdate(percentage);
        }

    }

    @Override
    public void onError(Exception e) {
        if(hasView())
        {
            getView().onError(e);
        }

    }

    @Override
    public void onFinish() {
        if(hasView())
        {
            getView().onFinished();
        }

    }
}
