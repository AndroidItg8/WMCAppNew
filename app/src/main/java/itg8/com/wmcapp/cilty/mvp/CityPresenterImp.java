package itg8.com.wmcapp.cilty.mvp;

import java.util.List;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;


/**
 * Created by Android itg 8 on 11/6/2017.
 */

public class CityPresenterImp extends BaseWeakPresenter<CityMVP.CityView> implements CityMVP.CityPresenter, CityMVP.CityListener {


    private final CityMVP.CityModule module;

    public CityPresenterImp(CityMVP.CityView cityView) {
        super(cityView);
         module =  new CityModuleImp();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onNoInternetConnect(boolean b) {
        if(hasView())
        {
            getView().hideProgress();

            getView().onNoInternetConnect(b);
        }

    }




    @Override
    public void onSuccess(List<CityModel> list) {
         if(hasView())
         {
             getView().hideProgress();
              getView().onGetCityList(list);

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
    public void onFail(String message) {
        if(hasView()) {
            getView().hideProgress();


            getView().onFail(message);
        }



    }

    @Override
    public void onError(Object t) {
        if(hasView()) {
            getView().hideProgress();

            getView().onFail(t.toString());
        }



    }

    @Override
    public void onGetCity(String url) {
        if(hasView()){
            getView().showProgress();

            module.onGetCityListFromServer( MyApplication.getInstance().getRetroController(),url, this);

        }

    }







}
