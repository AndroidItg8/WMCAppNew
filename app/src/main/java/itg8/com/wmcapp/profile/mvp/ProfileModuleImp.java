package itg8.com.wmcapp.profile.mvp;

import com.google.gson.Gson;

import java.util.List;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.profile.ProfileModel;
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 10/14/2017.
 */

public class ProfileModuleImp implements ProfileMVp.ProfileModule {

    private Call<List<ProfileModel>> cal;
    private Call<List<CityModel>> call;

    @Override
    public void onDestroy() {
        if(cal != null)
        {
            if(!cal.isCanceled())
                cal.cancel();
        }
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
    public void onGetProfileListFromServer(RetroController controller, String url, final ProfileMVp.ProfileListener listener) {
        cal = controller.getProfile(url);
        cal.enqueue(new Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                if (response.code() == 401) {
                    listener.onStartLoginActivity();
                    return;
                }
                if(response.isSuccessful())
                {
                    if(response.body()!= null)
                    {
                        Logs.d("responseBody"+new Gson().toJson(response.body()));
                        listener.onSuccess(response.body());
                    }else
                    {
                        listener.onFail("Download Failed");
                    }
                }else
                {
                    listener.onFail("Download Failed");
                }

            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                 if(t instanceof NoConnectivityException)
                 {
                     listener.onNoInternetConnect(true);
                 }else
                 {
                     listener.onFail(t.getMessage());
                 }

            }
        });
    }



    @Override
    public void onGetCityListFromServer(RetroController retroController, String url, final ProfileMVp.ProfileListener listener) {
        call = retroController.getCityFromServer(url);
        call.enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                if (response.code() == 401) {
                    listener.onStartLoginActivity();
                    return;
                }
                if(response.isSuccessful()) {
                    if (response.body() != null) {

                        listener.onSuccessCity(response.body());

                    } else {
                        listener.onFail("Download Failed");
                    }
                }else{
                    listener.onFail("Download Failed");

                }
            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
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

    @Override
    public void onSendToServer(RetroController retroController, String url, RequestBody name, RequestBody email, RequestBody mobile, RequestBody profileId, RequestBody city, RequestBody address, MultipartBody.Part file, RequestBody userName, RequestBody appUser, final ProfileMVp.ProfileListener listener) {
        Call<RegistrationModel> call = retroController.sendRegistrationInfoToserver(url,name,address,city,mobile,email,profileId,file);
         call.enqueue(new Callback<RegistrationModel>() {
             @Override
             public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                 if (response.isSuccessful()) {
                     if (response.body().isFlag()) {
                         listener.onSaveSuccess(response.body().getStatus());
                     } else {
                         listener.onFail(response.body().getStatus());

                     }
                 } else {
                     listener.onFail(response.body().getStatus());
                 }
             }

             @Override
             public void onFailure(Call<RegistrationModel> call, Throwable t) {
                 t.printStackTrace();

                 if(t instanceof NoConnectivityException)
                     listener.onNoInternetConnect(true);
                 else
                     listener.onFail(t.getMessage());


             }
         });


    }
}
