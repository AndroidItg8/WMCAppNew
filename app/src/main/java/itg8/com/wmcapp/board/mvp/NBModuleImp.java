package itg8.com.wmcapp.board.mvp;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
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
import itg8.com.wmcapp.registration.RegistrationModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by swapnilmeshram on 03/11/17.
 */

public class NBModuleImp  implements itg8.com.wmcapp.board.mvp.NBMVP.NBModule {


    private Observable<ResponseBody> call;

    @Override
    public void onDestroy() {

    }


    @Override
    public void onStartLoadingList(String url, final int page, int limit, final NBMVP.NBListener listener) {
        call= MyApplication.getInstance().getRetroController().loadNoticeBoard(url,page,limit,0);
        call.subscribeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<List<NoticeBoardModel>>>() {
                    @Override
                    public ObservableSource<List<NoticeBoardModel>> apply(ResponseBody responseBody) throws Exception {
                        String s=responseBody.string();
                        List<NoticeBoardModel> models = null;
                        if(s!=null){
                            models=new Gson().fromJson(s,new TypeToken<List<NoticeBoardModel>>(){}.getType());
                        }
                        if (models != null) {
                            return Observable.just(models);
                        }
                        return null;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NoticeBoardModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<NoticeBoardModel> o) {
                        Logs.d("SIZE OF LIST:"+o.size()+" page Number "+page);
                        listener.onNBListAvailable(o);
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
