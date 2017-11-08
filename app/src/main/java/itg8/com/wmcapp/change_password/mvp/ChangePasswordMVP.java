package itg8.com.wmcapp.change_password.mvp;

import android.view.View;

import itg8.com.wmcapp.common.RetroController;


/**
 * Created by USER-pc on 10/16/2017.
 */

public interface ChangePasswordMVP {

    public  interface  ChangePswdView
    {
        String getOldPassword();
        String getNewPassword();
        String getConfirmPassword();
        void onSuccess(String status);
        void onFail(String message);
        void onError(Object t);

        void onOldPswdInvalid(String err);

        void onNewPswdInvalid(String err);
        void onConfirmswdInvalid(String err);

        void showProgress();

        void hideProgress();

        void onNoInternetConnect(boolean b);
    }
    public interface ChangePswdPresenter
    {
        void onDestroy();
        void onSubmitButtonClicked(View view, String url);
        void onNoInternetConnect(boolean b);


    }

    public interface ChangePswdModule
    {
        void onDestroy();
        void onAuthenticationToChangePswd(RetroController controller, String url, String oldpswd, String newpswd, String confirmpswd, ChangePswdPresenterImp listner);
    }

    public interface ChangePswdListener{
        void onSuccess(String status);
        void onFail(String message);
        void onError(Object t);
        void onOldPswdInvalid(String err);

        void onNewPswdInvalid(String err);
        void onConfirmswdInvalid(String err);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);

    }
}
