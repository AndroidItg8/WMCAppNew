package itg8.com.wmcapp.news;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.news.model.NewsModel;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img)
    ImageView img;
    Unbinder unbinder;
    @BindView(R.id.lbl_title)
    CustomFontTextView lblTitle;
    @BindView(R.id.lbl_date)
    CustomFontTextView lblDate;
    @BindView(R.id.lbl_description)
    CustomFontTextView lblDescription;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NewsModel model;
    private Context mContext;


    public NewsDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param model  Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsDetailsFragment newInstance(NewsModel model, String param2) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, model);
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
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        lblTitle.setText(CommonMethod.checkEmpty(model.getTitle()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            lblDescription.setText(CommonMethod.checkEmpty(String.valueOf(Html.fromHtml(model.getDescription(),Html.FROM_HTML_MODE_LEGACY))));
        } else {
            lblDescription.setText(Html.fromHtml(model.getDescription()));
        }
        lblDescription.setText(CommonMethod.checkEmpty(String.valueOf(Html.fromHtml(model.getDescription()))));
        lblDate.setText(CommonMethod.getFormattedDateTime(model.getAddate()));
        CommonMethod.setUserPicaso(mContext, model.getProfilePic(),img);

        return view;
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
        if(mContext!= null)
        {
            mContext = null;
        }
    }
}
