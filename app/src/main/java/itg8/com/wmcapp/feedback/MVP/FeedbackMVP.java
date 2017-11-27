package itg8.com.wmcapp.feedback.MVP;

import android.view.View;


/**
 * Created by Android itg 8 on 11/27/2017.
 */

public interface FeedbackMVP {

    public  interface  FeedbackView
    {
        String getTitle();
        String getDescription();
        int getRating();
        void onSuccess(String message);
        void onFail(String message);
        void onError(Object t);

        void onTitleInvalid(String err);

        void onDescriptionInvalid(String err);
        void onRatingInvalid(String err);

        void showProgress();

        void hideProgress();
        void onNoInternetConnect(boolean b);


    }
    public interface FeedbackPresenter
    {
        void onDestroy();
        void onFeedbackClicked(View view);
        void onNoInternetConnect(boolean b);
        void onTitleInvalid(String err);
        void onDescriptionInvalid(String err);
        void onRatingInvalid(String err);


    }

    public interface FeedbackModule
    {
        void onDestroy();
        void onSendToServer( String url, String title, String description, int  rating, FeedbackMVP.FeedbackListener listener);

    }

    public interface FeedbackListener{
        void onSuccess(String message);
        void onFail(String message);
        void onError(Object t);

        void showProgress();
        void hideProgress();
        void onNoInternetConnect(boolean b);
    }
}
