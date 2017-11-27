package itg8.com.wmcapp.feedback.MVP;

import android.text.TextUtils;
import android.view.View;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseWeakPresenter;
import itg8.com.wmcapp.common.MyApplication;

/**
 * Created by Android itg 8 on 11/27/2017.
 */

public class FeedbackPresenterImp extends BaseWeakPresenter<FeedbackMVP.FeedbackView> implements FeedbackMVP.FeedbackPresenter, FeedbackMVP.FeedbackListener {
    private final FeedbackMVP.FeedbackModule module;

    public FeedbackPresenterImp(FeedbackMVP.FeedbackView feedbackView) {
        super(feedbackView);
         module = new FeedbackModuleImp();
    }

    @Override
    public void onDestroy() {
        module.onDestroy();
        if (hasView()) {
            detachView();
        }
    }

    @Override
    public void onFeedbackClicked(View view) {
        if (hasView()) {
            boolean isValid = true;
            String title = getView().getTitle();
            String description = getView().getDescription();
            int rating = getView().getRating();
            if (TextUtils.isEmpty(title)) {
                isValid = false;
                getView().onTitleInvalid(view.getContext().getString(R.string.empty));
            }
            if (TextUtils.isEmpty(description)) {
                isValid = false;
                getView().onDescriptionInvalid(view.getContext().getString(R.string.empty));
            }

                if(rating == 0)
                {
                    isValid = false;
                    getView().onRatingInvalid(view.getContext().getString(R.string.invalid_rating));
                }


            if (isValid) {
                getView().showProgress();
                module.onSendToServer( view.getContext().getString(R.string.url_feedback), title, description, rating ,this);

            }
        }
    }

    @Override
    public void onSuccess(String mes) {
        if (hasView()) {
            getView().hideProgress();

            getView().onSuccess(mes);
        }

    }



    @Override
    public void onTitleInvalid(String err) {
        if (hasView()) {
            getView().hideProgress();

            getView().onTitleInvalid(err);
        }
    }

    @Override
    public void onDescriptionInvalid(String err) {
        if (hasView()) {
            getView().hideProgress();

            getView().onDescriptionInvalid(err);
        }
    }

    @Override
    public void onRatingInvalid(String err) {
        if (hasView()) {
            getView().hideProgress();

            getView().onRatingInvalid(err);
        }
    }

    @Override
    public void onFail(String message) {
        if (hasView()) {
            hideProgress();
            getView().onFail(message);

        }
    }

    @Override
    public void onError(Object t) {
        if (hasView()) {
            hideProgress();
            getView().onError(t);

        }
    }

    @Override
    public void showProgress() {
        if (hasView()) {
            getView().showProgress();

        }
    }

    @Override
    public void hideProgress() {
        if (hasView()) {
            getView().hideProgress();

        }
    }

    @Override
    public void onNoInternetConnect(boolean b) {
        if (hasView()) {
            hideProgress();
            getView().onNoInternetConnect(b);
        }

    }
}
