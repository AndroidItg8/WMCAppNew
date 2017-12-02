package itg8.com.wmcapp.emergency;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.emergency.model.Contact;
import itg8.com.wmcapp.emergency.model.EmergencyModel;
import itg8.com.wmcapp.emergency.mvp.EmergencyMVP;
import itg8.com.wmcapp.emergency.mvp.EmergencyPresenterImp;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.torisum.TorisumDetailsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmergencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyFragment extends Fragment implements EmergencyAdapter.ItemClickedListner, EmergencyMVP.EmergencyView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String AMBULANCE = "tel:" + "102";
    private static final String HOSPITAL = "tel:" + "9823778532";
    private static final String FIRE = "tel:" + "101";
    private static final String POLICE = "tel:" + "100";
    private static final int RC_CALL = 123;
    private static final int FROM_INTERNET = 1;
    private static final int FROM_ERROR = 2;
     CommonMethod.onSetToolbarTitle listener;

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressView)
    CircularProgressView progressView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private Snackbar snackbar;
    private EmergencyMVP.EmergencyPresenter presenter;


    public EmergencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergencyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyFragment newInstance(String param1, String param2) {
        EmergencyFragment fragment = new EmergencyFragment();
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
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new EmergencyPresenterImp(this);
        presenter.onGetList(getString(R.string.url_emergency));
        listener.onSetTitle(getString(R.string.emegency_numbers));
        return view;
    }

    private void initView(List<EmergencyModel> lists) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new EmergencyAdapter(mContext, lists, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener= (CommonMethod.onSetToolbarTitle) mContext;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null) {
            mContext = null;
            listener =null;
        }

    }
    @Override
    public void onSuccess(List<EmergencyModel> list) {
        initView(list);

    }

    @Override
    public void onFail(String message) {
        if(message.equalsIgnoreCase("401"))
            ((HomeActivity) getActivity()).clearNLogout();
        else
            showSnackerbar(FROM_ERROR,message, false);



    }

    @Override
    public void onError(Object t) {
        showSnackerbar(FROM_ERROR,t.toString(), false);

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
        showSnackerbar(FROM_INTERNET,"No Internet Connection", b);

    }

    private void showSnackerbar(int from, String message, boolean isConnected) {

        snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        int color = 0;
        if (from == FROM_INTERNET) {

            if (!isConnected) {
                color = Color.WHITE;
                hideSnackbar();

            } else {
                message = " Not connected to internet...Please try again";
                color = Color.RED;

            }
            textView.setTextColor(color);
            textView.setMaxLines(2);

        } else {
            textView.setTextColor(color);
            textView.setMaxLines(2);
        }
        snackbar.show();
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSnackbar();

            }
        });
        snackbar.show();
    }

    private void hideSnackbar() {
        snackbar.dismiss();
    }



    @Override
    public void onItemClicked(int position, EmergencyModel model) {
        Fragment fragment = EmergencyDetailsFragment.newInstance(model, "");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(TorisumDetailsFragment.class.getSimpleName()).commit();

    }
}
