package itg8.com.wmcapp.forget.mvp;


import android.view.View;

import java.util.HashMap;

import itg8.com.wmcapp.common.RetroController;


/**
 * Created by Android itg 8 on 10/13/2017.
 */

public interface ForgetMVP {


    public  interface  ForgetView
    {
        String getEmailId();


        void onSuccess(String message);
        void onFail(String message);
        void onError(Object t);

        void onEmailInvalid(String err);

        void showProgress();

        void hideProgress();
        void onNoInternetConnect(boolean b);
    }
    public interface ForgetPresenter
    {
        void onDestroy();
        void onSubmitButtonClicked(View view, boolean isDigit);
        void onNoInternetConnect(boolean b);


    }

    public interface ForgetModule
    {
        void onDestroy();
        void onFail(String message);
        void onSendForgetToServer(RetroController controller,HashMap<String, String> hashMap, String url, ForgetPresenterImp listener);
    }

    public interface ForgetListener{
        void onSuccess(String message);
        void onFail(String message);
        void onError(Object t);

        void onEmailInvalid(String err);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);

    }

}
