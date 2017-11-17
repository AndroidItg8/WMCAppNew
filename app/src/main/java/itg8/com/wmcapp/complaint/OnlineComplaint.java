package itg8.com.wmcapp.complaint;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.OnRecyclerviewClickListener;
import itg8.com.wmcapp.common.ReceiveBroadcastReceiver;
import itg8.com.wmcapp.common.SentBroadCastReceiver;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.complaint.mvp.ComplaintMVP;
import itg8.com.wmcapp.complaint.mvp.ComplaintPresenterImp;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OnlineComplaint#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnlineComplaint extends Fragment implements ComplaintMVP.ComplaintView, OnRecyclerviewClickListener<ComplaintModel> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.ll_no_item)
    LinearLayout llNoItem;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SentBroadCastReceiver receiver;
    private Context mContext;
    private BroadcastReceiver receiveBroadcast;
    private ComplaintMVP.ComplaintPresenter presenter;
    private List<ComplaintModel> listOfComplaint;


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
        presenter.onLoadMoreItem(getString(R.string.url_complaint));

        if (listOfComplaint != null && listOfComplaint.size() > 0) {
             CommonMethod.showHideItem(recyclerView, llNoItem);
            setRecyclerView(listOfComplaint);
        } else {
            CommonMethod.showHideItem( llNoItem,recyclerView);

        }
        return view;
    }

    @Override
    public void onComplaintListAvailable(List<ComplaintModel> o) {
        CommonMethod.showHideItem(recyclerView, llNoItem);
        setListAndRecyclerView(o);
    }

    private void setListAndRecyclerView(List<ComplaintModel> o) {
        listOfComplaint = o;
        setRecyclerView(o);
    }


    private void sortComplaintList(List<ComplaintModel> o) {
        Collections.sort(o, new Comparator<ComplaintModel>() {
            public int compare(ComplaintModel m1, ComplaintModel m2) {
                return m1.getLastModifiedDate().compareToIgnoreCase(m2.getLastModifiedDate());
            }
        });
    }

    private void setRecyclerView(List<ComplaintModel> complaintMergeList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new ComplaintProfileOnlineAdapter(mContext, complaintMergeList, this));
    }

//


    @Override
    public void onNoMoreList() {
        CommonMethod.showHideItem(recyclerView, llNoItem);

    }

    @Override
    public void onShowPaginationLoading(boolean show) {

    }

    @Override
    public void onPaginationError(boolean show) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccessLike(ComplaintModel model, int position) {

    }

    @Override
    public void onFailedLike(String s) {


    }

    @Override
    public void showProgress(int position) {

    }

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
            presenter.onDetach();
            mContext = null;
        }
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

//        Drawable drawable = imageView.getDrawable();
//        Bitmap bmp = null;
//        if (drawable instanceof BitmapDrawable){
//            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        } else {
//            return null;
//        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        // Use methods on Context to access package-specific directories on external storage.
        // This way, you don't need to request external read/write permission.
        // See https://youtu.be/5xVh-7ywKpE?t=25m25s
//            File file =  new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
        File file = new File(path);
//            FileOutputStream out = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.close();
        // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
//            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                bmpUri = FileProvider.getUriForFile( mContext,"itg8.com.wmcapp.fileprovider", file);
//            }else {
        bmpUri = Uri.fromFile(file);
//            }


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
    public void onClick(int position, ComplaintModel model) {

        CommonMethod.shareItem(mContext, generateTextToshare(model), (model.getComplaintName()), getLocalBitmapUri(model));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
