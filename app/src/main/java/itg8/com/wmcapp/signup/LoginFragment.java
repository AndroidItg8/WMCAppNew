package itg8.com.wmcapp.signup;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.signup.mvp.LoginMvp;
import itg8.com.wmcapp.signup.mvp.LoginPresenterImp;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginMvp.LoginView, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lbl_login)
    CustomFontTextView lblLogin;
    @BindView(R.id.input_mobile)
    EditText inputMobile;
    @BindView(R.id.input_layout_mobile)
    TextInputLayout inputLayoutMobile;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.lbl_foget)
    CustomFontTextView lblFoget;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;

    @BindView(R.id.progressView)
    CircularProgressView progressView;


    Unbinder unbinder;
    @BindView(R.id.fab_login)
    FloatingActionButton fabLogin;
    @BindView(R.id.btn_facebook)
    Button btnFacebook;
    @BindView(R.id.btn_google)
    Button btnGoogle;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LoginMvp.LoginPresenter presenter;
    private Snackbar snackbar;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new LoginPresenterImp(this);
        fabLogin.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public String getUsername() {
        return inputMobile.getText().toString();
    }

    @Override
    public String getPassword() {
        return inputPassword.getText().toString();
    }

    @Override
    public void onSuccess() {
        Prefs.putBoolean(CommonMethod.IS_LOGIN, true);
//        Intent intent = new Intent(getContext(), HomeActivity.class);
//        startActivity(intent);
        getActivity().onBackPressed();

    }

    @Override
    public void onFail(String message) {
        showTextSnackbar(message);

    }


    @Override
    public void onError(Object t) {
        showTextSnackbar(t.toString());

    }

    @Override
    public void onUsernameInvalid(String err) {
        inputMobile.setError(err);


    }

    @Override
    public void onPasswordInvalid(String err) {
        inputPassword.setError(err);

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
        showSnackbar(b);

    }



    private void showSnackbar(boolean isConnected) {

        int color;
        String message;
        if (!isConnected) {

            message = "Connected to Internet";
            color = Color.WHITE;
            hideSnackbar();

        } else {
            message = " Not connected to internet...Please try again";
            color = Color.RED;
        }
        snackbar = Snackbar.make(getActivity().findViewById(R.id.fab), message, Snackbar.LENGTH_INDEFINITE);

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
        presenter.onLoginClicked(view);
    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private void showTextSnackbar(String s) {
        snackbar = Snackbar
                .make(getActivity().findViewById(R.id.fab), s, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setText(s);

        textView.setMaxLines(2);

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();

            }
        });
        snackbar.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab_login:
                 presenter.onLoginClicked(view);
                break;
        }
    }
}
