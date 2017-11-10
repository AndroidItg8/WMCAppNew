package itg8.com.wmcapp.complaint.mvp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import itg8.com.wmcapp.common.BaseDestroyModule;
import itg8.com.wmcapp.common.BaseFragmentPresenter;
import itg8.com.wmcapp.complaint.model.ComplaintModel;

/**
 * Created by swapnilmeshram on 06/11/17.
 */

public interface ComplaintMVP {
    public interface ComplaintView{
        void onComplaintListAvailable(List<ComplaintModel> o);
        void onNoMoreList();
        void onShowPaginationLoading(boolean show);
        void onPaginationError(boolean show);

        void hideProgress();
        void onSuccessLike(ComplaintModel model);
        void onFailedLike(String s);

        void showProgress();
    }

    public interface ComplaintPresenter extends BaseFragmentPresenter{

        void onDetach();

        void onAttach(Context context);

        void onPause();

        void onResume();

        void onLoadMore();

        void onLoadMoreItem(String string);

        RecyclerView.OnScrollListener scrollListener(LinearLayoutManager layoutManager);

        void onVoteUp(String string, int pkid, ComplaintModel model);
    }

    public interface ComplaintListener{
        void onComplaintListAvailable(List<ComplaintModel> o);
        void onNoMoreList();
        void onPaginationError();
        void onSuccess(ComplaintModel model);
        void onFailed(String s);
    }

    public interface ComplaintModule extends BaseDestroyModule{
        void onStartLoadingList(String loadUrl, int page, int limit, ComplaintListener listener);

        void onSendLikesToServer(String url, int SubMaster_fkid, ComplaintModel model, ComplaintListener listener);
    }

}
