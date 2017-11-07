package itg8.com.wmcapp.complaint.mvp;

import android.content.Context;

import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.complaint.mvp.ComplaintMVP;

/**
 * Created by swapnilmeshram on 06/11/17.
 */

public class ComplaintPresenterImp extends BaseWeakPresenter<ComplaintMVP.ComplaintView> implements ComplaintMVP.ComplaintPresenter, ComplaintMVP.ComplaintListener {

    private static final int LIMIT = 10;
    ComplaintMVP.ComplaintModule module;
    private int page=0;
    private String loadUrl;

    public ComplaintPresenterImp(ComplaintMVP.ComplaintView complaintView) {
        super(complaintView);
        module=new ComplaintModuleImp();
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
        getItems(page++,LIMIT,loadUrl);
    }

    @Override
    public void onLoadMoreItem(String url) {
        this.loadUrl=url;
        onLoadMore();
    }

    private void getItems(int page, int limit, String loadUrl) {
        if(hasView()){
            getView().onPaginationError(false);
            getView().onShowPaginationLoading(true);
            module.onStartLoadingList(loadUrl,page,limit,this);
        }
    }

    @Override
    public void onComplaintListAvailable() {
        if(hasView()){
            getView().onShowPaginationLoading(false);
            getView().onComplaintListAvailable();
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
