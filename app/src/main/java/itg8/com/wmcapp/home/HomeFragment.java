package itg8.com.wmcapp.home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.NoticeBoardFragment;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.complaint.AddComplaintFragment;
import itg8.com.wmcapp.complaint.ComplaintCategoryFragment;
import itg8.com.wmcapp.complaint.ComplaintFragment;
import itg8.com.wmcapp.emergency.EmergencyFragment;
import itg8.com.wmcapp.news.NewsFragment;
import itg8.com.wmcapp.prabhag.PrabhagFragment;
import itg8.com.wmcapp.torisum.TorisumFragment;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.card_complaint)
    CardView cardComplaint;
    @BindView(R.id.card_news)
    CardView cardNews;
    @BindView(R.id.card_notice)
    CardView cardNotice;
    @BindView(R.id.card_tourism)
    CardView cardTourism;
    @BindView(R.id.card_ward)
    CardView cardWard;
    @BindView(R.id.card_emergency)
    CardView cardEmergency;
    Unbinder unbinder;
    @BindView(R.id.txt_name)
    CustomFontTextView txtName;
    @BindView(R.id.rl_include)
    RelativeLayout rlInclude;
    CommonMethod.onSetToolbarTitle listener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Fragment fragment;
    private Context mContext;
    private String name;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();

        return view;
    }

    private void init() {
        if (name != null)
            txtName.setText(String.valueOf(getString(R.string.title_welcome) + name));
        cardComplaint.setOnClickListener(this);
        cardNews.setOnClickListener(this);
        cardEmergency.setOnClickListener(this);
        cardNotice.setOnClickListener(this);
        cardWard.setOnClickListener(this);
        cardTourism.setOnClickListener(this);
        rlInclude.setOnClickListener(this);
        listener.onSetTitle(getString(R.string.app_name));
//        listener.setDrawer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_complaint:
                fragment = ComplaintFragment.newInstance("", "");
                break;
            case R.id.card_emergency:
                fragment = EmergencyFragment.newInstance("", "");
                break;
            case R.id.card_news:
                fragment = NewsFragment.newInstance("", "");
                break;
            case R.id.card_notice:
                fragment = NoticeBoardFragment.newInstance("", "");
                break;
            case R.id.card_tourism:
                fragment = TorisumFragment.newInstance("", "");
                break;
            case R.id.card_ward:
                fragment = PrabhagFragment.newInstance(1);
                break;
            case R.id.rl_include:
                fragment = ComplaintCategoryFragment.newInstance("", "");
                break;
        }

        if (fragment != null) {
            callFragment(fragment);
        }
    }

    private void callFragment(Fragment fragment) {
        ((HomeActivity)getActivity()).callFragment(fragment);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (CommonMethod.onSetToolbarTitle) mContext;
    }

    public void sendProfleName(String fullName) {
        if (txtName != null)
            txtName.setText(String.valueOf(getString(R.string.title_welcome) + fullName));
        else
            name = fullName;
    }


}
