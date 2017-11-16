package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.database.ComplaintTableManipute;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfflineComplaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfflineComplaint extends Fragment implements ComplaintProfilOfflineeAdapter.UnSendItemClickedListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.lbl_no_item)
    CustomFontTextView lblNoItem;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;


    public OfflineComplaint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OfflineComplaint.
     */
    // TODO: Rename and change types and number of parameters
    public static OfflineComplaint newInstance(String param1, String param2) {
        OfflineComplaint fragment = new OfflineComplaint();
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
        View view = inflater.inflate(R.layout.fragment_offline_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
         init();
        return view;
    }

    private void init() {
        ComplaintTableManipute       complaintTableManipute = new ComplaintTableManipute(mContext);
        List<TempComplaintModel> listTempComplaintModel = complaintTableManipute.getAllComplaint();
        setRecyclerView(listTempComplaintModel);

    }
    private void setRecyclerView(List<TempComplaintModel> complaintMergeList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new ComplaintProfilOfflineeAdapter(mContext, complaintMergeList, this));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSyncItemClicked(int position, TempComplaintModel model) {

    }

    @Override
    public void onShareItemClicked(int position, Object model, ImageView view) {

    }

    @Override
    public void onSMSItemClicked(int position, TempComplaintModel model) {

    }
}
