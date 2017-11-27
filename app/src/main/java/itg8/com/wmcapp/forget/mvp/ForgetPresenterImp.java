package itg8.com.wmcapp.forget.mvp;

import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.MyApplication;


/**
 * Created by Android itg 8 on 10/13/2017.
 */

public class ForgetPresenterImp extends BaseWeakPresenter<ForgetMVP.ForgetView> implements  ForgetMVP.ForgetPresenter, ForgetMVP.ForgetListener {
    private final ForgetMVP.ForgetModule module;

    public ForgetPresenterImp(ForgetMVP.ForgetView forgetView) {
        super(forgetView);
         module = new ForgetModuleImp();
    }

    @Override
    public void onDestroy() {
        if(hasView())
        {
          detachView();
        }
        module.onDestroy();

    }

    @Override
    public void onSubmitButtonClicked(View view, boolean isDigit) {
        if(hasView()) {
            boolean isValid = true;
            HashMap<String, String> hashMap = new HashMap<>();
            String email = getView().getEmailId();

            if(TextUtils.isEmpty(email))
            {
                isValid = false;
                getView().onEmailInvalid(view.getContext().getString(R.string.empty));
            }

            if(TextUtils.isDigitsOnly(email))
            {


                if(email.length() != 10)
                {isValid = false;
                    getView().onEmailInvalid(view.getContext().getString(R.string.invalid_number));
                }else
                {
                    hashMap.put("Mobile",email);
                }
            }else {

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    isValid = false;
                    getView().onEmailInvalid(view.getContext().getString(R.string.invalid_email));
                }else
                {
                    hashMap.put("Email", email);
                }
            }

            if(isValid){
                getView().showProgress();

                module.onSendForgetToServer((MyApplication.getInstance().getRetroController()),hashMap,view.getContext().getString(R.string.url_forget_pswd), this);

            }
        }

    }

    @Override
    public void onNoInternetConnect(boolean b) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onNoInternetConnect(b);
        }
    }



    @Override
    public void onSuccess(String message) {
         if(hasView())
         {
             getView().hideProgress();
             getView().onSuccess(message);
         }

    }



    @Override
    public void onEmailInvalid(String err) {
        if(hasView()) {
            getView().hideProgress();
            getView().onEmailInvalid(err);
        }


    }

    @Override
    public void showProgress() {
        if(hasView())
            getView().showProgress();

    }

    @Override
    public void hideProgress() {
        if(hasView())
            getView().hideProgress();

    }

    @Override
    public void onFail(String message) {
        if(hasView()) {
            getView().hideProgress();
            getView().onFail(message);
        }

    }

    @Override
    public void onError(Object t) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onError(t);
        }

    }
}
