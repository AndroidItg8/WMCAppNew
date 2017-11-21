package itg8.com.wmcapp.emergency.mvp;

import java.util.List;

import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.emergency.model.EmergencyModel;

/**
 * Created by Android itg 8 on 11/20/2017.
 */

public interface EmergencyMVP {


    public  interface  EmergencyView
        {
            void onSuccess(List<EmergencyModel> list);
            void onFail(String message);
            void onError(Object t);


            void showProgress();

            void hideProgress();
            void onNoInternetConnect(boolean b);
        }


        public interface EmergencyPresenter
        {
            void onDestroy();
            void onGetList(String url);
            void onNoInternetConnect(boolean b);


        }

        public interface EmergencyModule
        {
            void onDestroy();
            void onFail(String message);
            void onEmergencyModelList(RetroController controller, String url, EmergencyPresenterImp listener);
        }

        public interface EmergencyListener{
            void onSuccess(List<EmergencyModel> message);
            void onFail(String message);
            void onError(Object t);

            void showProgress();
            void hideProgress();
            void onNoInternetConnect(boolean b);

        }

    }
