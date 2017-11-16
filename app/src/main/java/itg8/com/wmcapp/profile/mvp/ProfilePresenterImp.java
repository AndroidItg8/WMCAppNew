package itg8.com.wmcapp.profile.mvp;


import java.util.List;

import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.profile.ProfileModel;

/**
 * Created by Android itg 8 on 10/14/2017.
 */

public class ProfilePresenterImp extends BaseWeakPresenter<ProfileMVp.ProfileView> implements  ProfileMVp.ProfilePresenter, ProfileMVp.ProfileListener{

    private final ProfileMVp.ProfileModule module;

    public ProfilePresenterImp(ProfileMVp.ProfileView profileView) {
        super(profileView);
         module = new ProfileModuleImp();
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
            getView().onSuccess(list);

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
    public void onInternetConnect(boolean b) {
        if(hasView())
        {
            hideProgress();
            getView().onInternetConnect(b);
        }


    }
}
