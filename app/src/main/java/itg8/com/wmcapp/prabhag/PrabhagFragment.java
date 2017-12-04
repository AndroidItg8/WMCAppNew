package itg8.com.wmcapp.prabhag;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
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
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;
import itg8.com.wmcapp.prabhag.model.WardList;
import itg8.com.wmcapp.prabhag.mvp.PrabhagMVP;
import itg8.com.wmcapp.prabhag.mvp.PrabhagPresenterImp;


/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PrabhagFragment extends Fragment implements PrabhagMVP.PrabhagView, PrabhagItemRecyclerViewAdapter.ItemClickedListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int FROM_ERROR = 2;
    private static final int FROM_INTERNET = 1;
    private static final String ARG_COLUMN_LIST = "COLUMN_LIST";
    @BindView(R.id.list)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.progressView)
    CircularProgressView progressView;
    Context listener;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private PrabhagMVP.PrabhagPresenter presenter;
    private Snackbar snackbar;
    private android.content.Context context;
    private PrabhagItemRecyclerViewAdapter adapter;
    private int isFrom;
    private List<WardList> wradList;
    private List<PrabhagModel> list;
    CommonMethod.onSetToolbarTitle titleListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PrabhagFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PrabhagFragment newInstance(int columnCount) {
        PrabhagFragment fragment = new PrabhagFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    private static PrabhagFragment newInstance(List<WardList> wardList) {
        PrabhagFragment fragment = new PrabhagFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_COLUMN_LIST, (ArrayList<? extends Parcelable>) wardList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        if (getArguments() != null) {
            wradList = getArguments().getParcelableArrayList(ARG_COLUMN_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prabhagitem_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new PrabhagPresenterImp(this);
        if (wradList == null)
            presenter.onGetPrabhagList(getString(R.string.url_prabhag));
        else {
            isFrom = CommonMethod.WARD;
            setRecyclerView(list, wradList);
        }
     //   titleListener.onSetTitle(getString(R.string.prabhag));

            return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    //    titleListener= (CommonMethod.onSetToolbarTitle) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        titleListener=null;
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
        showSnackerbar(FROM_INTERNET, "No Internet Connect", b);
    }

    @Override
    public void onError(String message) {
        if(message.equalsIgnoreCase("401"))
            ((HomeActivity)getActivity()).clearNLogout();
        else
            showSnackerbar(FROM_ERROR, message, false);

    }


    @Override
    public void onPrabhagListAvailable(List<PrabhagModel> o) {
         this.list = o;

        setRecyclerView(o, wradList);

    }

    private void setRecyclerView(List<PrabhagModel> o, List<WardList> wradList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        adapter = new PrabhagItemRecyclerViewAdapter(context, o, wradList, this, isFrom);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        titleListener =null;
        unbinder.unbind();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


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
    public void onItemPrabhagClicked(int position, PrabhagModel model) {
        PrabhagFragment fragment = PrabhagFragment.newInstance(model.getWardList());
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container, fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();


    }


    @Override
    public void onItemWardClicked(int position, WardList model) {
        WardMemberFragment fragment = WardMemberFragment.newInstance(model.getMemberList());
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container, fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public interface onPrabhagClickedListener {
        void onPrabhagSelected();
    }
}
