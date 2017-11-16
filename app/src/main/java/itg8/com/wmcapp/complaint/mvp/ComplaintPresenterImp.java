package itg8.com.wmcapp.complaint.mvp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.complaint.model.ComplaintModel;

/**
 * Created by swapnilmeshram on 06/11/17.
 */

public class ComplaintPresenterImp extends BaseWeakPresenter<ComplaintMVP.ComplaintView> implements ComplaintMVP.ComplaintPresenter, ComplaintMVP.ComplaintListener {

    private static final int LIMIT = 10;
    ComplaintMVP.ComplaintModule module;
    private int page=0;
    private String loadUrl;
    private boolean isLoading;
    private boolean isFinished=false;

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
    public void onError(String message) {

    }

    @Override
    public void onLoadMore() {
        getItems(page,LIMIT);
    }

    @Override
    public void onLoadMoreItem(String url) {
        this.loadUrl=url;
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

                if (!isLoading && !isFinished)
                {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0)
                    {

                        page++;

                        getItems(page,LIMIT);
                    }
                }

            }
        };
    }

    @Override
    public void onVoteUp(String url, int pkid, ComplaintModel model, int position) {
        if(hasView())
        {
             getView().showProgress(position);
            module.onSendLikesToServer(url, pkid, model,  position,this);
        }


    }

    private void getItems(int page, int limit) {
        if(hasView()){
            getView().onPaginationError(false);
            getView().onShowPaginationLoading(true);
            isLoading=true;
            module.onStartLoadingList(loadUrl,page,limit,this);
        }
    }

    @Override
    public void onComplaintListAvailable(List<ComplaintModel> o) {
        if(hasView()){
            getView().onShowPaginationLoading(false);
            if(o.size()>0)
                getView().onComplaintListAvailable(o);
            else {
                getView().onNoMoreList();
                isFinished=true;
            }
            isLoading=false;
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

    @Override
    public void onSuccess(ComplaintModel model, int position) {
         if(hasView())
         {
             getView().hideProgress();
             getView().onSuccessLike(model, position);
         }

    }

    @Override
    public void onFailed(String s) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onFailedLike(s);
        }


    }

    @Override
    public void onFailedList(String s) {
        if(hasView())
        {
            getView().hideProgress();
            getView().onFailedLike(s);
        }
    }
}
