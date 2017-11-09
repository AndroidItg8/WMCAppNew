package itg8.com.wmcapp.news;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment implements NewsAdapter.NewsItemClickedListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final long FADE_DEFAULT_TIME = 400;
    private static final long MOVE_DEFAULT_TIME = 100;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NewsDetailsFragment fragment;


    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter( new NewsAdapter(getActivity(), this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemNewsClicked(int position, ImageView img) {
        fragment = NewsDetailsFragment.newInstance(ViewCompat.getTransitionName(img), "");

//        // 1. Exit for Previous Fragment
//        Fade exitFade = new Fade();
//        exitFade.setDuration(FADE_DEFAULT_TIME);
//        Fragment previousFragment = getFragmentManager().findFragmentById(R.id.frame_container);
//
//        previousFragment.setExitTransition(exitFade);
//
//
//        // 2. Shared Elements Transition
//        TransitionSet enterTransitionSet = new TransitionSet();
//        enterTransitionSet.addTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
//        enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
//        enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
//        fragment.setSharedElementEnterTransition(enterTransitionSet);
//
//        // 3. Enter Transition for New Fragment
//        Fade enterFade = new Fade();
//        enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
//        enterFade.setDuration(FADE_DEFAULT_TIME);
//        fragment.setEnterTransition(enterFade);

    FragmentManager fm =getFragmentManager();

    FragmentTransaction fragmentTransaction= fm.beginTransaction();
        fragmentTransaction.addSharedElement(img,  ViewCompat.getTransitionName(img));
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    //    fragmentTransaction.commitAllowingStateLoss();
    }
}
