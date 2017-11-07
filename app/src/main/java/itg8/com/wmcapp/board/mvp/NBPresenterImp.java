package itg8.com.wmcapp.board.mvp;

import android.content.Context;

import java.util.List;

import itg8.com.wmcapp.common.BaseWeakPresenter;

public class NBPresenterImp extends BaseWeakPresenter<NBMVP.NBView> implements NBMVP.NBListener, NBMVP.NBPresenter {

    itg8.com.wmcapp.board.mvp.NBMVP.NBModule module;
    private static final int LIMIT = 10;
    private int page=0;

    public NBPresenterImp(NBMVP.NBView nbView) {
        super(nbView);
        module = new NBModuleImp();
    }

    @Override
    public void onDetach() {
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
    public void onLoadMore() {
        getItems(page++,LIMIT);
    }

    private void getItems(int page, int limit) {
        if(hasView()){
            getView().onPaginationError(false);
            getView().onShowPaginationLoading(true);
            module.onStartLoadingList(page,limit,this);
        }
    }

    @Override
    public void onNBListAvailable() {
        if(hasView()){
            getView().onShowPaginationLoading(false);
            getView().onNBListAvailable();
        }
    }

    @Override
    public void onNoMoreList() {
        if(hasView()){
            getView().onShowPaginationLoading(false);
            getView().onNoMoreList();
        }
    }

    @Override
    public void onPaginationError() {
        if(hasView()){
            getView().onShowPaginationLoading(false);
            getView().onPaginationError(true);
        }
    }
}
