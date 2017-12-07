package itg8.com.wmcapp.complaint;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.ReceiveBroadcastReceiver;
import itg8.com.wmcapp.common.SentBroadCastReceiver;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.database.ComplaintTableManipute;

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

    Unbinder unbinder;
    @BindView(R.id.rl_no_item)
    RelativeLayout rlNoItem;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private SentBroadCastReceiver receiver;
    private ReceiveBroadcastReceiver receiveBroadcast;


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
        ComplaintTableManipute complaintTableManipute = new ComplaintTableManipute(mContext);
        List<TempComplaintModel> listTempComplaintModel = complaintTableManipute.getAllComplaint();
        if (listTempComplaintModel != null && listTempComplaintModel.size() > 0) {
            CommonMethod.showHideItem(recyclerView, rlNoItem);
            setRecyclerView(listTempComplaintModel);
        } else {
            CommonMethod.showHideItem(rlNoItem,recyclerView );
        }

    }

    private void setRecyclerView(List<TempComplaintModel> complaintMergeList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ComplaintProfilOfflineeAdapter(getActivity(), complaintMergeList, this));
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
    public void onShareItemClicked(int position, TempComplaintModel model, ImageView view) {
            CommonMethod.shareItem(getActivity(), generateTextToshare(model), (model.getComplaintName()), getLocalBitmapUri(model.getFilePath()));
    }


    public Uri getLocalBitmapUri( String path) {
        if(!TextUtils.isEmpty(path)) {
            Logs.d("Path:" + path);

//            File file = null;
//            String fileName = Uri.parse(CommonMethod.BASE_URL +path).getLastPathSegment();
//            file = new File(mContext.getCacheDir(),fileName);
//            Logs.d("Path:" + file.getAbsolutePath());


            String paths = null;
            try {
                URL url = new URL(CommonMethod.BASE_URL+path);
                Bitmap imag = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                paths = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), imag, "", null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Uri.parse(paths);
        }else
        {
            return null;
        }




    }

    private String generateTextToshare(TempComplaintModel models) {
            return "This  Complaint \n" + models.getComplaintName() + "\n Description: " + models.getDescription() + "\nAddress:" + models.getCityName();


    }

    @Override
    public void onSMSItemClicked(int position, TempComplaintModel model) {
        SendSMS("9823857732", generateSMSText(model));
    }

    private void SendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent(CommonMethod.SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), 0, new Intent(CommonMethod.DELIVERED), 0);
        Intent intent = new Intent();
        intent.setAction(CommonMethod.SENT);
        getActivity().sendBroadcast(intent);
        intent.setAction(CommonMethod.DELIVERED);
        getActivity().sendBroadcast(intent);


        try {
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "exception", Toast.LENGTH_LONG).show();
        }
    }


    private String generateSMSText(TempComplaintModel model) {
        String builder;
        builder = "Dear Sir, " + model.getComplaintName() + "\n  We  have this problem "
                + model.getDescription() + "\n In this location" + model.getAdd();
        return builder;

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
}
