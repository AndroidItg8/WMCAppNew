package itg8.com.wmcapp.torisum;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common_method.CommonMethod;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TorisumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TorisumFragment extends Fragment implements TorisumAdapter.TorisumItemClickecListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TorisumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TorisumFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TorisumFragment newInstance(String param1, String param2) {
        TorisumFragment fragment = new TorisumFragment();
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
        View view = inflater.inflate(R.layout.fragment_torisum, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
         recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TorisumAdapter(getActivity(), this));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClicked() {
        Fragment fragment =  TorisumDetailsFragment.newInstance("","");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(TorisumDetailsFragment.class.getSimpleName()).commit();



    }
}
