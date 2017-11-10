package itg8.com.wmcapp.profile;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.profile.mvp.ProfileMVp;
import itg8.com.wmcapp.profile.mvp.ProfilePresenterImp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements ProfileMVp.ProfileView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lbl_profile)
    TextView lblProfile;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.lbl_email)
    TextView lblEmail;
    @BindView(R.id.text_email)
    TextView textEmail;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.lbl_mobile)
    TextView lblMobile;
    @BindView(R.id.text_mobile)
    TextView textMobile;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.lbl_address)
    TextView lblAddress;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.progressView)
    CircularProgressView progressView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProfileMVp.ProfilePresenter presenter;
    private Snackbar snackbar;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        unbinder = ButterKnife.bind(this, view);
        presenter = new ProfilePresenterImp(this);
        presenter.onGetProfileList(getString(R.string.url_profile));
        return view;
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
        snackbar = Snackbar
                .make(btnOk, message, Snackbar.LENGTH_INDEFINITE);

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
        presenter.onGetProfileList(getString(R.string.url_profile));
    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    @Override
    public void onSuccess(List<ProfileModel> list) {
        textEmail.setText(CommonMethod.checkEmpty(list.get(0).getEmailId()));
        textMobile.setText(CommonMethod.checkEmpty(list.get(0).getContactNumber()));
        lblProfile.setText(CommonMethod.checkEmpty(list.get(0).getFullName()));

        String address = (CommonMethod.checkEmpty(list.get(0).getAddressLine1()))+
                (CommonMethod.checkEmpty( list.get(0).getAddressLine2()));

        if(TextUtils.isEmpty(address))
            textAddress.setText("NOT AVAILABLE");
        else
            textAddress.setText((CommonMethod.checkEmpty(list.get(0).getAddressLine1()))+
                    "\n"+(CommonMethod.checkEmpty( list.get(0).getAddressLine2()))

            );


    }

    @Override
    public void onFail(String message) {
        showToast(message);
    }

    @Override
    public void onError(Object t) {
        showToast(t.toString());
    }

    private void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
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
    public void onInternetConnect(boolean b) {
        showSnackbar(b);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
