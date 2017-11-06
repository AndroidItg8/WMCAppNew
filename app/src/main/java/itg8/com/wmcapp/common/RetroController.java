package itg8.com.wmcapp.common;

import java.util.List;

import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by swapnilmeshram on 31/10/17.
 */

 public  interface RetroController {
    @GET
    Call<List<CityModel>> getCityFromServer(@Url String url);

    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<ResponseBody> checkAuthentication(@Url String url,
                                           @Field("grant_type") String password,
                                           @Field("username") String emailId,
                                           @Field("password") String pswd);

//   Email:vijay@gmail.com
//   Password:123456
//   ConfirmPassword:123456
//   UserRoles:AppUser

   @FormUrlEncoded
   @POST()
   @Headers("Content-Type:application/x-www-form-urlencoded")
  Call<RegistrationModel>  sendRegistrationInfoToserver(@Url String url,
                                                        @Field("Email") String emailId,
                                                        @Field("password") String password,
                                                        @Field("ConfirmPassword")String cPassword,
                                                        @Field("UserRoles") String appUser);
}
