package itg8.com.wmcapp.board.mvp;

import android.content.Context;

import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import ru.alexbykov.nopaginate.callback.OnLoadMore;

/**
 * Created by swapnilmeshram on 06/11/17.
 */

public interface NBMVP {
    public interface NBView{
        void onNBListAvailable();
        void onNoMoreList();
        void onShowPaginationLoading(boolean show);
        void onPaginationError(boolean show);
    }

    public interface NBPresenter extends BaseFragmentPresenter, OnLoadMore {

        @Override
        void onDetach();

        @Override
        void onAttach(Context context);

        @Override
        void onPause();

        @Override
        void onResume();

        @Override
        void onLoadMore();
    }

    public interface NBListener{
        void onNBListAvailable();
        void onNoMoreList();
        void onPaginationError();
    }

    public interface NBModule extends BaseDestroyModule {
        void onStartLoadingList(int page, int limit,NBListener listener);
    }

}
