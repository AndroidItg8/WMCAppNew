package itg8.com.wmcapp.torisum.mvp;

import com.google.gson.Gson;

import java.util.List;

import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.torisum.model.TourismFilterCategoryModel;
import itg8.com.wmcapp.torisum.model.TourismFilterModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 11/8/2017.
 */

public class TorisumModuleImp implements TourismMVP.TourismModule {
    private Call<List<TorisumModel>> call;

    @Override
    public void onDestroy() {
        if(call!= null)
            call.cancel();

    }

    @Override
    public void onStartLoadingList(RetroController controller, String loadUrl, final TourismMVP.TourismListener listener) {
 call = controller.getTorisumList(loadUrl);
 call.enqueue(new Callback<List<TorisumModel>>() {
     @Override
     public void onResponse(Call<List<TorisumModel>> call, Response<List<TorisumModel>> response) {
         if(response.code()==401)
         {
             listener.onError("401");
             return;
         }
         if(response.isSuccessful())
         {
             if(response.body()!= null)
             {
                 listener.onTourismListAvailable(response.body());
             }else
             {
                 listener.onError("Download Failed");
             }
         }else
         {
             listener.onError("Download Failed");
         }
     }

     @Override
     public void onFailure(Call<List<TorisumModel>> call, Throwable t) {
         t.printStackTrace();
         if(t instanceof NoConnectivityException)
         {
             listener.onInternetError(true);
         }else
         {
             listener.onError(t.getMessage());
         }

     }
 });



    }

    @Override
    public void onStartLoadingCategoryFilter(RetroController controller, String loadUrl, final TourismMVP.TourismListener listener) {
        Call<List<TourismFilterModel> > call = controller.loadCategoryTourism(loadUrl);
        call.enqueue(new Callback<List<TourismFilterModel>>() {
            @Override
            public void onResponse(Call<List<TourismFilterModel>> call, Response<List<TourismFilterModel>> response) {
                if(response.isSuccessful())
                {
                    if(response.body()!= null)
                    {
                        listener.onTourismCategoryFilterListAvailbe(response.body());
                    }else
                    {
                        listener.onError("Download Failed");
                    }
                }else
                {
                    listener.onError("Download Failed");
                }
            }

            @Override
            public void onFailure(Call<List<TourismFilterModel>> call, Throwable t) {
                t.printStackTrace();
                if(t instanceof NoConnectivityException)
                {
                    listener.onInternetError(true);
                }else
                {
                    listener.onError(t.getMessage());
                }

            }
        });


    }

    @Override
    public void onLoadingFilterTourismList(RetroController retroController, String url, List<TourismFilterCategoryModel> torismFilterCategory, final TourismMVP.TourismListener listener) {
        Logs.d("onLoadingFilterTourismList:"+new Gson().toJson(torismFilterCategory));
        Call<List<TorisumModel>> call = retroController.getFilterTourismList(url,torismFilterCategory);
         call.enqueue(new Callback<List<TorisumModel>>() {
             @Override
             public void onResponse(Call<List<TorisumModel>> call, Response<List<TorisumModel>> response) {
                 if(response.isSuccessful())
                 {
                     if(response.body()!= null)
                     {
                         listener.onTourismListAvailable(response.body());
                     }else
                     {
                         listener.onError("DownLoad Failed");
                     }
                 }else
                 {
                     listener.onError("DownLoad Failed");
                 }
             }

             @Override
             public void onFailure(Call<List<TorisumModel>> call, Throwable t) {
                 t.printStackTrace();
                 if(t instanceof NoConnectivityException)
                 {
                     listener.onInternetError(true);
                 }else
                 {
                     listener.onError(t.getMessage());
                 }
             }
         });

    }
}
