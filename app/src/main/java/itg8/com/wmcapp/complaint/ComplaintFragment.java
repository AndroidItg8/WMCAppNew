package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.mvp.ComplaintMVP;
import itg8.com.wmcapp.complaint.mvp.ComplaintPresenterImp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment implements ComplaintMVP.ComplaintView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private ComplaintAdapter adapter;

    private ComplaintMVP.ComplaintPresenter presenter;
    private LinearLayoutManager layoutManager;

    public ComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintFragment newInstance(String param1, String param2) {
        ComplaintFragment fragment = new ComplaintFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter=new ComplaintPresenterImp(this);
        initPagination();
        init();
        presenter.onLoadMoreItem(getString(R.string.url_complaint));
        return view;
    }

    private void initPagination() {

    }

    private void init() {
        layoutManager=new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ComplaintAdapter(mContext);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(presenter.scrollListener(layoutManager));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mContext!= null)
            mContext= null;
        presenter.onDetach();
    }

    @Override
    public void onComplaintListAvailable(List<ComplaintModel> o) {
        adapter.addItems(o);
    }

    @Override
    public void onNoMoreList() {

    }

    @Override
    public void onShowPaginationLoading(boolean show) {
        if(show){
            adapter.addFooter();
//            recyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.notifyItemInserted();
//                }
//            });
        }else {
            adapter.removeFooter();
        }
    }

    @Override
    public void onPaginationError(boolean show) {

    }
}
