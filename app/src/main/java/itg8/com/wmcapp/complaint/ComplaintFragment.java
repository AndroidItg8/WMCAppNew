package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.complaint.mvp.ComplaintMVP;
import itg8.com.wmcapp.complaint.mvp.ComplaintPresenterImp;
import itg8.com.wmcapp.database.CityTableManipulate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment implements ComplaintMVP.ComplaintView, ComplaintAdapter.ComplaintListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int VOTED = 1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    CommonMethod.onSetToolbarTitle listener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private ComplaintAdapter adapter;
    private ComplaintMVP.ComplaintPresenter presenter;
    private LinearLayoutManager layoutManager;
    private CityTableManipulate cityManipulate;
    private CityModel city;


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
        presenter = new ComplaintPresenterImp(this);
        cityManipulate = new CityTableManipulate(mContext);
        initPagination();
        init();
        presenter.onLoadMoreItem(getString(R.string.url_complaint), CommonMethod.FROM_COMPLAINT);
        listener.onSetTitle(getString(R.string.complaint));

        return view;
    }

    private void initPagination() {

    }

    private void init() {
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ComplaintAdapter(mContext, CommonMethod.FROM_COMPLAINT, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(presenter.scrollListener(layoutManager, CommonMethod.FROM_COMPLAINT));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (CommonMethod.onSetToolbarTitle) mContext;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null) {
            mContext = null;
            listener = null;
            presenter.onDetach();
        }
    }

    @Override
    public void onComplaintListAvailable(List<ComplaintModel> o) {
        for (ComplaintModel model : o
                ) {
            city = cityManipulate.getCity(String.valueOf(model.getCityFkid()), CityModel.FIELD_ID);
            model.setCityName(city != null ? city.getName() : null);
        }


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
        model.setVoted(true);
        model.setClickable(false);
        model.setLikestatus(VOTED);
        model.setLikeList(null);
        adapter.hideProgress(position);

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
        Fragment fragment = ComplaintDeatilsFragment.newInstance(model, "");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(ComplaintDeatilsFragment.class.getSimpleName()).commit();


    }

    @Override
    public void onVoteUpClicked(int position, ComplaintModel model) {
        showDialogue(model, position);

    }

    private void showDialogue(final ComplaintModel model, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        // set title
        alertDialogBuilder.setTitle("Vote");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want  give vote ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity

                        presenter.onVoteUp(getString(R.string.url_like), model.getPkid(), model, position);

                        dialog.dismiss();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    @Override
    public void onShareClicked(int position, ComplaintModel model) {
        shareItem(mContext, generateTextToshare(model), (model.getComplaintName()), getLocalBitmapUri(model.getImagePath()));


    }

    private void shareItem(Context mContext, String title, String body, Uri uri) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        if (uri != null) {
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
//            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(sharingIntent, "Share"));
        }
    }

    public Uri getLocalBitmapUri(String path) {
        // Extract Bitmap from ImageView drawable
//        String path = "";
//        if (model instanceof TempComplaintModel) {
//            TempComplaintModel complaintModel = (TempComplaintModel) model;
//            path = complaintModel.getFilePath();
//        } else if (model instanceof ComplaintModel) {
//            ComplaintModel complaintModels = (ComplaintModel) model;
//            path = complaintModels.getImagePath();
//
//        }
        Uri bmpUri = null;
// {
        if(!TextUtils.isEmpty(path)) {
            File file = new File(path);
            Logs.d("Path:" + path);
            bmpUri = Uri.fromFile(file);
        }
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

//    private String generateTextToshare(ComplaintModel model) {
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////            return Html.fromHtml("Problems:<b>" + model.getComplaintDescription() + "</b> \nAddress: " + model.getComplaintName() + "\n<a href=\"" + CommonMethod.BASE_URL + model.getImagePath()+"\">"+CommonMethod.BASE_URL + model.getImagePath()+"</a>",0).toString();
//            return Html.fromHtml("Problems:<b>" + model.getComplaintDescription() + "</b> \nAddress: " + model.getComplaintName() + "\n</br><a href=\"http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg\">http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg</a>", 0).toString();
//        } else {
//            return Html.fromHtml("Problems:<b>" + model.getComplaintDescription() + "</b> \nAddress: " + model.getComplaintName() + "\n</br><a href=\"http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg\">http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg</a>").toString();
//
//        }
//
//    }


}
