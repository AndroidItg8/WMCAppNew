package itg8.com.wmcapp.emergency.mvp;

import java.util.List;

import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.emergency.model.EmergencyModel;

/**
 * Created by Android itg 8 on 11/20/2017.
 */

public class EmergencyPresenterImp extends BaseWeakPresenter<EmergencyMVP.EmergencyView> implements  EmergencyMVP.EmergencyPresenter, EmergencyMVP.EmergencyListener {


    private final EmergencyMVP.EmergencyModule module;

    public EmergencyPresenterImp(EmergencyMVP.EmergencyView emergencyView) {
        super(emergencyView);
         module = new EmergencyModuleImp();
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
    public void onGetList(String url) {
        if(hasView()) {
            getView().showProgress();
             module.onEmergencyModelList(MyApplication.getInstance().getRetroController(), url,this);

        }


    }

    @Override
    public void onNoInternetConnect(boolean b) {
        if(hasView())
            getView().onNoInternetConnect(b);

    }

    @Override
    public void onSuccess(List<EmergencyModel> message) {
        if(hasView()) {
            getView().hideProgress();
            getView().onSuccess(message);
        }

    }
    @Override
    public void showProgress() {
        if(hasView())
            getView().showProgress();

    }

    @Override
    public void hideProgress() {
        if(hasView())
            getView().hideProgress();

    }

    @Override
    public void onFail(String message) {
        if(hasView())
            getView().onFail(message);

    }

    @Override
    public void onError(Object t) {
        if(hasView())
            getView().onError(t);

    }
}