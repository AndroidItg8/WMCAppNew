package itg8.com.wmcapp.registration.mvp;

import android.widget.EditText;

import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import itg8.com.wmcapp.common.InternetView;

/**
 * Created by swapnilmeshram on 03/11/17.
 */

public interface RegistrationMVP {
    public interface RegistrationView extends InternetView {
        void onSuccess();
        void onFailure();

    }

    public interface RegistrationPresenter extends BaseFragmentPresenter{
        void onSubmitRegistration();
    }

    public interface RegistrationModule extends BaseDestroyModule {

    }


}
