package itg8.com.wmcapp.change_password.mvp;



import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.registration.RegistrationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER-pc on 10/16/2017.
 */

public class ChagePasswordImp implements ChangePasswordMVP.ChangePswdModule {


    private Call<RegistrationModel> call;

    @Override
    public void onDestroy() {
         if(call != null)
         {
             if(!call.isCanceled())
             {
                 call.cancel();
             }
         }
    }

    @Override
    public void onAuthenticationToChangePswd(RetroController controller, String url,  String oldpswd, String newpswd, String confirmpswd, final ChangePswdPresenterImp listener) {

             call = controller.changePassword(url, oldpswd, newpswd);
          call.enqueue(new Callback<RegistrationModel>() {
              @Override
              public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                  if (response.isSuccessful()) {
                      if(response.body() != null )
                      {
                          if(response.body().isFlag()) {
                              listener.onSuccess(response.body().getStatus());
                          }
                          else
                          {
                              listener.onFail(response.body().getStatus());
                          }

                      }
                      else
                      {
                          listener.onFail(response.body().getStatus());
                      }

                  }else
                  {
                      listener.onFail(response.message());
                  }


              }

              @Override
              public void onFailure(Call<RegistrationModel> call, Throwable t) {
                  if(t instanceof NoConnectivityException)
                  {
                      listener.onNoInternetConnect(true);

                  }else
                  {
                      listener.onError(t);
                  }

              }
          });

    }


}
