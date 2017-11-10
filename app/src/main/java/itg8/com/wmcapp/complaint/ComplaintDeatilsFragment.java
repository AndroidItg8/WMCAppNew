package itg8.com.wmcapp.complaint;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.complaint.model.ComplaintModel;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ComplaintModel model;


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
        View view =  inflater.inflate(R.layout.fragment_complaint_deatils, container, false);
         return view ;
    }

}
