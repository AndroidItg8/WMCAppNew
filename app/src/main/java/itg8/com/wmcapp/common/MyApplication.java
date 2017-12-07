package itg8.com.wmcapp.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.JobNoticeBoardShedule;
import itg8.com.wmcapp.board.model.DeleteNoticeModel;
import itg8.com.wmcapp.board.model.TempNoticeBoardModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.database.ComplaintTableManipute;
import itg8.com.wmcapp.database.NBTableManipulate;
import itg8.com.wmcapp.profile.ProfileModel;
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by swapnilmeshram on 31/10/17.
 */

public class MyApplication extends Application {

    private static final String SHARED = "MyWardhPref";
    private static final String TAG = MyApplication.class.getSimpleName();
    private static final int MY_BACKGROUND_JOB = 0;
    private static final int MY_BACKGROUND_JOB_CALL_NETWORK = 1;
    private static MyApplication mInstance;
    private RetroController retroController;
    private CityTableManipulate mDAOCity = null;
    private ComplaintTableManipute complaintTableManipute;
    private Context mContext;
    private ProfileModel profileModel;
    private NBTableManipulate noticeTableManipute;
    private int FROM_SUCCESS = 1;
    private int FROM_FAILED = 2;
    private List<Integer> nbID;


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
        noticeTableManipute = new NBTableManipulate(getApplicationContext());


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
        final List<TempComplaintModel> listTempComplaintModel = complaintTableManipute.getAllComplaint();
        Log.d("MyApplication ", "uploadAllRemaining");
        if (listTempComplaintModel != null && listTempComplaintModel.size() > 0) {


            for (TempComplaintModel model : listTempComplaintModel) {
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
                Logs.d("Model" + new Gson().toJson(model));
            }
        }


    }

    private Observable<ResponseBody> sendModelToServer(String description, String complaintName, String add, Double latitude, Double longitude, String filePath, int cityId, final long tblId, String showIdentity) {
        File file = new File(filePath);
        ProgressRequestBody prb = new ProgressRequestBody(file, new ProgressRequestBody.UploadCallbacks() {
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
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), prb);
        RequestBody lat = createPartFromString(String.valueOf(latitude));
        RequestBody lang = createPartFromString(String.valueOf(longitude));
        RequestBody addr = createPartFromString(String.valueOf(add));
        RequestBody desc = createPartFromString(String.valueOf(description));
        RequestBody name = createPartFromString(String.valueOf(complaintName));
        //TODO changes: temporary city id;
        RequestBody city = createPartFromInt(cityId);
        RequestBody ident = createPartFromString(showIdentity);
        Observable<ResponseBody> call = getRetroController().addComplaint(getString(R.string.url_add_complaint), part, lat, lang, name, desc, city, ident);
//        call.subscribeOn(Schedulers.io()).map(new Function<ResponseBody, TempComplaintModel>() {
//            @Override
//            public TempComplaintModel apply(ResponseBody responseBody) throws Exception {
//                return new TempComplaintModel(responseBody,tableId);
//            }
//        })
        return call;
//        call.subscribeOn(Schedulers.io())
//                .flatMap(new Function<ResponseBody, ObservableSource<Long>>() {
//                    @Override
//                    public ObservableSource<Long> apply(ResponseBody responseBody) throws Exception {
//                        return createRegModelFromResponse(responseBody, tblId);
//                    }
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long o) {
//                         complaintTableManipute.deleteComplaint(Integer.parseInt(String.valueOf(o)),TempComplaintModel.FIELD_TBL_ID);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    private ObservableSource<Long> createRegModelFromResponse(ResponseBody responseBody, long tblId) {
        try {
            String s = responseBody.string();
            Logs.d("Response Complaint Model:" + s);
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

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public void getProfile(final CommonMethod.ProfileSetListener listener) {
        if (profileModel != null) {
            listener.onSetProfile(profileModel);
        } else {
            Call<List<ProfileModel>> cal = getRetroController().getProfile(getString(R.string.url_profile));
            cal.enqueue(new Callback<List<ProfileModel>>() {
                @Override
                public void onResponse(Call<List<ProfileModel>> call, Response<List<ProfileModel>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().size() > 0) {
                            Logs.d("responseBody" + new Gson().toJson(response.body()));
                            listener.onSetProfile(response.body().get(0));
                            setProfileModel(response.body().get(0));
                        } else {
                            listener.onFailed("Download Failed");
                        }
                    } else {
                        listener.onFailed("Download Failed");
                    }

                }

                @Override
                public void onFailure(Call<List<ProfileModel>> call, Throwable t) {
                    if (t instanceof NoConnectivityException) {
                        //  listener.onNoInternetConnect(true);
                    } else {
                        listener.onFailed(t.getMessage());
                    }

                }
            });
        }
    }

    public void deleteNoticeItemFromServer(String url, final int pkid) {
        Call<RegistrationModel> call = getRetroController().deleteNBFromServer(url, pkid);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().isFlag()) {
                        saveTempNoticeBoardModel(pkid, FROM_SUCCESS);
                        return;
                    } else {
                        saveTempNoticeBoardModel(pkid, FROM_FAILED);


                    }
                } else {
                    saveTempNoticeBoardModel(pkid, FROM_FAILED);
                }
                callRetryNoticeDelete();
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable throwable) {
                if (throwable instanceof HttpException) {
                    // We had non-2XX http error
                    Logs.d("IN HTTPEXCEPTION: " + throwable.getMessage());
                }
                if (throwable instanceof IOException) {
                    // A network or conversion error happened
                    Logs.d("IN IOException: " + throwable.getMessage());
                }
                saveTempNoticeBoardModel(pkid, FROM_FAILED);
                callRetryNoticeDelete();


            }

        });
    }

    private void callRetryNoticeDelete() {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.HOUR,1);
        List<TempNoticeBoardModel> tempNoticeBoardModelList=noticeTableManipute.getNoticeBoardItem(CommonMethod.TYPE_NOTICE_UNSYNC,TempNoticeBoardModel.FIELD_TYPE);
        String list = new Gson().toJson(tempNoticeBoardModelList);
        Logs.d("CallRetryNotice");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myNoticeBoardJobSchedular(getApplicationContext(),list);
        } else {
//
//            NetworkChangeReceiver broadcast = new NetworkChangeReceiver();
//            IntentFilter filter = new IntentFilter(String.valueOf(ConnectivityManager.TYPE_WIFI));
//            filter.addAction(String.valueOf(ConnectivityManager.TYPE_WIFI));
//            filter.addAction(String.valueOf(ConnectivityManager.TYPE_MOBILE));
//            this.registerReceiver(broadcast, filter);


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void myNoticeBoardJobSchedular(Context context, String list) {
        Log.d(" MyApplication ", "myJobSchedular");

        JobScheduler js =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        @SuppressLint("JobSchedulerService") JobInfo.Builder builder = new JobInfo.Builder(
                MY_BACKGROUND_JOB_CALL_NETWORK,
                new ComponentName(context, JobNoticeBoardShedule.class));
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(CommonMethod.SYNC_NB_JOB, list);
        builder.setExtras(persistableBundle);
        Logs.d("OnPut List:"+new Gson().toJson(list));
//        builder.setPersisted(true); //Job scheduled to work after reboot
//        builder.setPeriodic(Calendar.MINUTE * 30);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        if (js != null) {
            js.schedule(builder.build());
        }
    }

    private void saveTempNoticeBoardModel(int pkid, int from) {
        TempNoticeBoardModel model = new TempNoticeBoardModel();
        model.setNoticeId(pkid);
        if (from == FROM_SUCCESS) {
            model.setType(CommonMethod.TYPE_NOTICE_SYNC);
            noticeTableManipute.deleteNBItem(pkid,TempNoticeBoardModel.FIELD_NOTICE_ID);
        }
        else {
            model.setType(CommonMethod.TYPE_NOTICE_UNSYNC);
        }
        noticeTableManipute.create(model);

    }

     public void getDeletedNBList()
     {
         Call<List<DeleteNoticeModel>> call = getRetroController().getDeleteNBList(getString(R.string.url_delete_list));
         call.enqueue(new Callback<List<DeleteNoticeModel>>() {
             @Override
             public void onResponse(Call<List<DeleteNoticeModel>> call, Response<List<DeleteNoticeModel>> response) {
                 if(response.isSuccessful()) {
                     if (response.body() != null) {
                         saveTempSyncNoticeBoardModel(response.body());

                     }
                 }
             }

             @Override
             public void onFailure(Call<List<DeleteNoticeModel>> call, Throwable throwable) {

                 if (throwable instanceof HttpException) {
                     // We had non-2XX http error
                     Logs.d("IN HTTPEXCEPTION: " + throwable.getMessage());
                 }
                 if (throwable instanceof IOException) {
                     // A network or conversion error happened
                     Logs.d("IN IOException: " + throwable.getMessage());
                 }

             }
         });
     }

    private void saveTempSyncNoticeBoardModel(List<DeleteNoticeModel> body) {
        if(noticeTableManipute.getAll().size()<=0) {
            TempNoticeBoardModel tempNBModel;
            for (DeleteNoticeModel modelDelete : body) {
                tempNBModel = new TempNoticeBoardModel();
                tempNBModel.setTblId(modelDelete.getNoticeFkid());
                tempNBModel.setType(CommonMethod.SYNC_NB_JOB);
                noticeTableManipute.create(tempNBModel);
            }
        }
    }

    public void setNbID(List<Integer> nbID) {
        this.nbID = nbID;
    }

    public List<Integer> setNBListToArray() {

        if(nbID== null)
        {
            List<TempNoticeBoardModel> list =  noticeTableManipute.getAll();
                nbID = new ArrayList<>(list.size());
                for(int i =0; i<list.size();i++)
                {
                    nbID.add(list.get(i).getNoticeId());
                }

        }
        return nbID;

    }

     public void deleteNoticeBoard()
     {
         noticeTableManipute.getDeleteAll();
     }


}

