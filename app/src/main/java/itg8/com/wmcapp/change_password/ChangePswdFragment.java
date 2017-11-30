package itg8.com.wmcapp.change_password;


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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.change_password.mvp.ChangePasswordMVP;
import itg8.com.wmcapp.change_password.mvp.ChangePswdPresenterImp;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.home.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePswdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePswdFragment extends Fragment implements ChangePasswordMVP.ChangePswdView, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.input_oldPswd)
    EditText inputOldPswd;
    @BindView(R.id.input_layout_old_pswd)
    TextInputLayout inputLayoutOldPswd;
    @BindView(R.id.input_newPswd)
    EditText inputNewPswd;
    @BindView(R.id.input_layout_new_pswd)
    TextInputLayout inputLayoutNewPswd;
    @BindView(R.id.input_cirmPswd)
    EditText inputCirmPswd;
    @BindView(R.id.input_layout_cirm_pswd)
    TextInputLayout inputLayoutCirmPswd;
    @BindView(R.id.fab_go)
    FloatingActionButton fabGo;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Snackbar snackbar;
    private ChangePasswordMVP.ChangePswdPresenter presenter;


    public ChangePswdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePswdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePswdFragment newInstance(String param1, String param2) {
        ChangePswdFragment fragment = new ChangePswdFragment();
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
        View view = inflater.inflate(R.layout.fragment_change_pswd, container, false);
        unbinder = ButterKnife.bind(this, view);
        fabGo.setOnClickListener(this);
        presenter = new ChangePswdPresenterImp(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        unbinder.unbind();
    }

    @Override
    public String getOldPassword() {
        return inputOldPswd.getText().toString().trim();
    }

    @Override
    public String getNewPassword() {
        return inputNewPswd.getText().toString().trim();

    }

    @Override
    public String getConfirmPassword() {
        return inputCirmPswd.getText().toString().trim();

    }
    @Override
    public void onSuccess(String status) {
        Prefs.putBoolean(CommonMethod.IS_LOGIN, true);
        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
        getActivity().finish();
        startActivity(new Intent(getContext(), HomeActivity.class));
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
    public void onOldPswdInvalid(String err) {
        inputOldPswd.setError(err);


    }

    @Override
    public void onNewPswdInvalid(String err) {
        inputNewPswd.setError(err);


    }

    @Override
    public void onConfirmswdInvalid(String err) {
        inputCirmPswd.setError(err);

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

    public  void showSnackbar(boolean isConnected) {

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
        presenter.onSubmitButtonClicked(view, getString(R.string.url_change_password));
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
    public void onClick(View v) {
        if(v.getId()==R.id.fab_go)
        {
            presenter. onSubmitButtonClicked(v, getString(R.string.url_change_password));

        }
    }
}
