package itg8.com.wmcapp.complaint.mvp;

import android.content.Context;

import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import ru.alexbykov.nopaginate.callback.OnLoadMore;

/**
 * Created by swapnilmeshram on 06/11/17.
 */

public interface ComplaintMVP {
    public interface ComplaintView{
        void onComplaintListAvailable();
        void onNoMoreList();
        void onShowPaginationLoading(boolean show);
        void onPaginationError(boolean show);
    }

    public interface ComplaintPresenter extends BaseFragmentPresenter, OnLoadMore {

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

        void onLoadMoreItem(String string);
    }

    public interface ComplaintListener{
        void onComplaintListAvailable();
        void onNoMoreList();
        void onPaginationError();
    }

    public interface ComplaintModule extends BaseDestroyModule{
        void onStartLoadingList(String loadUrl, int page, int limit, ComplaintListener listener);
    }

}
