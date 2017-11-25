package itg8.com.wmcapp.complaint;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.ReceiveBroadcastReceiver;
import itg8.com.wmcapp.common.SentBroadCastReceiver;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.complaint.mvp.ComplaintMVP;
import itg8.com.wmcapp.complaint.mvp.ComplaintPresenterImp;
import itg8.com.wmcapp.database.CityTableManipulate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnlineComplaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineComplaint extends Fragment implements ComplaintMVP.ComplaintView, ComplaintAdapter.ComplaintListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.rl_no_item)
    RelativeLayout rlNoItem;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SentBroadCastReceiver receiver;
    private Context mContext;
    private BroadcastReceiver receiveBroadcast;
    private ComplaintMVP.ComplaintPresenter presenter;
    private List<ComplaintModel> listOfComplaint;
    private LinearLayoutManager layoutManager;
    private ComplaintAdapter adapter;
    private CityTableManipulate cityManipulate;
    private CityModel city;


    public OnlineComplaint() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OnlineComplaint.
     */
    // TODO: Rename and change types and number of parameters
    public static OnlineComplaint newInstance(String param1, String param2) {
        OnlineComplaint fragment = new OnlineComplaint();
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
        View view = inflater.inflate(R.layout.fragment_online_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new ComplaintPresenterImp(this);
        if (listOfComplaint != null && listOfComplaint.size() > 0) {
            CommonMethod.showHideItem(recyclerView,rlNoItem );
            init();

        } else {
            CommonMethod.showHideItem(rlNoItem,recyclerView);
        }
        presenter.onLoadMoreItem(getString(R.string.url_complaint), CommonMethod.FROM_COMPLAINT_USER);
        return view;
    }


    private void init() {
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ComplaintAdapter(mContext, CommonMethod.FROM_COMPLAINT_USER, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(presenter.scrollListener(layoutManager, CommonMethod.FROM_COMPLAINT_USER));


    }

    public Uri getLocalBitmapUri(Object model) {
        // Extract Bitmap from ImageView drawable
        String path = "";
        if (model instanceof TempComplaintModel) {
            TempComplaintModel complaintModel = (TempComplaintModel) model;
            path = complaintModel.getFilePath();
        } else if (model instanceof ComplaintModel) {
            ComplaintModel complaintModels = (ComplaintModel) model;
            path = complaintModels.getImagePath();

        }

        Uri bmpUri = null;
        File file = new File(path);
        bmpUri = Uri.fromFile(file);
        return bmpUri;
    }

    private String generateTextToshare(Object model) {
        if (model instanceof TempComplaintModel) {
            TempComplaintModel models = (TempComplaintModel) model;
            return "This  Complaint \n" + models.getComplaintName() + "\n Description: " + models.getDescription() + "Address:\n" + models.getCityName();

        } else {
            ComplaintModel modelComplaint = (ComplaintModel) model;
            return "This  Complaint \n" + modelComplaint.getComplaintName() + "\n Description: " + modelComplaint.getComplaintDescription() + "Address:\n" + modelComplaint.getCityName();

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(CommonMethod.SENT);
        receiver = new SentBroadCastReceiver();
        getActivity().registerReceiver(receiver, filter);

        receiveBroadcast = new ReceiveBroadcastReceiver();
        getActivity().registerReceiver(receiveBroadcast, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
        getActivity().unregisterReceiver(receiveBroadcast);
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
        if (mContext != null)
            mContext = null;
        presenter.onDetach();
    }

    @Override
    public void onComplaintListAvailable(List<ComplaintModel> o) {
        cityManipulate = new CityTableManipulate(mContext);
        for (ComplaintModel model : o
                ) {
            city = cityManipulate.getCity(String.valueOf(model.getCityFkid()), CityModel.FIELD_ID);
            model.setCityName(city != null ? city.getName() : null);
        }

        listOfComplaint = o;
        adapter.addItems(o);
    }

    @Override
    public void onNoMoreList() {

    }

    @Override
    public void onShowPaginationLoading(boolean show) {
        if (show) {
            adapter.addFooter();
//            recyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.notifyItemInserted();
//                }
//            });
        } else {
            adapter.removeFooter();
        }
    }

    @Override
    public void onPaginationError(boolean show) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccessLike(ComplaintModel model, int position) {
//        model.setVoted(true);
//        model.setClickable(false);
//        model.setLikestatus(VOTED);
//        model.setLikeList(null);
//        adapter.hideProgress(position);

    }

    @Override
    public void onFailedLike(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgress(int position) {
        adapter.showProgress(position);

    }

    @Override
    public void onComplaintItemClicked(int position, ComplaintModel model) {
//        Fragment fragment = ComplaintDeatilsFragment.newInstance(model, "");
//        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(ComplaintDeatilsFragment.class.getSimpleName()).commit();


    }

    @Override
    public void onVoteUpClicked(int position, ComplaintModel model) {

    }

    @Override
    public void onShareClicked(int position, ComplaintModel model) {
        CommonMethod.shareItem(mContext, generateTextToshare(model), (model.getComplaintName()), getLocalBitmapUri(model));

    }


//
}
