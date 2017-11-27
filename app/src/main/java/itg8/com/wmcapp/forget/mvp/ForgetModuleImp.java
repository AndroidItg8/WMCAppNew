package itg8.com.wmcapp.forget.mvp;


import java.util.HashMap;

import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.registration.RegistrationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 10/13/2017.
 */

public class ForgetModuleImp  implements ForgetMVP.ForgetModule {
    private Call<RegistrationModel> call;

    @Override
    public void onDestroy() {
         if(call != null)
         {
             if(!call.isCanceled())
                 call.cancel();
         }

    }

    @Override
    public void onFail(String message) {

    }



    @Override
    public void onSendForgetToServer(RetroController controller,HashMap<String, String> parametrs,String url, final ForgetPresenterImp listener) {

    call = controller.forgetPaswd(url, parametrs);
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



