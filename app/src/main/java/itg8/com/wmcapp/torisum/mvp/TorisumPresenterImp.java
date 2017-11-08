package itg8.com.wmcapp.torisum.mvp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.torisum.model.TorisumModel;

/**
 * Created by Android itg 8 on 11/8/2017.
 */

public class TorisumPresenterImp extends BaseWeakPresenter<TourismMVP.TourismView> implements TourismMVP.TourismListener, TourismMVP.TourismPresenter{

    private final TourismMVP.TourismModule module;

    public TorisumPresenterImp(TourismMVP.TourismView tourismView) {
        super(tourismView);
        module= new TorisumModuleImp();
    }

    @Override
    public void onDetach() {
        module.onDestroy();

    }

    @Override
    public void onAttach(Context context) {
        if(hasView())
        {

        }

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }



    @Override
    public void onGetTorismList(String url) {
        if(hasView())
        {
         getView().showProgress();
         module.onStartLoadingList(MyApplication.getInstance().getRetroController(), url, this);
        }

    }



    @Override
    public void onTourismListAvailable(List<TorisumModel> o) {
         if(hasView())
         {
             getView().hideProgress();
             getView().onTourismListAvailable(o);
         }

    }

    @Override
    public void onInternetError(boolean b) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onNoInternetConnection(b);
        }
    }

    @Override
    public void onError(String message) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onError(message);
        }

    }
}
