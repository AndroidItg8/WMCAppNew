package itg8.com.wmcapp.complaint;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.NoConnectivityException;
import itg8.com.wmcapp.complaint.model.ComplaintCategoryModel;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.prabhag.PrabhagItemRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintCategoryFragment extends Fragment implements ComplaintCategoryAdapter.ItemClickedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Snackbar snackbar;


    public ComplaintCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintCategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintCategoryFragment newInstance(String param1, String param2) {
        ComplaintCategoryFragment fragment = new ComplaintCategoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaint_category, container, false);
        unbinder = ButterKnife.bind(this, view);
         downloadComplaintCategory();
        return view;
    }

    private void downloadComplaintCategory() {
        Call<List<ComplaintCategoryModel>> call = MyApplication.getInstance().getRetroController().getComplaintCategory(getString(R.string.url_complaint_category_list));
        call.enqueue(new Callback<List<ComplaintCategoryModel>>() {
            @Override
            public void onResponse(Call<List<ComplaintCategoryModel>> call, Response<List<ComplaintCategoryModel>> response) {
                if(response.isSuccessful())
                {
                    if(response.body()!= null)
                    {
                        setRecyclerView(response.body());
                    }else {
                        showSnackerbar(CommonMethod.FROM_ERROR,"Download Failed",false );
                    }
                }else
                {
                    showSnackerbar(CommonMethod.FROM_ERROR,"Download Failed",false );
                }
            }

            @Override
            public void onFailure(Call<List<ComplaintCategoryModel>> call, Throwable t) {
                if(t instanceof NoConnectivityException)
                {
                    showSnackerbar(CommonMethod.FROM_INTERNET,"Download Failed",true );

                }
            }
        });
    }

    private void setRecyclerView(List<ComplaintCategoryModel> body) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ComplaintCategoryAdapter(getActivity(), body, this));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void showSnackerbar(int from, String message, boolean isConnected) {

        snackbar = Snackbar.make(recyclerView, message, Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        int color = 0;
        if (from == CommonMethod.FROM_INTERNET) {

            if (!isConnected) {
                color = Color.WHITE;
                hideSnackbar();

            } else {
                color = Color.RED;

            }
            textView.setTextColor(color);
            textView.setMaxLines(2);

        } else {
            textView.setTextColor(color);
            textView.setMaxLines(2);
        }

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSnackbar();

            }
        });
        snackbar.show();
    }

    private void hideSnackbar() {
        snackbar.dismiss();
    }

    @Override
    public void onItemClicked(int position, ComplaintCategoryModel model) {
    Fragment     fragment = AddComplaintFragment.newInstance(model,"");
        callFragment(fragment);


    }

    private void callFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.frame_container,fragment);
        ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.commit();

    }
}
