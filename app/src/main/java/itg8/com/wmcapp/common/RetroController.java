package itg8.com.wmcapp.common;

import java.util.List;

import io.reactivex.Observable;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

//    OldPassword:WMC123
//    NewPassword:123456
@FormUrlEncoded
@POST()
@Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> changePassword(@Url String url, @Field("OldPassword") String oldpswd, @Field("NewPassword") String newpswd);



    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> forgetPaswd(@Url String url,@Field("Email") String email);

//    Category_fkid:1
//    ComplaintName:Dash
//    ComplaintDescription:drfgv
//    Longitutde:sdfgv
//    Latitude:rgs
//    City_fkid:1

    //
    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> AddComplaint(@Url String url,
                                         @Field("Category_fkid") String coategoryId,
                                         @Field("ComplaintName") String  complaintName,
                                         @Field("ComplaintDescription") String description,
                                         @Field("Longitutde") String longititude,
                                         @Field("Latitude") String latitiude,
                                         @Field("City_fkid") String cityId
                                         );



    @GET()
    Observable<ResponseBody> loadComplaint(@Url String url, @Query("skip") int page, @Query("pageSize") int limit);

    @GET()
    Observable<ResponseBody> loadNoticeBoard(@Url String url,int page, int limit);


}
