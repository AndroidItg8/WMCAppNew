package itg8.com.wmcapp.news.mvp;

import java.util.List;

import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.news.model.NewsModel;


/**
 * Created by Android itg 8 on 11/27/2017.
 */

public class NewsPresenterImp extends BaseWeakPresenter<NewsMVP.NewsView> implements NewsMVP.NewsPresenter, NewsMVP.NewsListener {
    private final NewsMVP.NewsModule module;

    public NewsPresenterImp(NewsMVP.NewsView newsView) {
        super(newsView);
        module = new NewsModuleImp();
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
    public void onGetNewsList(String url) {
        if(hasView())
        {
            showProgress();
             module.onGetNewsListFromServer(url, this);
        }
    }

    @Override
    public void onSuccess(List<NewsModel> list) {
        if(hasView())
        {
            hideProgress();
            getView().onSuccess(list);
        }
    }

    @Override
    public void onFail(String message) {
        if (hasView()) {
            hideProgress();
            getView().onFail(message);

        }
    }

    @Override
    public void onError(Object t) {
        if (hasView()) {
            hideProgress();
            getView().onError(t);

        }
 }

    @Override
    public void showProgress() {
        if (hasView()) {
            getView().showProgress();

        }
 }

    @Override
    public void hideProgress() {
        if (hasView()) {
            getView().hideProgress();

        }
    }

    @Override
    public void onNoInternetConnect(boolean b) {
        if (hasView()) {
            hideProgress();
            getView().onNoInternetConnect(b);
        }

    }
}
