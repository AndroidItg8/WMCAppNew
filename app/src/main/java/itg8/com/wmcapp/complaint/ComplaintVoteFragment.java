package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.profile.LikeProfileAdapter;
import itg8.com.wmcapp.profile.model.UserLikeModel;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintVoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintVoteFragment extends Fragment {
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
    private Context mContext;


    public ComplaintVoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintVoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintVoteFragment newInstance(String param1, String param2) {
        ComplaintVoteFragment fragment = new ComplaintVoteFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaint_vote, container, false);
getLikeListFromServer();

         recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//         recyclerView.setAdapter(new LikeProfileAdapter(mContext, list ));

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    private void getLikeListFromServer() {
//        Call<List<UserLikeModel>> call = MyApplication.getInstance().getRetroController();
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
