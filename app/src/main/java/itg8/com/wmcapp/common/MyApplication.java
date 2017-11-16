package itg8.com.wmcapp.common;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.database.ComplaintTableManipute;
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by swapnilmeshram on 31/10/17.
 */

public class MyApplication extends Application {

    private static final String SHARED = "MyWardhPref";
    private static final String TAG = MyApplication.class.getSimpleName();
    private static final int MY_BACKGROUND_JOB = 0;
    private static MyApplication mInstance;
    private RetroController retroController;
    private CityTableManipulate mDAOCity = null;
    private ComplaintTableManipute complaintTableManipute;
    private Context mContext;


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //  ACRA.init(this);
        mInstance.initPreference();
        retroController = mInstance.buildRetrofit();
         mContext = getApplicationContext();

    }

    private void initPreference() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(SHARED)
                .setUseDefaultSharedPreference(false)
                .build();
    }

    private RetroController buildRetrofit() {
//         HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//         interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//         OkHttpClient.Builder builder = new OkHttpClient.Builder();
//         builder.connectTimeout(5, TimeUnit.MINUTES);
//         builder.addInterceptor(interceptor);
//         builder.readTimeout(5, TimeUnit.MINUTES);
//         if(header!=null)
//             builder.addInterceptor(getHeader(header));
//
//         OkHttpClient client=builder.build();
//         Gson gson = new GsonBuilder().setLenient().create();
//
//         Retrofit retrofit = new Retrofit.Builder()
//                 .baseUrl(CommonMethod.BASE_URL)
//                 .addConverterFactory(GsonConverterFactory.create(gson))
//                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                 .client(client)
//                 .build();

        return Retro.getInstance().getController(Prefs.getString(CommonMethod.HEADER, null), getApplicationContext());
    }


    public RetroController getRetroController() {
        if (retroController == null)
            retroController = buildRetrofit();

        return retroController;

    }


    public void resetRetroAfterLogin() {
        retroController = null;
    }


    public void saveComplaintModel(TempComplaintModel model) {
        complaintTableManipute = new ComplaintTableManipute(getApplicationContext());
        complaintTableManipute.create(model);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myJobSchedular(getApplicationContext());
        } else {

            NetworkChangeReceiver broadcast = new NetworkChangeReceiver();
            IntentFilter filter = new IntentFilter(String.valueOf(ConnectivityManager.TYPE_WIFI));
            filter.addAction(String.valueOf(ConnectivityManager.TYPE_WIFI));
            filter.addAction(String.valueOf(ConnectivityManager.TYPE_MOBILE));
            this.registerReceiver(broadcast, filter);


        }


    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void myJobSchedular(Context context) {
        Log.d(" MyApplication ", "myJobSchedular");

        JobScheduler js =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(
                MY_BACKGROUND_JOB,
                new ComponentName(context, JobNetworkShedule.class));
        //MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                 builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        if (js != null) {
            js.schedule(builder.build());
        }
    }

    public void uploadAllRemaining() {
        complaintTableManipute = new ComplaintTableManipute(getApplicationContext());
        List<TempComplaintModel> listTempComplaintModel = complaintTableManipute.getAllComplaint();
        Log.d("MyApplication ", "uploadAllRemaining");


        for (TempComplaintModel model: listTempComplaintModel
             ) {

           sendModelToServer(
                   model.getDescription(),
                   model.getComplaintName(),
                   model.getAdd(),
                   model.getLatitude(),
                   model.getLongitude(),
                   model.getFilePath(),
                   model.getCityId(),
                   model.getTblId(),
                   model.getShowIdentity());
           Logs.d("Model"+new Gson().toJson(model));


        }

    }

    private void sendModelToServer(String description, String complaintName, String add, Double latitude, Double longitude, String filePath, int cityId, final long tblId, String showIdentity) {
        ProgressRequestBody prb = new ProgressRequestBody(new File(filePath), new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();


            }

            @Override
            public void onFinish() {

            }
        });
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", filePath, prb);
        RequestBody lat = createPartFromString(String.valueOf(latitude));
        RequestBody lang = createPartFromString(String.valueOf(longitude));
        RequestBody addr = createPartFromString(String.valueOf(add));
        RequestBody desc = createPartFromString(String.valueOf(description));
        RequestBody name = createPartFromString(String.valueOf(complaintName));
        //TODO changes: temporary city id;
        RequestBody city = createPartFromInt(cityId);
        RequestBody ident = createPartFromString(showIdentity);
        Observable<ResponseBody> call = getRetroController().addComplaint(getString(R.string.url_add_complaint),part, lat, lang, name, desc, city, ident);
        call.subscribeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(ResponseBody responseBody) throws Exception {
                        return createRegModelFromResponse(responseBody, tblId);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long o) {
                         complaintTableManipute.deleteComplaint(Integer.parseInt(String.valueOf(o)),TempComplaintModel.FIELD_TBL_ID);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private ObservableSource<Long> createRegModelFromResponse(ResponseBody responseBody, long tblId) {
        try {
            String s = responseBody.string();
            JSONObject object = new JSONObject(s);
            if (object.getBoolean("flag")) {
                return Observable.just(tblId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private RequestBody createPartFromString(String val) {
        return RequestBody.create(MediaType.parse("text/plain"), val);
    }

    private RequestBody createPartFromInt(int val) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(val));
    }
}
