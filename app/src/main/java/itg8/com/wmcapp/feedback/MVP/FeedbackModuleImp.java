package itg8.com.wmcapp.feedback.MVP;

import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.registration.RegistrationFragment;
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android itg 8 on 11/27/2017.
 */

public class FeedbackModuleImp implements FeedbackMVP.FeedbackModule {
    private Call<RegistrationModel> call;

    @Override
    public void onDestroy() {
         if(call!= null)
         {
             if(!call.isCanceled())
             {
                 call.cancel();
             }
         }

    }

    @Override
    public void onSendToServer(String url, String title, String description, int rating, final FeedbackMVP.FeedbackListener listener) {
//        call = MyApplication.getInstance().getRetroController().submitFeedback(url,title,description, rating);
        call = MyApplication.getInstance().getRetroController().submitFeedback(url, rating,description);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                if(response.isSuccessful())
                {
                    if(response.body().isFlag())
                    {
                        listener.onSuccess(response.body().getStatus());
                    }else
                    {
                        listener.onFail(response.body().getStatus());
                    }
                }else
                {
                    listener.onFail(response.body().getStatus());

                }
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                t.printStackTrace();

                if(t instanceof NoConnectivityException)
                    listener.onNoInternetConnect(true);
                else
                    listener.onFail(t.getMessage());
            }
        });

    }
}
