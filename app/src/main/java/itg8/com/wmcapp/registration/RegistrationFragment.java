package itg8.com.wmcapp.registration;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.MyApplication;
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
    @BindView(R.id.progressView)
    CircularProgressView progressView;
    @BindView(R.id.btn_signUp)
    Button btnSignUp;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Call<RegistrationModel> call;
    private boolean isDestroyed=false;


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
        btnSignUp.setOnClickListener(this);
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

    private void senDataToserver() {
        if (validate()) {


            postDataIntoServer(
                    inputName.getText().toString().trim(),
                    inputEmail.getText().toString().trim(),
                    inputMobile.getText().toString().trim(),
                    inputPassword.getText().toString().trim(),
                    inputCpassword.getText().toString().trim()
            );
        }

    }

    private void postDataIntoServer(String name, String email, String mobile, String password, String cpassword) {
        String url = getString(R.string.url_registration);
        showProgress();
        RetroController api = MyApplication.getInstance().getRetroController();
        call = api.sendRegistrationInfoToserver(url, email, password, cpassword,"AppUser");
         call.enqueue(new Callback<RegistrationModel>() {
             @Override
             public void onResponse(Call<RegistrationModel> call, Response<RegistrationModel> response) {

                 hideProgress();

                 if (response.isSuccessful()) {
                     if (response.body().isFlag()) {
                         showToast("Registration Successfully");

                     }else
                     {
                         showToast("Registration Failed");

                     }
                 } else {
                     showToast("Download Failed");
                 }
             }

             @Override
             public void onFailure(Call<RegistrationModel> call, Throwable t) {
                 t.printStackTrace();
                 showToast(t.getMessage());

             }
         });


    }


    private boolean validate() {
        String email = inputEmail.getText().toString();
        String name = inputName.getText().toString();
        String MobileNumber = inputMobile.getText().toString();
        String password = inputPassword.getText().toString();
        String cpassword = inputCpassword.getText().toString();
        boolean validate = true;
        if (TextUtils.isEmpty(name)) {
            inputName.setError(getString(R.string.empty));
            validate = false;
        }
        if (TextUtils.isEmpty(password)) {
            inputPassword.setError(getString(R.string.empty));
            validate = false;
        }if (TextUtils.isEmpty(cpassword)) {
            inputCpassword.setError(getString(R.string.empty));
            validate = false;
        }
         else if(!password.equalsIgnoreCase(cpassword)) {
            validate = false;
            inputPassword.setError(getString(R.string.pswd));
            inputCpassword.setError(getString(R.string.pswd));
        }

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.empty));
            validate = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validate = false;
            inputEmail.setError(getString(R.string.invalid_email));

        }
        if (MobileNumber.length() != 10) {
            validate = false;
            inputMobile.setError(getString(R.string.invalid_number));
        } else if (TextUtils.isEmpty(MobileNumber)) {
            inputMobile.setError(getString(R.string.empty));
            validate = false;
        }



        return validate;
    }




    private void showProgress() {
        if (!isDestroyed)
            progressView.setVisibility(View.VISIBLE);

    }



    private void hideProgress() {
        if (!isDestroyed)
            progressView.setVisibility(View.GONE);
    }

    private void showToast(String s) {
        if (!isDestroyed)
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }



}
