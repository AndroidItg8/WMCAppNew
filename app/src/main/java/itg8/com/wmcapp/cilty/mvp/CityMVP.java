package itg8.com.wmcapp.cilty.mvp;

import android.view.View;

import java.util.List;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.Retro;
import itg8.com.wmcapp.common.RetroController;


/**
 * Created by Android itg 8 on 11/6/2017.
 */

public interface CityMVP {

    public  interface  CityView
    {

        void onGetCityList(List<CityModel> list);
        void onFail(String message);
        void onError(Object t);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);

    }
    public interface CityPresenter
    {
        void onDestroy();
        void onNoInternetConnect(boolean b);


        void onGetCity(String url);
    }

    public interface CityModule
    {
        void onDestroy();

        void onGetCityListFromServer(RetroController retroController, String url, CityPresenterImp listener);
    }

    public interface CityListener{
        void onSuccess(List<CityModel> list);
        void onFail(String message);
        void onError(Object t);

        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);

    }
}
