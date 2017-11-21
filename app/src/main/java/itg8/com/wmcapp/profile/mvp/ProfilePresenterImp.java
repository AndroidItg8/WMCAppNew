package itg8.com.wmcapp.profile.mvp;


import android.text.TextUtils;
import android.view.View;

import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
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
    public void onUpdateButtonClicked(View view) {
        if (hasView()) {
            boolean isValid = true;
            String address = getView().getAddress();
            String email = getView().getEmail();
            String city = getView().getCity();
            String name = getView().getName();
            String mobile = getView().getMobile();
//            if (TextUtils.isEmpty(address)) {
//                isValid = false;
//                getView().onAddressInvalid(view.getContext().getString(R.string.empty));
//            }
//            if (TextUtils.isEmpty(city)) {
//                isValid = false;
//                getView().onCityInvalid(view.getContext().getString(R.string.empty));
//            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isValid = false;
                getView().onEmailInvalid(view.getContext().getString(R.string.invalid_email));
            }


            if (isValid) {
                getView().showProgress();
                module.onSendToServer((MyApplication.getInstance().getRetroController()), view.getContext().getString(R.string.url_edit_profile),name,mobile,address, email,city, this);

            }

        }
    }
}
