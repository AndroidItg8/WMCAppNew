package itg8.com.wmcapp.board;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.model.TempNoticeBoardModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.MyApplication;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobNoticeBoardShedule extends JobService {


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        String json = jobParameters.getExtras().getString(CommonMethod.SYNC_NB_JOB);

     List<TempNoticeBoardModel> list = new Gson().fromJson(json,new TypeToken<List<TempNoticeBoardModel>>(){}.getType());
        for (TempNoticeBoardModel model:list) {
            MyApplication.getInstance().deleteNoticeItemFromServer(getString(R.string.url_delete_notice),model.getNoticeId());
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
