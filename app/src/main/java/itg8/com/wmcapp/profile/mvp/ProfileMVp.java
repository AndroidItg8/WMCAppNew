package itg8.com.wmcapp.profile.mvp;


import android.view.View;

import java.io.File;
import java.util.List;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.profile.ProfileModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Android itg 8 on 10/14/2017.
 */

public interface ProfileMVp {

    public  interface ProfileView
    {

        void onSuccess(List<ProfileModel> list);
        void onSuccessCityList(List<CityModel> list);
        void onFail(String message);
        void onError(Object t);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);
        void onInternetConnect(boolean b);
       void onProgressUpdate(int percentage);
        void onFinished();


        void onSuccessSave(String status);
        ProfileModel getProfileModel();
        File getImageFile();

        void onStartLoginActivity();
    }


    public interface ProfilePresenter
    {
        void onDestroy();
        void onGetProfileList(String view);
        void onNoInternetConnect(boolean b);
        void onUpdateButtonClicked(View view);
        void onGetCityList(String url);
    }

    public interface ProfileModule
    {
        void onDestroy();
        void onFail(String message );
        void onGetProfileListFromServer(RetroController controller, String url, ProfileListener listener);

        void onGetCityListFromServer(RetroController retroController, String url, ProfileListener listener);

        void onSendToServer(RetroController retroController, String url, RequestBody name, RequestBody email, RequestBody mobile, RequestBody profileId, RequestBody city, RequestBody address, MultipartBody.Part file, RequestBody userName, RequestBody appUser , ProfileListener listener);
    }

    public interface ProfileListener{
        void onSuccess(List<ProfileModel> list);
        void onSuccessCity(List<CityModel> list);
        void onFail(String message);
        void onError(Object t);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);
        void onSaveSuccess(String status);

        void onStartLoginActivity();
    }
}
