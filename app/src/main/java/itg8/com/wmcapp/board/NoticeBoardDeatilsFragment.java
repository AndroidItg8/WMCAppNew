package itg8.com.wmcapp.board;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.model.NoticeBoardModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoticeBoardDeatilsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticeBoardDeatilsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_garbage)
    ImageView imgGarbage;
    @BindView(R.id.lbl_title_only)
    CustomFontTextView lblTitleOnly;
    @BindView(R.id.lbl_description)
    CustomFontTextView lblDescription;
    @BindView(R.id.lbl_date)
    CustomFontTextView lblDate;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NoticeBoardModel model;
    private Context context;


    public NoticeBoardDeatilsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticeBoardDeatilsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticeBoardDeatilsFragment newInstance(NoticeBoardModel param1, String param2) {
        NoticeBoardDeatilsFragment fragment = new NoticeBoardDeatilsFragment();
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
        View view = inflater.inflate(R.layout.fragment_notice_board_deatils, container, false);
        unbinder = ButterKnife.bind(this, view);
         init();
        return view;
    }

    private void init() {
        lblTitleOnly.setText(CommonMethod.checkEmpty(model.getNoticeName()));
        lblDescription.setText(CommonMethod.checkEmpty(model.getNoticeDescription()));
        lblDate.setText(CommonMethod.getFormattedDateTime(model.getAddedDate()));
        Picasso.with(context)
                .load(CommonMethod.BASE_URL + model.getImagePath())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imgGarbage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(context)
                                .load(CommonMethod.BASE_URL + model.getImagePath())
                                .into(imgGarbage);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
