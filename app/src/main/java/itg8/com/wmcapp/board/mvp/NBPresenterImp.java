package itg8.com.wmcapp.board.mvp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.common.BaseWeakPresenter;

public class NBPresenterImp extends BaseWeakPresenter<NBMVP.NBView> implements NBMVP.NBListener, NBMVP.NBPresenter {

    private static final int LIMIT = 10;
    itg8.com.wmcapp.board.mvp.NBMVP.NBModule module;
    private int page = 0;
    private boolean isLoading;
    private boolean isFinished = false;

    public NBPresenterImp(NBMVP.NBView nbView) {
        super(nbView);
        module = new NBModuleImp();
    }

    @Override
    public void onDetach() {
        module.onDestroy();
        if(hasView())
            detachView();
    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onLoadMore() {
        getItems(page, LIMIT);
    }

    @Override
    public void onLoadMoreItems(String url) {
        this.url = url;
        onLoadMore();
    }

    @Override
    public RecyclerView.OnScrollListener scrollListener(final LinearLayoutManager linearLayoutManager) {
        return new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isFinished) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {

                        page++;

                        getItems(page, LIMIT);
                    }
                }
            }
        };
    }


    private void getItems(int page, int limit) {
        if (hasView()) {
            getView().onPaginationError(false);
            getView().onShowPaginationLoading(true);
            isLoading = true;
            module.onStartLoadingList(url, page, limit, this);
        }
    }

    @Override
    public void onNBListAvailable(List<NoticeBoardModel> o) {
        if (hasView()) {
            getView().onShowPaginationLoading(false);
            if (o.size() > 0)
                getView().onNBListAvailable(o);
            else {
                getView().onNoMoreList();
                isFinished = true;
            }
            isLoading = false;
        }
    }

    @Override
    public void onNoMoreList() {
        if (hasView()) {
            getView().onShowPaginationLoading(false);
            getView().onNoMoreList();
            isLoading = false;
        }
    }

    @Override
    public void onPaginationError() {
        if (hasView()) {
            getView().onShowPaginationLoading(false);
            getView().onPaginationError(true);
            isLoading = false;
        }
    }

}
