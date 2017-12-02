package itg8.com.wmcapp.cilty.mvp;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.database.BaseDatabaseHelper;
import itg8.com.wmcapp.home.HomeActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 11/6/2017.
 */

public class CityModuleImp  implements CityMVP.CityModule{


    private Call<List<CityModel>> call;

    @Override
    public void onDestroy() {

    }

    @Override
    public void onGetCityListFromServer(RetroController retroController, String url, final CityPresenterImp listener) {

       call = retroController.getCityFromServer(url);
         call.enqueue(new Callback<List<CityModel>>() {
             @Override
             public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                 if (response.code() == 401) {
                     listener.onError("401");
                     return;
                 }
                 if(response.isSuccessful()) {
                     if (response.body() != null) {

                         listener.onSuccess(response.body());

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



}
