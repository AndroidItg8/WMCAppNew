package itg8.com.wmcapp.signup.mvp;

import android.view.View;


import itg8.com.wmcapp.common.RetroController;

/**
 * Created by Android itg 8 on 10/9/2017.
 */

public interface LoginMvp {
     public  interface  LoginView
     {
         String getUsername();
         String getPassword();
         void onSuccess();
         void onFail(String message);
         void onError(Object t);

         void onUsernameInvalid(String err);

         void onPasswordInvalid(String err);

         void showProgress();

         void hideProgress();
         void onNoInternetConnect(boolean b);


         void onFirstTimeLogin(String success);
     }
      public interface LoginPresenter
      {
          void onDestroy();
          void onLoginClicked(View view, boolean isMobile);
          void onNoInternetConnect(boolean b);


      }

      public interface LoginModule
      {
          void onDestroy();
          void onSendToServer(RetroController controller, String string, String username, String password, LoginListener loginPresenterImp);

      }

    public interface LoginListener{
        void onSuccess();
        void onFail(String message);
        void onError(Object t);
        void onUsernameInvalid(String err);
        void onPasswordInvalid(String err);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);
        void onFirstTimeLogin(String success);
    }
}
