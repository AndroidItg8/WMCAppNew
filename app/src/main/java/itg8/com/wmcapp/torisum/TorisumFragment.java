package itg8.com.wmcapp.torisum;


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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.torisum.model.TorisumModel;
import itg8.com.wmcapp.torisum.mvp.TorisumPresenterImp;
import itg8.com.wmcapp.torisum.mvp.TourismMVP;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TorisumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TorisumFragment extends Fragment implements OnRecyclerviewClickListener<TorisumModel>, TourismMVP.TourismView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int FROM_ERROR = 2;
    private static final int FROM_INTERNET = 1;

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressView)
    CircularProgressView progressView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TourismMVP.TourismPresenter presenter;
    private int page;
    private int from;
    private Snackbar snackbar;


    public TorisumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TorisumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String param1, String param2) {
        TorisumFragment fragment = new TorisumFragment();
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
        View view = inflater.inflate(R.layout.fragment_torisum, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new TorisumPresenterImp(this);
        presenter.onGetTorismList(getString(R.string.url_toriusm));

        return view;
    }

    private void init(List<TorisumModel> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TorisumAdapter(getActivity(), list, this));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @Override
    public void onTourismListAvailable(List<TorisumModel> list) {
        init(list);
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
    public void onNoInternetConnection(boolean b) {
        from = FROM_INTERNET;
        showSnackerbar(from, "Not connected to internet...Please try again",b);

    }

    @Override
    public void onError(String message) {
         from = FROM_ERROR;
        showSnackerbar(from, message,false);

    }

    private void showSnackerbar(int from, String message, boolean isConnected) {


        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        int color = 0;
        if(from == FROM_INTERNET) {

            if (!isConnected) {
                color = Color.WHITE;
                hideSnackbar();

            } else {
                message = " Not connected to internet...Please try again";
                color = Color.RED;

            }
            textView.setTextColor(color);
            textView.setMaxLines(2);

        }else
        {
            textView.setTextColor(color);
            textView.setMaxLines(2);
        }
        snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE);
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
    public void onClick(int position, TorisumModel model) {
        Fragment fragment = TorisumDetailsFragment.newInstance(model, "");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(TorisumDetailsFragment.class.getSimpleName()).commit();


    }


}
