package itg8.com.wmcapp.complaint.mvp;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * Created by swapnilmeshram on 06/11/17.
 */

class ComplaintModuleImp implements ComplaintMVP.ComplaintModule {
    Observable<ResponseBody> call;
    @Override
    public void onDestroy() {

    }

    @Override
    public void onStartLoadingList(String loadUrl, int page, int limit, final ComplaintMVP.ComplaintListener listener) {
        call= MyApplication.getInstance().getRetroController().loadComplaint(loadUrl,page,limit);
        call.subscribeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(ResponseBody responseBody) throws Exception {
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        listener.onComplaintListAvailable();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof HttpException) {
                            // We had non-2XX http error
                            Logs.d("IN HTTPEXCEPTION: "+throwable.getMessage());
                        }
                        if (throwable instanceof IOException) {
                            // A network or conversion error happened
                            Logs.d("IN IOException: "+throwable.getMessage());
                        }
                        listener.onPaginationError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
