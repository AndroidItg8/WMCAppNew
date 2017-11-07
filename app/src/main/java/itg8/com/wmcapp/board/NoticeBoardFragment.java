package itg8.com.wmcapp.board;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.mvp.NBMVP;
import itg8.com.wmcapp.board.mvp.NBPresenterImp;
import itg8.com.wmcapp.complaint.AddComplaintFragment;
import ru.alexbykov.nopaginate.paginate.Paginate;
import ru.alexbykov.nopaginate.paginate.PaginateBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoticeBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeBoardFragment extends Fragment implements View.OnClickListener, itg8.com.wmcapp.board.mvp.NBMVP.NBView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.rl_include)
    RelativeLayout rlInclude;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NBMVP.NBPresenter presenter;
    private Fragment fragment= null;
    private Context mContext;
    private Paginate paginate;
    private NoticeBoardAdater adapter;


    public NoticeBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticeBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticeBoardFragment newInstance(String param1, String param2) {
        NoticeBoardFragment fragment = new NoticeBoardFragment();
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
        View view = inflater.inflate(R.layout.fragment_notice_board, container, false);
        unbinder = ButterKnife.bind(this, view);
rlInclude.setOnClickListener(this);
        initPaginate();
        init();
        return view;
    }

    private void initPaginate() {
        paginate = new PaginateBuilder()
                .with(recyclerView)
                .setCallback(presenter)
                .setLoadingTriggerThreshold(10)
                .build();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new NoticeBoardAdater(getActivity());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        presenter=new NBPresenterImp(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_include:
                fragment = AddComplaintFragment.newInstance("","");

                break;

        }
        callFragment(fragment);
    }

    private void callFragment(Fragment fragment) {

    }


    private void showNoListAvail() {

    }


    private void showSnackbar(String s) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mContext!= null)
            mContext= null;
        paginate.unSubscribe();
        presenter.onDetach();
    }

    @Override
    public void onNBListAvailable() {
        adapter.addItems();
    }

    @Override
    public void onNoMoreList() {
        paginate.setPaginateNoMoreItems(true);
    }

    @Override
    public void onShowPaginationLoading(boolean show) {
        paginate.showLoading(show);
    }

    @Override
    public void onPaginationError(boolean show) {
        paginate.showError(show);
    }
}
