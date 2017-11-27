package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;

import itg8.com.wmcapp.common.ReceiveBroadcastReceiver;
import itg8.com.wmcapp.common.SentBroadCastReceiver;

import itg8.com.wmcapp.complaint.mvp.ComplaintMVP;
import itg8.com.wmcapp.database.ComplaintTableManipute;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnSendComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnSendComplaintFragment extends Fragment  {
    //implements  ComplaintProfileAdapter.UnSendItemClickedListner
    //ComplaintMVP.ComplaintView,
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private ComplaintTableManipute complaintTableManipute;


    public UnSendComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnSendComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UnSendComplaintFragment newInstance(String param1, String param2) {
        UnSendComplaintFragment fragment = new UnSendComplaintFragment();
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
        View view = inflater.inflate(R.layout.fragment_un_send_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
        complaintTableManipute = new ComplaintTableManipute(mContext);
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OnlineComplaint(), "  Online");
        adapter.addFragment(new OfflineComplaint(), "  Offline");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    @Override
//    public void onComplaintListAvailable(List<ComplaintModel> o) {
//        setListAndRecyclerView(o);
//    }
//
//    private void setListAndRecyclerView(List<ComplaintModel> o) {
//        List<TempComplaintModel> listTempComplaintModel = complaintTableManipute.getAllComplaint();
//        sortTempComplaintList(listTempComplaintModel);
//        sortComplaintList(o);
//        List<Object> complaintMergeList = mergeComplaintList(o, listTempComplaintModel);
//        sortObjectList(complaintMergeList);
//        setRecyclerView(complaintMergeList);
//    }
//
//    private void sortObjectList(List<Object> complaintMergeList) {
//        Collections.sort(complaintMergeList, new Comparator<Object>() {
//            @Override
//            public int compare(Object o, Object t1) {
//                return o.equals(t1);
//            }
//
//            public int compare(ComplaintModel m1, ComplaintModel m2) {
//                return m1.getLastModifiedDate().compareToIgnoreCase(m2.getLastModifiedDate());
//            }
//        });
//    }
//
//    private void sortComplaintList(List<ComplaintModel> o) {
//        Collections.sort(o, new Comparator<ComplaintModel>() {
//            public int compare(ComplaintModel m1, ComplaintModel m2) {
//                return m1.getLastModifiedDate().compareToIgnoreCase(m2.getLastModifiedDate());
//            }
//        });
//    }
//
//    private void setRecyclerView(List<Object> complaintMergeList) {
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setAdapter(new ComplaintProfileAdapter(mContext, complaintMergeList, this));
//    }
//
//    private void sortTempComplaintList(List<TempComplaintModel> complaintMergeList) {
//        Collections.sort(complaintMergeList, new Comparator<TempComplaintModel>() {
//            public int compare(TempComplaintModel m1, TempComplaintModel m2) {
//                return m1.getLastModifiedDate().compareToIgnoreCase(m2.getLastModifiedDate());
//            }
//        });
//
//
//    }
//
//    private List<Object> mergeComplaintList(List<ComplaintModel> listComplaint, List<TempComplaintModel> listTempComplaintModel) {
//        List<Object> mergeComplaintList = new ArrayList<>();
//        mergeComplaintList.addAll(listComplaint);
//        mergeComplaintList.addAll(listTempComplaintModel);
//        Logs.d("MergeList:" + new Gson().toJson(mergeComplaintList));
//        return mergeComplaintList;
//    }
//
//    @Override
//    public void onNoMoreList() {
//
//    }
//
//    @Override
//    public void onShowPaginationLoading(boolean show) {
//
//    }
//
//    @Override
//    public void onPaginationError(boolean show) {
//
//    }
//
//    @Override
//    public void hideProgress() {
//
//    }
//
//    @Override
//    public void onSuccessLike(ComplaintModel model, int position) {
//
//    }
//
//    @Override
//    public void onFailedLike(String s) {
//        List<TempComplaintModel> listTempComplaintModel = complaintTableManipute.getAllComplaint();
//        sortTempComplaintList(listTempComplaintModel);
//        List<Object> tempList = new ArrayList<>();
//        tempList.addAll(listTempComplaintModel);
//        setRecyclerView(tempList);
//
//
//    }
//
//    @Override
//    public void showProgress(int position) {
//
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // presenter.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null) {
         //   presenter.onDetach();
            mContext = null;
        }
    }



}
