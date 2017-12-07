package itg8.com.wmcapp.change_password.mvp;

import android.text.TextUtils;
import android.view.View;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;


/**
 * Created by USER-pc on 10/16/2017.
 */

public class ChangePswdPresenterImp extends BaseWeakPresenter<ChangePasswordMVP.ChangePswdView> implements ChangePasswordMVP.ChangePswdPresenter,ChangePasswordMVP.ChangePswdListener {


    private final ChangePasswordMVP.ChangePswdModule module;
    String oldPassword;
    String newPassword;
    String confirmPassword;

    public ChangePswdPresenterImp(ChangePasswordMVP.ChangePswdView changePswdView) {
        super(changePswdView);
         module = new ChagePasswordImp();
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
    public void onSubmitButtonClicked(View view,  String url) {

        if(hasView()) {
            boolean isValid = true;
            isValid =PasswordValidity(view);

            if (isValid) {
                getView().showProgress();
                module.onAuthenticationToChangePswd((MyApplication.getInstance().getRetroController()), url,  oldPassword, newPassword, confirmPassword, this);

            }

    }

    }

    private boolean PasswordValidity(View view) {
        oldPassword = getView().getOldPassword();
        newPassword = getView().getNewPassword();
        confirmPassword = getView().getConfirmPassword();
        boolean isValid = true;
        if(TextUtils.isEmpty(oldPassword))
        {
            isValid=false;
            getView().onOldPswdInvalid(view.getContext().getString(R.string.empty));
        }
        if(TextUtils.isEmpty(newPassword))
        {
            isValid=false;
            getView().onNewPswdInvalid(view.getContext().getString(R.string.empty));
        }
        if(TextUtils.isEmpty(confirmPassword))
        {
            isValid=false;
            getView().onConfirmswdInvalid(view.getContext().getString(R.string.empty));
        }

        if (newPassword.length() < 6) {
            isValid = false;
            getView().onNewPswdInvalid(view.getContext().getString(R.string.invalid_pass_not));
        }
        if (oldPassword.length() < 6) {
            isValid = false;
            getView().onOldPswdInvalid(view.getContext().getString(R.string.invalid_pass_not));
        }
        if (confirmPassword.length() < 6) {
            isValid = false;
            getView().onConfirmswdInvalid(view.getContext().getString(R.string.invalid_pass_not));
        } else if (!newPassword.equals(confirmPassword)) {
            isValid = false;
            getView().onConfirmswdInvalid(view.getContext().getString(R.string.invalid_pass_not_match));
            getView().onNewPswdInvalid(view.getContext().getString(R.string.invalid_pass_not_match));
        }
        return isValid;
    }

    @Override
    public void onSuccess(String status) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onSuccess(status);
        }

    }

    @Override
    public void onFail(String message) {
        if(hasView())
        {
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

    @Override
    public void onOldPswdInvalid(String err) {
        if(hasView())
        {
            getView().hideProgress();

            getView().onOldPswdInvalid(err);
        }

    }

    @Override
    public void onNewPswdInvalid(String err) {
        if(hasView())
        {
            getView().hideProgress();

            getView().onNewPswdInvalid(err);
        }
    }

    @Override
    public void onConfirmswdInvalid(String err) {
        if(hasView())
        {
            getView().hideProgress();

            getView().onConfirmswdInvalid(err);
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
            getView().hideProgress();

            getView().onNoInternetConnect(b);
        }

    }


}
