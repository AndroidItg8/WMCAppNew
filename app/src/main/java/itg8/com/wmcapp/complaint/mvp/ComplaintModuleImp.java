package itg8.com.wmcapp.complaint.mvp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
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
        call= MyApplication.getInstance().getRetroController().loadComplaint(loadUrl,page,limit,0);
        call.subscribeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<List<ComplaintModel>>>() {
                    @Override
                    public ObservableSource<List<ComplaintModel>> apply(ResponseBody responseBody) throws Exception {
                        String s=responseBody.string();
                        List<ComplaintModel> models = null;
                        if(s!=null){
                            models=new Gson().fromJson(s,new TypeToken<List<ComplaintModel>>(){}.getType());
                        }
                        if (models != null) {
                            return Observable.just(models);
                        }
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ComplaintModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ComplaintModel> o) {
                        listener.onComplaintListAvailable(o);
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
