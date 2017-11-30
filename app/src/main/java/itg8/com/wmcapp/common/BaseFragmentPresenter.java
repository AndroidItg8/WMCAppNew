package itg8.com.wmcapp.common;

import android.content.Context;

/**
 * Created by swapnilmeshram on 03/11/17.
 */

public interface BaseFragmentPresenter {
    void onDetach();
    void onAttach(Context context);
    void onPause();
    void onResume();
     void onError(String message);

}
