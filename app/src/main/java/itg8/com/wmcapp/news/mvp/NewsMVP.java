package itg8.com.wmcapp.news.mvp;


import java.util.List;

import itg8.com.wmcapp.news.model.NewsModel;

/**
 * Created by Android itg 8 on 11/27/2017.
 */

public interface NewsMVP {



    public  interface NewsView
    {

        void onSuccess(List<NewsModel> list);

        void onFail(String message);
        void onError(Object t);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);


    }


    public interface NewsPresenter
    {
        void onDestroy();
        void onGetNewsList(String view);
        void onNoInternetConnect(boolean b);

    }

    public interface NewsModule
    {
        void onDestroy();
        void onGetNewsListFromServer(String url, NewsMVP.NewsListener listener);
    }

    public interface NewsListener{
        void onSuccess(List<NewsModel> list);

        void onFail(String message);
        void onError(Object t);
        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);
    }
}
