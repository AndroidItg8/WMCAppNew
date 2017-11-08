package itg8.com.wmcapp.torisum.mvp;

/**
 * Created by Android itg 8 on 11/8/2017.
 */

 public interface BaseFragmentView {
     void showProgress();
     void hideProgress();
    void onNoInternetConnection(boolean b);
     void onError(String message);

}
