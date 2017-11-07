package itg8.com.wmcapp.forget.mvp;

import android.view.View;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseWeakPresenter;
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
    public void onSubmitButtonClicked(View view) {
        if(hasView()) {
            boolean isValid = true;
            String email = getView().getEmailId();
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                isValid=false;
                getView().onEmailInvalid(view.getContext().getString(R.string.invalid_email));
            }

            if(isValid){
                getView().showProgress();

                module.onSendForgetToServer((MyApplication.getInstance().getRetroController()),email,view.getContext().getString(R.string.url_forget_pswd), this);

            }
        }

    }

    @Override
    public void onNoInternetConnect(boolean b) {

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
        if(hasView())
            getView().onFail(message);

    }

    @Override
    public void onError(Object t) {
        if(hasView())
            getView().onError(t);

    }
}
