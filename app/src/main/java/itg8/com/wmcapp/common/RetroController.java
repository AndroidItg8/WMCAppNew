package itg8.com.wmcapp.common;

import java.util.List;

import io.reactivex.Observable;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.complaint.model.LikeModel;
import itg8.com.wmcapp.profile.ProfileModel;
import itg8.com.wmcapp.registration.RegistrationModel;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by swapnilmeshram on 31/10/17.
 */

public interface RetroController {
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

//    Password:123456
//    ConfirmPassword:123456
//    FullName:Aman Ambarte
//    MobileNumber:786786786786
//    UserRoles:AppUser
//    Email:Aman@gmail.com

    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> sendRegistrationInfoToserver(@Url String url,
                                                         @Field("Password") String password,
                                                         @Field("ConfirmPassword") String cPassword,
                                                         @Field("FullName") String name,
                                                         @Field("MobileNumber") String mobile,
                                                         @Field("UserRoles") String appUser,
                                                         @Field("Email") String emailId);

    //    OldPassword:WMC123
//    NewPassword:123456
    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> changePassword(@Url String url, @Field("OldPassword") String oldpswd, @Field("NewPassword") String newpswd);


    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<RegistrationModel> forgetPaswd(@Url String url, @Field("Email") String email);

//    Category_fkid:1
//    ComplaintName:Dash
//    ComplaintDescription:drfgv
//    Longitutde:sdfgv
//    Latitude:rgs
//    City_fkid:1

    //



    @GET()
    Observable<ResponseBody> loadComplaint(@Url String url, @Query("skip") int page, @Query("pageSize") int limit, @Query("cityid") int cityId);

    @GET()
    Observable<ResponseBody> loadNoticeBoard(@Url String url, @Query("skip") int page, @Query("pageSize") int limit, @Query("cityid") int cityId);


    @Multipart
    @POST()
    Observable<ResponseBody> addComplaint(@Url String url,
                                          @Part MultipartBody.Part file,
                                          @Part("Latitude") RequestBody lat,
                                          @Part("Longitutde") RequestBody lang,
                                          @Part("ComplaintName") RequestBody addr,
                                          @Part("ComplaintDescription") RequestBody desc,
                                          @Part("City_fkid") RequestBody cityId,
                                          @Part("ShowIdentity") RequestBody identity);

    @GET()
    Call<List<TorisumModel>> getTorisumList(@Url String loadUrl);

    @GET()
    Call<List<ProfileModel>> getProfile(@Url String url);

    //    Master_fkid:1
//    SubMaster_fkid:5
//    Likes:1
    @FormUrlEncoded
    @POST()
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Call<LikeModel> sendLike(@Url String url, @Field("Master_fkid") int complaint, @Field("SubMaster_fkid") int subMaster_fkid, @Field("Likes") int i);
}
