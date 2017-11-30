package itg8.com.wmcapp.prabhag.mvp;

import android.content.Context;

import java.util.List;

import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;

/**
 * Created by Android itg 8 on 11/21/2017.
 */

public class PrabhagPresenterImp extends BaseWeakPresenter<PrabhagMVP.PrabhagView> implements PrabhagMVP.PrabhagPresenter, PrabhagMVP.PrabhagListener {


    private final PrabhagMVP.PrabhagModule module;

    public PrabhagPresenterImp(PrabhagMVP.PrabhagView view) {
        super(view);
        module = new PrabhagModuleImp();
    }

    @Override
    public void onDetach() {
        module.onDestroy();
        if(hasView())
            detachView();

    }

    @Override
    public void onAttach(Context context) {
        if (hasView()) {

        }

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }


    @Override
    public void onPrabhagListAvailable(List<PrabhagModel> o) {
        if (hasView()) {
            getView().hideProgress();
            getView().onPrabhagListAvailable(o);
        }

    }

    @Override
    public void onInternetError(boolean b) {
        if (hasView()) {
            getView().hideProgress();
            getView().onNoInternetConnection(b);
        }
    }

    @Override
    public void onError(String message) {
        if (hasView()) {
            getView().hideProgress();
            getView().onError(message);
        }

    }

    @Override
    public void onGetPrabhagList(String url) {
        if (hasView()) {
            getView().showProgress();
            module.onStartLoadingList(MyApplication.getInstance().getRetroController(), url, this);
        }
    }
}