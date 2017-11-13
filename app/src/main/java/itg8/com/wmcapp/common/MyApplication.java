package itg8.com.wmcapp.common;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.database.ComplaintTableManipute;

/**
 * Created by swapnilmeshram on 31/10/17.
 */

public class MyApplication extends Application{

    private static final String SHARED = "MyWardhPref";
    private static final String TAG = MyApplication.class.getSimpleName();
    private static final int MY_BACKGROUND_JOB = 0;
    private static MyApplication mInstance;
    private RetroController retroController;
    private CityTableManipulate mDAOCity = null;
    private ComplaintTableManipute complaintTableManipute;


    public static synchronized MyApplication getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //  ACRA.init(this);
        mInstance.initPreference();
        retroController = mInstance.buildRetrofit();

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myJobSchedular(getApplicationContext());
        }else
        {
            //BroadCast Use
        }


    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void myJobSchedular(Context context) {
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
}
