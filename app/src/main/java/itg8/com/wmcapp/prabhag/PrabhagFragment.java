package itg8.com.wmcapp.prabhag;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.prabhag.model.PrabhagModel;
import itg8.com.wmcapp.prabhag.mvp.PrabhagMVP;
import itg8.com.wmcapp.prabhag.mvp.PrabhagPresenterImp;


/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class PrabhagFragment extends Fragment implements PrabhagMVP.PrabhagView, OnRecyclerviewClickListener<PrabhagModel> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int FROM_ERROR = 2;
    private static final int FROM_INTERNET = 1;
    @BindView(R.id.list)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.progressView)
    CircularProgressView progressView;
    onPrabhagClickedListener listener;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private PrabhagMVP.PrabhagPresenter presenter;
    private Snackbar snackbar;
    private android.content.Context context;
    private PrabhagItemRecyclerViewAdapter adapter;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prabhagitem_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new PrabhagPresenterImp(this);
        presenter.onGetPrabhagList(getString(R.string.url_prabhag));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        listener = (onPrabhagClickedListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
        showSnackerbar(FROM_ERROR, message, false);

    }


    @Override
    public void onPrabhagListAvailable(List<PrabhagModel> o) {

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        adapter = new PrabhagItemRecyclerViewAdapter(context, o, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
    public void onClick(int position, PrabhagModel prabhagModel) {
        if (prabhagModel.isPragbhagSelected()) {
            adapter.notifyDataSetChanged();
        } else {
            WardMemberFragment fragment=WardMemberFragment.newInstance(prabhagModel.getWardList().get(position).getMemberList());
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            ft.replace(R.id.frame_container,fragment);
            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        }
    }

    public interface onPrabhagClickedListener {
        void onPrabhagSelected();
    }
}
