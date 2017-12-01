package itg8.com.wmcapp.forget;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.forget.mvp.ForgetMVP;
import itg8.com.wmcapp.forget.mvp.ForgetPresenterImp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgetFragment extends Fragment implements ForgetMVP.ForgetView, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.fab_go)
    FloatingActionButton fabGo;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    Unbinder unbinder;
    CommonMethod.onSetToolbarTitle listener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Snackbar snackbar;
    private ForgetMVP.ForgetPresenter presenter;
    private boolean isDigit = false;
    private Context mContext;



    public ForgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgetFragment newInstance(String param1, String param2) {
        ForgetFragment fragment = new ForgetFragment();
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
        View view = inflater.inflate(R.layout.fragment_forget, container, false);
        unbinder = ButterKnife.bind(this, view);
        fabGo.setOnClickListener(this);
        listener.onSetTitle(getString(R.string.forget_pswd));
//        listener.setDrawer();
        presenter = new ForgetPresenterImp(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        listener=null;
        unbinder.unbind();
    }

    @Override
    public String getEmailId() {
        return inputEmail.getText().toString().trim();
    }

    @Override
    public void onSuccess(String message) {
        showSnackbar(false, CommonMethod.FROM_ERROR, message);

    }

    @Override
    public void onFail(String message)
    {
        showSnackbar(false, CommonMethod.FROM_ERROR, message);
    }

    @Override
    public void onError(Object t) {
        showSnackbar(false, CommonMethod.FROM_ERROR, t.toString());


    }

    private void showToast(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        showSnackbar(false, CommonMethod.FROM_ERROR, s);


    }

    @Override
    public void onEmailInvalid(String err) {
        inputEmail.setError(err);

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
        showSnackbar(b, CommonMethod.FROM_INTERNET, "No Internet Connection");

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_go) {
            presenter.onSubmitButtonClicked(v, isDigit);
        }
    }

    private void showSnackbar(boolean isConnected, int from, String s) {

        int color = 0;
        if (from == CommonMethod.FROM_INTERNET) {
            if (isConnected) {
                color = Color.RED;
                hideSnackbar();
            }
        } else {
            color = Color.WHITE;
        }
        snackbar = Snackbar
                .make(getActivity().findViewById(R.id.fab), s, Snackbar.LENGTH_INDEFINITE);

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
        listener = (CommonMethod.onSetToolbarTitle) mContext;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null) {
            mContext = null;
        }
    }
}
