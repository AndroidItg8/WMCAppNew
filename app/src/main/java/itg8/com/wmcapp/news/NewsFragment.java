package itg8.com.wmcapp.news;


import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.news.model.NewsModel;
import itg8.com.wmcapp.news.mvp.NewsMVP;
import itg8.com.wmcapp.news.mvp.NewsPresenterImp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements NewsAdapter.NewsItemClickedListner, NewsMVP.NewsView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final long FADE_DEFAULT_TIME = 400;
    private static final long MOVE_DEFAULT_TIME = 100;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NewsDetailsFragment fragment;
    private NewsMVP.NewsPresenter presenter;
    CommonMethod.onSetToolbarTitle listener;
    private Context mContext;
    private Snackbar snackbar;


    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new NewsPresenterImp(this);
        setHasOptionsMenu(true);
        presenter.onGetNewsList(getString(R.string.url_news));
         listener.onSetTitle(getString(R.string.new_updates));
        return view;
    }

    private void init(List<NewsModel> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NewsAdapter(getActivity(), list, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();

        unbinder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemNewsClicked(int position, NewsModel model) {
        fragment = NewsDetailsFragment.newInstance(model, "");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onSuccess(List<NewsModel> list) {
        init(list);

    }

    @Override
    public void onFail(String message) {
        if(message.equalsIgnoreCase("401"))
            ((HomeActivity)getActivity()).clearNLogout();
        else
            showSnackerbar(CommonMethod.FROM_INTERNET,message,false);

    }

    @Override
    public void onError(Object t) {
        showSnackerbar(CommonMethod.FROM_ERROR,t.toString(),false);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
         progressBar.setVisibility(View.GONE);


    }

    @Override
    public void onNoInternetConnect(boolean b) {
        showSnackerbar(CommonMethod.FROM_INTERNET,"No Internet Connection yet..!!",b);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
         listener= (CommonMethod.onSetToolbarTitle) mContext;
    }

    private void showSnackerbar(int from, String message, boolean isConnected) {

        snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        int color = 0;
        if (from == CommonMethod.FROM_INTERNET) {

            if (!isConnected) {
                color = Color.WHITE;
                hideSnackbar();

            } else {
                color = Color.RED;

            }
            textView.setTextColor(color);
            textView.setMaxLines(2);

        } else {
            textView.setTextColor(color);
            textView.setMaxLines(2);
        }

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
    public void onDetach() {
        super.onDetach();
        if(mContext!= null) {
            mContext = null;
            listener= null;


        }
    }
}
