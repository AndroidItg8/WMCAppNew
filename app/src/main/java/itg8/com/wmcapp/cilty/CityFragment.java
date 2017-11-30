package itg8.com.wmcapp.cilty;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.widget.CustomFontTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityFragment extends Fragment implements CityAdapter.CityItemClickedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.lbl_select_city)
    CustomFontTextView lblSelectCity;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rg_city)
    RadioGroup rgCity;
    @BindView(R.id.progressView)
    CircularProgressView progressView;
    @BindView(R.id.btn_dismiss)
    FloatingActionButton btnDismiss;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ArrayList<CityModel> list;
    private CityAdapter cityAdapter;

    public CityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cityModelList Parameter 1.
     * @param param2        Parameter 2.
     * @return A new instance of fragment CityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CityFragment newInstance(List<CityModel> cityModelList, String param2) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, (ArrayList<? extends Parcelable>) cityModelList);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        btnDismiss.setOnClickListener(this);
        initRecyclerView(list);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onQueryTextChange(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initRecyclerView(List<CityModel> filteredModelList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cityAdapter = new CityAdapter(getActivity(), filteredModelList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(cityAdapter);
    }

    private boolean onQueryTextChange(String s) {
        final List<CityModel> filteredModelList = filter(list, s);
        initRecyclerView(filteredModelList);
        return true;
    }

    private List<CityModel> filter(List<CityModel> mainList, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<CityModel> filteredModelList = new ArrayList<>();
        for (CityModel model : mainList) {
            final String text = model.getName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(CityModel model) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onCityItemClicked(int position, CityModel cityModel) {
        cityModel.setSelected(true);
        Prefs.putInt(CommonMethod.SELECTED_CITY, cityModel.getID());
        lblSelectCity.setText("Your Selected City:"+CommonMethod.checkEmpty(cityModel.getName()));
        cityAdapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View view) {
            mListener.onFragmentInteraction();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

}
