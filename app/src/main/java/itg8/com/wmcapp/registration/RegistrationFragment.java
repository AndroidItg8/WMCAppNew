package itg8.com.wmcapp.registration;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.common.RetroController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FROM_ERROR = 2;
    private static final int FROM_INERNET = 1;

    Unbinder unbinder;
    @BindView(R.id.input_name)
    EditText inputName;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.input_mobile)
    EditText inputMobile;
    @BindView(R.id.input_layout_mobile)
    TextInputLayout inputLayoutMobile;
    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;


    @BindView(R.id.input_city)
    EditText inputCity;
    @BindView(R.id.input_layout_city)
    TextInputLayout inputLayoutCity;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.input_cpassword)
    EditText inputCpassword;
    @BindView(R.id.input_layout_cpassword)
    TextInputLayout inputLayoutCpassword;
    @BindView(R.id.rl_registration)
    LinearLayout rlRegistration;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.fab_signUp)
    FloatingActionButton fabSignUp;
    @BindView(R.id.progressView)
    ProgressBar progressView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Call<RegistrationModel> call;
    private boolean isDestroyed = false;
    private Snackbar snackbar;
    private Context mContext;
    private OnAttachRegistrationListener listener;


    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }


    private void init() {
        fabSignUp.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        senDataToserver();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (OnAttachRegistrationListener) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void senDataToserver() {
        if (validate()) {
            postDataIntoServer(
                    inputName.getText().toString().trim(),
                    inputEmail.getText().toString().trim(),
                    inputMobile.getText().toString().trim(),
                    inputPassword.getText().toString().trim()
            );
        }

    }

    private void postDataIntoServer(String name, String email, String mobile, String password) {
        String url = getString(R.string.url_registration);
        showProgress();
        RetroController api = MyApplication.getInstance().getRetroController();
//    Email:jay@gmail.com
//    Password:123456
//    ConfirmPassword:123456
//    UserRoles:AppUser
//    MobileNumber:9823778532
//    FullName:ayesha

        call = api.registartion(url, email, password, password, "AppUser", mobile, name);
        call.enqueue(new Callback<RegistrationModel>() {
            @Override
            public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {

                hideProgress();
                if (response.isSuccessful()) {
                    if (response.body().isFlag()) {
                        showSnackbar(false, FROM_ERROR, response.body().getStatus());
                        inputName.setText("");
                        inputMobile.setText("");
                        inputEmail.setText("");
                        inputPassword.setText("");

                    } else {
                        showSnackbar(false, FROM_ERROR, response.body().getStatus());
                    }
                } else {
                    showSnackbar(false, FROM_ERROR, response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<RegistrationModel> call, Throwable t) {
                t.printStackTrace();

                if (t instanceof NoConnectivityException)
                    showSnackbar(true, FROM_INERNET, t.getMessage());

                else
                    showSnackbar(false, FROM_ERROR, t.getMessage());


            }
        });


    }

    private void showSnackbar(boolean isConnected, int from, String message) {

        int color = 0;
        if (from == FROM_INERNET) {
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
                .make(fabSignUp, message, Snackbar.LENGTH_INDEFINITE);

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
        listener.onAttachFragmnet();
//        getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private boolean validate() {
        String email = inputEmail.getText().toString();
        String name = inputName.getText().toString();
        String MobileNumber = inputMobile.getText().toString();
        String password = inputPassword.getText().toString();

        boolean validate = true;
        if (TextUtils.isEmpty(name)) {
            inputLayoutName.setError(getString(R.string.empty));
            validate = false;
        }
        if (TextUtils.isEmpty(password)) {
            inputLayoutPassword.setError(getString(R.string.empty));
            validate = false;
        }
        if (TextUtils.isEmpty(MobileNumber)) {
            inputLayoutMobile.setError(getString(R.string.empty));
            validate = false;

        } else {
            if (MobileNumber.length() != 10) {
                inputLayoutMobile.setError(getString(R.string.invalid_number));
                validate = false;
            }
        }

        if (password.length() < 6) {
            validate = false;
            inputLayoutPassword.setError(getString(R.string.invalid_pass));
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputLayoutEmail.setError(getString(R.string.invalid_email));
            validate = false;
        }

        return validate;
    }


    private void showProgress() {
        progressView.setVisibility(View.VISIBLE);

    }


    private void hideProgress() {
        progressView.setVisibility(View.GONE);
    }

    public interface OnAttachRegistrationListener {
        void onAttachFragmnet();
    }

}
