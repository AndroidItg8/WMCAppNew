package itg8.com.wmcapp.signup.mvp;


import android.text.TextUtils;
import android.view.View;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;


/**
 * Created by Android itg 8 on 10/9/2017.
 */

public class LoginPresenterImp extends BaseWeakPresenter<LoginMvp.LoginView> implements LoginMvp.LoginPresenter, LoginMvp.LoginListener {


    private final LoginMvp.LoginModule module;

    public LoginPresenterImp(LoginMvp.LoginView view) {
        super(view);
        module = new LoginModuleImp();
    }

    @Override
    public void onDestroy() {
        module.onDestroy();
        if (hasView()) {
            detachView();
        }

    }

    @Override
    public void onLoginClicked(View view, boolean isMobile) {
        if (hasView()) {
            boolean isValid = true;
            String password = getView().getPassword();
            String username = getView().getUsername();
            if (TextUtils.isEmpty(username)) {
                isValid = false;
                getView().onUsernameInvalid(view.getContext().getString(R.string.empty));
            }
            if (TextUtils.isEmpty(password)) {
                isValid = false;
                getView().onPasswordInvalid(view.getContext().getString(R.string.empty));
            }
             if(isMobile)
             {
                 if(username.length() != 10)
                 {
                     isValid = false;
                     getView().onUsernameInvalid(view.getContext().getString(R.string.invalid_number));
                 }

             }else
             {
                 if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                     isValid = false;
                     getView().onUsernameInvalid(view.getContext().getString(R.string.invalid_email));
                 }
             }


            if (password.length() < 6) {
                isValid = false;
                getView().onPasswordInvalid(view.getContext().getString(R.string.invalid_pass));
            }
            if (isValid) {
                getView().showProgress();
                module.onSendToServer((MyApplication.getInstance().getRetroController()), view.getContext().getString(R.string.url_login), username, password, this);

            }
        }


    }

    @Override
    public void onNoInternetConnect(boolean b) {
        if (hasView()) {
            getView().hideProgress();

            getView().onNoInternetConnect(b);
        }
    }

    @Override
    public void onFirstTimeLogin(String success) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onFirstTimeLogin(success);
        }
    }


    @Override
    public void onUsernameInvalid(String err) {
        if (hasView()) {
            getView().hideProgress();
            getView().onUsernameInvalid(err);
        }

    }

    @Override
    public void onPasswordInvalid(String err) {
        if (hasView()) {
            getView().hideProgress();

            getView().onPasswordInvalid(err);
        }

    }

    @Override
    public void showProgress() {
        if (hasView()) {
            getView().showProgress();

        }

    }

    @Override
    public void hideProgress() {
        if (hasView()) {
            getView().hideProgress();
        }

    }


    @Override
    public void onFail(String message) {
        if (hasView()) {
            getView().hideProgress();


            getView().onFail(message);
        }


    }

    @Override
    public void onError(Object t) {
        if (hasView()) {
            getView().hideProgress();


            getView().onFail(t.toString());
        }


    }


    @Override
    public void onSuccess() {
        if (hasView()) {
            getView().hideProgress();
            getView().onSuccess();
        }

    }
}

