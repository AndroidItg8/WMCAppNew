package itg8.com.wmcapp.torisum.mvp;


import java.util.List;

import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.torisum.model.TourismFilterModel;


/**
 * Created by Android itg 8 on 11/8/2017.
 */

public interface TourismMVP {
    public interface TourismView extends BaseFragmentView{
        void onTourismListAvailable(List<TorisumModel> o);
         void onTourismCategoryFilterList(List<TourismFilterModel> tourismFilterModelList);



    }

    public interface TourismPresenter extends BaseFragmentPresenter {

        void  onGetTorismList(String url);
        void onGetFilterCategoryList(String url);
    }

    public interface TourismListener{
        void onTourismListAvailable(List<TorisumModel> o);
        void onTourismCategoryFilterListAvailbe(List<TourismFilterModel> o);

        void onInternetError(boolean b);
        void onError(String message);


    }

    public interface TourismModule extends BaseDestroyModule {
        void onStartLoadingList(RetroController controller,String loadUrl, TourismMVP.TourismListener listener);
        void onStartLoadingCategoryFilter(RetroController controller,String loadUrl, TourismMVP.TourismListener listener);
    }

}
