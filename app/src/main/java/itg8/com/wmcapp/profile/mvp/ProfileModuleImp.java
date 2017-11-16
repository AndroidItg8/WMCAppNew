package itg8.com.wmcapp.profile.mvp;

import java.util.ArrayList;
import java.util.List;

import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.profile.ProfileModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 10/14/2017.
 */

public class ProfileModuleImp implements ProfileMVp.ProfileModule {

    private List<ProfileModel> list= new ArrayList<>();
    private Call<List<ProfileModel>> cal;

    @Override
    public void onDestroy() {
        if(cal != null)
        {
            if(!cal.isCanceled())
                cal.cancel();
        }
    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void onGetProfileListFromServer(RetroController controller, String url, final ProfilePresenterImp listener) {
        cal = controller.getProfile(url);
        cal.enqueue(new Callback<List<ProfileModel>>() {
            @Override
            public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                if(response.isSuccessful())
                {
                    if(response.body()!= null)
                    {
                        list = response.body();
                        listener.onSuccess(response.body());
                    }else
                    {
                        listener.onFail("Download Failed");
                    }
                }else
                {
                    listener.onFail("Download Failed");
                }

            }

            @Override
            public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                 if(t instanceof NoConnectivityException)
                 {
                     listener.onNoInternetConnect(true);
                 }else
                 {
                     listener.onFail(t.getMessage());
                 }

            }
        });


    }
}
