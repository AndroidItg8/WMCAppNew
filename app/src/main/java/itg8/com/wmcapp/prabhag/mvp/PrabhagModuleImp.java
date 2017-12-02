package itg8.com.wmcapp.prabhag.mvp;

import java.util.List;

import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 11/21/2017.
 */

public class PrabhagModuleImp implements PrabhagMVP.PrabhagModule {
    private Call<List<PrabhagModel>> call;

    @Override
    public void onDestroy() {
         if(call!= null)
         {
             if(!call.isCanceled())
                 call.cancel();
         }

    }

    @Override
    public void onStartLoadingList(RetroController controller, String loadUrl, final PrabhagMVP.PrabhagListener listener) {
        call = controller.getPragbhagList(loadUrl);
        call.enqueue(new Callback<List<PrabhagModel>>() {
            @Override
            public void onResponse(Call<List<PrabhagModel>> call, Response<List<PrabhagModel>> response) {
                if(response.code()==401)
                {
                    listener.onError("401");
                    return;
                }
                if(response.isSuccessful())
                {
                    if(response.body()!= null)
                    {
                        listener.onPrabhagListAvailable(response.body());
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
            public void onFailure(Call<List<PrabhagModel>> call, Throwable t) {
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
