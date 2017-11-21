package itg8.com.wmcapp.profile;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.CityFragment;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.database.BaseDatabaseHelper;
import itg8.com.wmcapp.profile.mvp.ProfileMVp;
import itg8.com.wmcapp.profile.mvp.ProfilePresenterImp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements ProfileMVp.ProfileView, View.OnClickListener , CityFragment.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FROM_INTERNET = 2;
    private static final int FROM_ERROR = 1;

    @BindView(R.id.progressView)
    CircularProgressView progressView;
    Unbinder unbinder;

    @BindView(R.id.btn_ok)
    Button btnOk;
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
    @BindView(R.id.input_address)
    EditText inputAddress;
    @BindView(R.id.input_layout_address)
    TextInputLayout inputLayoutAddress;
    @BindView(R.id.input_city)
    EditText inputCity;
    @BindView(R.id.input_layout_city)
    TextInputLayout inputLayoutCity;
    @BindView(R.id.rl_registration)
    LinearLayout rlRegistration;
    @BindView(R.id.card)
    CardView card;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProfileMVp.ProfilePresenter presenter;
    private Snackbar snackbar;
    private Dao<CityModel, Integer> mDAOCity = null;
    private List<CityModel> cityList;
    private int cityId;


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
         presenter.onGetCityList(getString(R.string.url_city));

        btnOk.setOnClickListener(this);
        return view;
    }

    private void showSnackbar(boolean isConnected, int from,String message) {

        int color = 0;
         if(from == FROM_INTERNET) {
             if (!isConnected) {

                 color = Color.WHITE;
                 hideSnackbar();

             } else {
                 color = Color.RED;
             }
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
        inputEmail.setText(CommonMethod.checkEmpty(list.get(0).getEmailId()));
        inputMobile.setText(CommonMethod.checkEmpty(list.get(0).getContactNumber()));
        inputName.setText(CommonMethod.checkEmpty(list.get(0).getFullName()));
        inputAddress.setText(CommonMethod.checkEmpty(list.get(0).getAddressLine1()));
        inputCity.setOnClickListener(this);
    }

    @Override
    public void onSuccessCityList(List<CityModel> list) {
        try {
            saveBrandToDatabase(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        presenter.onGetProfileList(getString(R.string.url_profile));


    }

    @Override
    public void onFail(String message) {
        showSnackbar(false, FROM_ERROR,message);

    }

    @Override
    public void onError(Object t) {
        showSnackbar(false, FROM_ERROR, t.toString());

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
         showSnackbar(b, FROM_INTERNET,"No InternetConnection");

    }

    @Override
    public void onInternetConnect(boolean b) {
        // showSnackbar(b);

    }

    @Override
    public String getCity() {
        return String.valueOf(cityId);
    }

    @Override
    public String getEmail() {
        return inputAddress.getText().toString().trim();
    }

    @Override
    public String getAddress() {
        return inputAddress.getText().toString().trim();
    }

    @Override
    public void onAddressInvalid(String error) {
        setError(inputAddress, error);

    }

    @Override
    public void onEmailInvalid(String error) {
        setError(inputEmail, error);
    }

    @Override
    public void onCityInvalid(String error) {
        setError(inputCity, error);
    }

    @Override
    public void onSuccessSave(String status) {
        showSnackbar(false, FROM_ERROR,status);
    }

    @Override
    public String getName() {
        return inputName.getText().toString().trim();
    }

    @Override
    public String getMobile() {
        return inputMobile.getText().toString().trim();
    }

    private void setError(EditText view, String error) {
        view.setError(error);
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
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                presenter.onUpdateButtonClicked(view);
                break;
            case R.id.input_city:
                callCityFragment();
                break;
        }
    }

    private void callCityFragment() {
        CityFragment fragmentCity = CityFragment.newInstance(cityList,"");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container,fragmentCity);
        ft.addToBackStack(fragmentCity.getClass().getSimpleName());
        ft.commit();
    }

    private void saveBrandToDatabase(List<CityModel> list) throws SQLException {
        try {

            mDAOCity = BaseDatabaseHelper.getBaseInstance().getHelper(getActivity()).getmDAOCity();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (mDAOCity != null) {
            BaseDatabaseHelper.getBaseInstance().clearCityTable();


            for (CityModel model : list) {
                try {
                    int id = mDAOCity.create(model);

                    cityList = mDAOCity
                            .queryBuilder()
                            .where()
                            .eq(CityModel.FIELD_ID, model.getID())
                            .query();
                    Logs.d( "CityList:" + new Gson().toJson(cityList));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void onFragmentInteraction(CityModel cityModel) {
         cityId = cityModel.getID();
         inputCity.setText(cityModel.getName());

    }
}
