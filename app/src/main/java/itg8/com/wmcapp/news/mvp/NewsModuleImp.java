package itg8.com.wmcapp.news.mvp;

import java.util.List;

import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.news.model.NewsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 11/27/2017.
 */

public class NewsModuleImp implements NewsMVP.NewsModule {
    @Override
    public void onDestroy() {

    }



    @Override
    public void onGetNewsListFromServer(String url, final NewsMVP.NewsListener listener) {
        Call<List<NewsModel>> call = MyApplication.getInstance().getRetroController().getNewsList(url);
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                 if(response.isSuccessful())
                 {
                     if(response.body()!= null)
                     {
                         listener.onSuccess(response.body());
                     }else
                     {
                         listener.onFail("Download Failed");
                     }
                 }else{
                     listener.onFail("Download Failed");
                 }

            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                t.printStackTrace();

                if(t instanceof NoConnectivityException)
                    listener.onNoInternetConnect(true);
                else
                    listener.onFail(t.getMessage());
 }
        });

    }
}
