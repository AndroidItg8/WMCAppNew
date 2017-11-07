package itg8.com.wmcapp.board.nbmvp;

import java.util.List;

import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import itg8.com.wmcapp.common.InternetView;

/**
 * Created by swapnilmeshram on 03/11/17.
 */

public interface NBMVP {
    public interface NBView extends InternetView{
        void onListOfNBAvail(List<Object> o);
        void onListEmpty();
        void onListRetrivalFailed();
    }

    public interface NBPresenter extends BaseFragmentPresenter{
        void onStartGettingList();
//        void onStartGettingOfflineList();
    }

    public interface NBModule extends BaseDestroyModule{
        void onStartGettingList(NBListener listener);
        void onStartGettingOfflineList(NBListener listener);
    }

    public interface NBListener{
        void onOnlineList(List<Object> list);
        void onOnlineError(Throwable t);
        void onOfflineError(Throwable t);
    }
}
