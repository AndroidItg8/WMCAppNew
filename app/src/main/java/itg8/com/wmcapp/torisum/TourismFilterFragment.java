package itg8.com.wmcapp.torisum;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.torisum.model.HeadingView;
import itg8.com.wmcapp.torisum.model.InfoView;
import itg8.com.wmcapp.torisum.model.SubCatList;
import itg8.com.wmcapp.torisum.model.TourismFilterModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TourismFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TourismFilterFragment extends Fragment implements View.OnClickListener, InfoView.ItemCheckListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.expandableView)
    ExpandablePlaceHolderView expandableView;
    Unbinder unbinder;
    @BindView(R.id.fab_filter)
    FloatingActionButton fabFilter;
    FilterItemListener listener;
    CommonMethod.OnBackPressListener onBackPressListener;
    @BindView(R.id.fab_filter_clear)
    FloatingActionButton fabFilterClear;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private List<TourismFilterModel> listofCategoryModel;
    private List<SubCatList> selectedList;
    private InfoView infoView;
    private HeadingView header;

    public TourismFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TourismFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TourismFilterFragment newInstance(List<TourismFilterModel> param1, String param2) {
        TourismFilterFragment fragment = new TourismFilterFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listofCategoryModel = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tourism_filter, container, false);
        unbinder = ButterKnife.bind(this, view);

        selectedList = new ArrayList<>();
        if(listener.getCategoryTemp()!= null){
            listofCategoryModel= listener.getCategoryTemp();

        }
        if (listofCategoryModel != null) {
            for (TourismFilterModel model : listofCategoryModel) {
                header=new HeadingView(getActivity(), model.getCategoryName());
                expandableView.addView(header);
                for (SubCatList info : model.getSubCatList()) {
                   infoView= new InfoView(getActivity(), info, this);
                    expandableView.addView(infoView);
                }
                expandableView.expand(header);
            }
        }
        fabFilter.setOnClickListener(this);
        fabFilterClear.setOnClickListener(this);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mContext != null)
            mContext = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (FilterItemListener) mContext;
        onBackPressListener = (CommonMethod.OnBackPressListener) mContext;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null)
            mContext = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_filter:
                listener.selectItemList(selectedList);
                listener.saveCategoryTemp(listofCategoryModel);
                onBackPressListener.onBackPress();
                break;
            case R.id.fab_filter_clear:
                infoView.clearItem();
                break;
        }
    }


    @Override
    public void onItemCheck(SubCatList info) {
        info.setChecked(true);
        if(!selectedList.contains(info))
              selectedList.add(info);

    }

    @Override
    public void onItemUnCheck(SubCatList info) {
        info.setChecked(false);
        selectedList.remove(info);

    }



    public interface FilterItemListener {
        void selectItemList(List<SubCatList> selectedList);
        void saveCategoryTemp(List<TourismFilterModel> listofCategoryModel);
        List<TourismFilterModel> getCategoryTemp();
    }
}
