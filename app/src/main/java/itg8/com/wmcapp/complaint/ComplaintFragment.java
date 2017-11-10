package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
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
        presenter.onLoadMoreItem(getString(R.string.url_complaint));

        return view;
    }

    private void initPagination() {

    }

    private void init() {
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ComplaintAdapter(mContext, this);
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
        if (mContext != null)
            mContext = null;
        presenter.onDetach();
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
    public void onSuccessLike(ComplaintModel model) {
         model.setVoted(true);

        adapter.hideProgress();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedLike(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgress() {
        adapter.showProgress();

    }

    @Override
    public void onComplaintItemClicked(int position, ComplaintModel model) {
        Fragment fragment = ComplaintDeatilsFragment.newInstance(model, "");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(ComplaintDeatilsFragment.class.getSimpleName()).commit();


    }

    @Override
    public void onVoteUpClicked(int position, ComplaintModel model) {
        showDialogue(model);

    }

    private void showDialogue(final ComplaintModel model) {
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

                        presenter.onVoteUp(getString(R.string.url_like), model.getPkid(),model);

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
        CommonMethod.shareItem(getActivity(), generateTextToshare(model), model.getComplaintName());

    }

    private String generateTextToshare(ComplaintModel model) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return Html.fromHtml("Problems:<b>" + model.getComplaintDescription() + "</b> \nAddress: " + model.getComplaintName() + "\n<a href=\"" + CommonMethod.BASE_URL + model.getImagePath()+"\">"+CommonMethod.BASE_URL + model.getImagePath()+"</a>",0).toString();
            return Html.fromHtml("Problems:<b>" + model.getComplaintDescription() + "</b> \nAddress: " + model.getComplaintName() + "\n</br><a href=\"http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg\">http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg</a>", 0).toString();
        } else {
            return Html.fromHtml("Problems:<b>" + model.getComplaintDescription() + "</b> \nAddress: " + model.getComplaintName() + "\n</br><a href=\"http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg\">http://winnipeg.ca/waterandwaste/images/garbage/garbage_cc.jpg</a>").toString();

        }

    }


}
