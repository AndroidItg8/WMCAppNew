package itg8.com.wmcapp.feedback;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.feedback.MVP.FeedbackMVP;
import itg8.com.wmcapp.feedback.MVP.FeedbackPresenterImp;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment implements FeedbackMVP.FeedbackView, View.OnClickListener, TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.ratin_dailoue)
    RatingBar ratinDailoue;
    @BindView(R.id.txt_title)
    EditText txtTitle;
    @BindView(R.id.input_layout_title)
    TextInputLayout inputLayoutTitle;
    @BindView(R.id.txt_rating_desc)
    EditText txtRatingDesc;
    @BindView(R.id.input_layout_des)
    TextInputLayout inputLayoutDes;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.fab_submit)
    FloatingActionButton fabSubmit;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.lbl_count)
    CustomFontTextView lblCount;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Snackbar snackbar;
    CommonMethod.OnBackPressListener listener;
    private Context mContext;
    private FeedbackMVP.FeedbackPresenter presenter;
    CommonMethod.onSetToolbarTitle titleListener;
    private StringBuilder sb;


    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new FeedbackPresenterImp(this);
        titleListener.onSetTitle(getString(R.string.feedback));
        inputLayoutDes.getEditText().addTextChangedListener(this);
        fabSubmit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.onDestroy();
        titleListener = null;
        listener = null;
        unbinder.unbind();
    }

    @Override
    public String getTitle() {
        return txtTitle.getText().toString().trim();
    }

    @Override
    public String getDescription() {
        return txtRatingDesc.getText().toString().trim();
    }

    @Override
    public int getRating() {
        return (int) ratinDailoue.getRating();
    }

    @Override
    public void onSuccess(String message) {
        showSnackbar(false, CommonMethod.FROM_ERROR, message);
        listener.onBackPress();


    }

    @Override
    public void onFail(String message) {
        showSnackbar(false, CommonMethod.FROM_ERROR, message);


    }

    @Override
    public void onError(Object t) {
        showSnackbar(false, CommonMethod.FROM_ERROR, t.toString());


    }

    @Override
    public void onTitleInvalid(String err) {
        inputLayoutTitle.setError(err);

    }

    @Override
    public void onDescriptionInvalid(String err) {
        inputLayoutDes.setError(err);

    }

    @Override
    public void onRatingInvalid(String err) {
        showSnackbar(false, CommonMethod.FROM_ERROR, err);

    }

    @Override
    public void showProgress() {
        progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void onNoInternetConnect(boolean b) {
        showSnackbar(b, CommonMethod.FROM_INTERNET, getString(R.string.no_internet_title));
    }


    private void showSnackbar(boolean isConnected, int from, String message) {

        int color = 0;
        if (from == CommonMethod.FROM_INTERNET) {
            if (!isConnected) {

                color = Color.WHITE;
                hideSnackbar();

            } else {
                color = Color.RED;
            }
        } else {
            color = Color.WHITE;
        }
        snackbar = Snackbar
                .make(fabSubmit, message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTextColor(color);
        textView.setMaxLines(2);
        snackbar.show();


        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSnackbarOkClicked(view);

            }
        });
        snackbar.show();
    }

    private void onSnackbarOkClicked(View view) {
        hideSnackbar();
    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (CommonMethod.OnBackPressListener) mContext;
        titleListener = (CommonMethod.onSetToolbarTitle) mContext;
    }

    @Override
    public void onClick(View view) {
        if (view == fabSubmit) {
            presenter.onFeedbackClicked(view);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        sb =  new StringBuilder();
        lblCount.setText(sb.append(charSequence.length()).append("/").append("260").toString());

    }

    @Override
    public void afterTextChanged(Editable editable) {


    }
}
