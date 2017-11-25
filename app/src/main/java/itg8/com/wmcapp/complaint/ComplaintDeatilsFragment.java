package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.widget.CircularImageView;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintDeatilsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintDeatilsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_garbage)
    ImageView imgGarbage;
    @BindView(R.id.txt_noImg)
    CustomFontTextView txtNoImg;
    @BindView(R.id.rl_center)
    RelativeLayout rlCenter;
    @BindView(R.id.lbl_cityName)
    CustomFontTextView lblCityName;
    @BindView(R.id.lbl_problem_value)
    CustomFontTextView lblProblemValue;
    @BindView(R.id.lbl_address_value)
    CustomFontTextView lblAddressValue;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.img)
    CircularImageView img;
    @BindView(R.id.lbl_name_value)
    CustomFontTextView lblNameValue;
    @BindView(R.id.lbl_days_value)
    CustomFontTextView lblDaysValue;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.lbl_vote)
    CustomFontTextView lblVote;
    @BindView(R.id.lbl_share)
    CustomFontTextView lblShare;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ComplaintModel model;
    private Context mContext;
    private static final int VOTED = 1;
    private static final int VOTE_UP = 0;
    private Unbinder unbinder;


    public ComplaintDeatilsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintDeatilsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintDeatilsFragment newInstance(ComplaintModel param1, String param2) {
        ComplaintDeatilsFragment fragment = new ComplaintDeatilsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = getArguments().getParcelable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_deatils, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        if (model.getImagePath() != null) {

            imgGarbage.setVisibility(View.VISIBLE);
            Picasso.with(mContext)
                    .load(CommonMethod.BASE_URL + model.getImagePath())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imgGarbage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed
                            Picasso.with(mContext)
                                    .load(CommonMethod.BASE_URL + model.getImagePath())
                                    .into(imgGarbage, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            imgGarbage.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onError() {
                                            imgGarbage.setVisibility(View.GONE);
                                        }
                                    });
                        }
                    });
        } else {
            txtNoImg.setVisibility(View.VISIBLE);

        }

        lblCityName.setText(CommonMethod.checkEmpty(model.getCityName()));
        lblAddressValue.setText(CommonMethod.checkEmpty(model.getComplaintName()));
        lblProblemValue.setText(CommonMethod.checkEmpty(model.getComplaintDescription()));
        lblNameValue.setText(CommonMethod.checkEmpty(model.getUserFullename()));
        CommonMethod.setUserPicaso(mContext, model.getUserProfilepic(),img);




        if (model.getLikestatus() == VOTE_UP) {
            lblVote.setText("VOTE UP"+" ("+ model.getLikeList().size() +")");
            lblVote.setTextColor((lblVote.getContext().getResources().getColor(R.color.colorGray)));
        } else {
            lblVote.setText("VOTED ("+ model.getLikeList().size() +")");
            lblVote.setTextColor((lblVote.getContext().getResources().getColor(R.color.colorPrimary)));
        }

        if (model.getLikeList() != null && model.getLikeList().size() > 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            recyclerView.setAdapter(new LikeAdapter(mContext, model.getLikeList()));
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
        }

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
        if (mContext != null) {
            mContext = null;
        }
        super.onDetach();
    }
}
