package itg8.com.wmcapp.profile.mvp;


import java.util.List;

import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.profile.ProfileModel;

/**
 * Created by Android itg 8 on 10/14/2017.
 */

public interface ProfileMVp {

    public  interface ProfileView
    {

        void onSuccess(List<ProfileModel> list);
        void onFail(String message);
        void onError(Object t);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);
        void onInternetConnect(boolean b);


    }


    public interface ProfilePresenter
    {
        void onDestroy();
        void onGetProfileList(String view);
        void onNoInternetConnect(boolean b);
        void onInternetConnect(boolean b);
    }

    public interface ProfileModule
    {
        void onDestroy();
        void onFail(String message );
        void onGetProfileListFromServer(RetroController controller, String url, ProfilePresenterImp listener);
    }

    public interface ProfileListener{
        void onSuccess(List<ProfileModel> list);
        void onFail(String message);
        void onError(Object t);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);
        void onInternetConnect(boolean b);

    }
}
