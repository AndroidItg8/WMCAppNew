package itg8.com.wmcapp.emergency.mvp;

import java.util.List;

import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import itg8.com.wmcapp.emergency.model.EmergencyModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 11/20/2017.
 */

public class EmergencyModuleImp implements EmergencyMVP.EmergencyModule {

    private Call<List<EmergencyModel>> call;

    @Override
    public void onDestroy() {
        if (call != null) {
            if (!call.isCanceled())
                call.cancel();
        }

    }

    @Override
    public void onFail(String message) {

    }

    @Override
    public void onEmergencyModelList(RetroController controller, String url, final EmergencyPresenterImp listener) {
        call = controller.getEmergency(url);
        call.enqueue(new Callback<List<EmergencyModel>>() {
            @Override
            public void onResponse(Call<List<EmergencyModel>> call, Response<List<EmergencyModel>> response) {
                if (response.code() == 401)
                {
                    listener.onFail("401");
                    return;
                }
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            listener.onSuccess(response.body());
                        } else {
                            listener.onError("Download Failed");
                        }
                    } else {
                        listener.onError("Download Failed");
                    }
            }

            @Override
            public void onFailure(Call<List<EmergencyModel>> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof NoConnectivityException) {
                    listener.onNoInternetConnect(true);
                } else {
                    listener.onError(t.getMessage());
                }

            }
        });

    }
}
