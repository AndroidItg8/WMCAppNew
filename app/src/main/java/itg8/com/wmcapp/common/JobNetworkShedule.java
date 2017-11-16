package itg8.com.wmcapp.common;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import android.support.annotation.RequiresApi;
import android.util.Log;


/**
 * Created by Android itg 8 on 11/13/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobNetworkShedule extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("JobNetworkShedule ", "onStartJob");

        MyApplication.getInstance().uploadAllRemaining();



        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
