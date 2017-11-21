package itg8.com.wmcapp.prabhag.mvp;

import java.util.List;

import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;
import itg8.com.wmcapp.torisum.mvp.BaseFragmentView;

/**
 * Created by Android itg 8 on 11/21/2017.
 */

public interface PrabhagMVP {
    public interface PrabhagView extends BaseFragmentView {
        void onPrabhagListAvailable(List<PrabhagModel> o);



    }

    public interface PrabhagPresenter extends BaseFragmentPresenter {

   void onGetPrabhagList(String url);
    }

    public interface PrabhagListener{
        void onPrabhagListAvailable(List<PrabhagModel> o);

        void onInternetError(boolean b);
        void onError(String message);



    }

    public interface PrabhagModule extends BaseDestroyModule {
        void onStartLoadingList(RetroController controller, String loadUrl, PrabhagMVP.PrabhagListener listener);
    }
}
