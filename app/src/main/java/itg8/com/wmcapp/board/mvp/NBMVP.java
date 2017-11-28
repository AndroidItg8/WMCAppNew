package itg8.com.wmcapp.board.mvp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import ru.alexbykov.nopaginate.callback.OnLoadMore;

/**
 * Created by swapnilmeshram on 06/11/17.
 */

public interface NBMVP {
    public interface NBView{
        void onNBListAvailable(List<NoticeBoardModel> o);
        void onNoMoreList();
        void onShowPaginationLoading(boolean show);
        void onPaginationError(boolean show);

    }

    public interface NBPresenter extends BaseFragmentPresenter {

        void onDetach();

        void onAttach(Context context);

        void onPause();

        void onResume();

        void onLoadMore();

        void onLoadMoreItems(String string);

        RecyclerView.OnScrollListener scrollListener(LinearLayoutManager layoutManager);

    }

    public interface NBListener {
        void onNBListAvailable(List<NoticeBoardModel> o);

        void onNoMoreList();

        void onPaginationError();

    }

    public interface NBModule extends BaseDestroyModule {
        void onStartLoadingList(String url, int page, int limit, NBListener listener);

    }

}
