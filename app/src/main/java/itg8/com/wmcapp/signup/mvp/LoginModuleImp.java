package itg8.com.wmcapp.signup.mvp;



import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.common.Retro;
import itg8.com.wmcapp.common.RetroController;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Android itg 8 on 10/9/2017.
 */

public class LoginModuleImp implements LoginMvp.LoginModule {

    //    private Call<List<ProfileModel>> cal;
    private Call<ResponseBody> call;

    public LoginModuleImp() {
    }

    @Override
    public void onDestroy() {
       if (call != null) {
            if (!call.isCanceled())
                call.cancel();

        }
    }


    @Override
    public void onSendToServer(RetroController controller, String url, String username, String password, final LoginMvp.LoginListener listener) {
        call = controller.checkAuthentication(url, "password", username, password);
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if (response.code() == 400) {
                   listener.onFail("Invalid Credential");
                   return;
               }
               if (response.isSuccessful()) {
                   if (response.body() != null) {
                       try {
                           String res = response.body().string();
                           JSONObject object = new JSONObject(res);
                           if (object.has("access_token")) {
                               Prefs.putString(CommonMethod.HEADER, object.getString("access_token"));
                               MyApplication.getInstance().resetRetroAfterLogin();
//                                listener.onSuccess();
                               if (object.has("Firstlogin")) {
                                   if (object.getString("Firstlogin").equalsIgnoreCase("0")) {
                                       listener.onFirstTimeLogin("Success");
                                   }
                                   else {
                                       listener.onSuccess();
                                   }
                               } else {
                                   listener.onFail("Invalid User");
                               }
                           }else
                           {
                               listener.onFail("Invalid User");
                           }



                       } catch (IOException e) {
                           e.printStackTrace();
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }

               } else {
                   listener.onFail(response.message());
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               t.printStackTrace();
               if (t instanceof NoConnectivityException) {
                   listener.onNoInternetConnect(true);

               } else {
                   listener.onError(t);
               }


           }
       });


    }







}