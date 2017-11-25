package itg8.com.wmcapp.signup;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.forget.ForgetFragment;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.profile.ProfileActivity;
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
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final int FB_LOGIN = 1;
    private static final int GPlus_LOGIN = 2;


    Unbinder unbinder;


    OnAttachActivityListener listener;
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
    @BindView(R.id.lbl_forget)
    CustomFontTextView lblForget;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.card)
    CardView card;
    @BindView(R.id.fab_login)
    FloatingActionButton fabLogin;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.btn_facebook)
    Button btnFacebook;
    @BindView(R.id.btn_google)
    SignInButton btnGoogle;
    @BindView(R.id.btn_g)
    Button btnG;
    @BindView(R.id.ll_snack)
    LinearLayout llSnack;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LoginMvp.LoginPresenter presenter;
    private Snackbar snackbar;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Context mContext;
    private String from;
    private FragmentTransaction ft;
    private boolean isMobile=false;


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
        inputMobile.setText("9823456575");
        inputPassword.setText("123456");
        presenter = new LoginPresenterImp(this);
        fabLogin.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        lblForget.setOnClickListener(this);
        btnG.setOnClickListener(this);
        loginGoogleInit();
        if(TextUtils.isDigitsOnly(inputMobile.getText()))
        {
            isMobile= true;
        }

        return view;
    }

    private void loginGoogleInit() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            updateUI(currentUser);
        else
            removeFromGoogle();
    }

    private void removeFromGoogle() {
// Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Logs.d("Successfully logout.");
                    }
                });
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
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
//         getActivity().finish();

        listener.onAttachActivity();


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
        inputLayoutMobile.setError(err);

    }

    @Override
    public void onPasswordInvalid(String err) {
        inputLayoutPassword.setError(err);

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

    @Override
    public void onFirstTimeLogin(String success) {
        Prefs.putString(CommonMethod.IS_LOGIN_FIRST_TIME, success);
//        RegistrationFragment fragment = RegistrationFragment.newInstance(CommonMethod.IS_LOGIN_FIRST_TIME, success);
//        ft = getFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//        ft.replace(R.id.frame_container, fragment);
//        ft.addToBackStack(fragment.getClass().getSimpleName());
//        ft.commit();

        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra(CommonMethod.FROM_FIRST_TIME_LOGIN, true);
        startActivity(intent);
        listener.onAttachActivity();
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
        snackbar = Snackbar.make(llSnack, message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(color);
//        textView.setMaxLines(2);


        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSnackbarOkClicked(view);

            }
        });
        snackbar.show();
    }

    private void onSnackbarOkClicked(View view) {
        presenter.onLoginClicked(view, isMobile);
    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private void showTextSnackbar(String s) {
        snackbar = Snackbar
                .make(llSnack, s, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
//        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(Color.WHITE);
//        textView.setText(s);
//
//        textView.setMaxLines(2);

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
        switch (view.getId()) {
            case R.id.fab_login:
                presenter.onLoginClicked(view, isMobile);
                break;
            case R.id.btn_google:
                break;
            case R.id.btn_g:
                signInGoogle();
                break;
            case R.id.lbl_forget:
                ForgetFragment fragment = ForgetFragment.newInstance("","");
                ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                ft.replace(R.id.frame_container,fragment);
                ft.addToBackStack(fragment.getClass().getSimpleName());
                ft.commit();
                break;
        }
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i(TAG, "signInWithCredential: success " + new Gson().toJson(user));
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (mContext != null)
                                showTextSnackbar("Failed To Sync  Google Account");

                        }


                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        UserModel userModel = new UserModel();
        userModel.setName(user.getDisplayName());
        userModel.setEmail(user.getEmail());
        userModel.setMobile(user.getPhoneNumber());


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnAttachActivityListener)
            listener = (OnAttachActivityListener) context;
        else
            throw new IllegalStateException("Activity should implement OnAttachActivityListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null) {
            mContext = null;

        }
        presenter.onDestroy();
    }


    public interface OnAttachActivityListener {
        void onAttachActivity();
    }
}
