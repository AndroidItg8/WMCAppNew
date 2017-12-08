package itg8.com.wmcapp.complaint;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.complaint.model.ComplaintModel;
import itg8.com.wmcapp.complaint.model.TempComplaintModel;
import itg8.com.wmcapp.complaint.mvp.ComplaintMVP;
import itg8.com.wmcapp.complaint.mvp.ComplaintPresenterImp;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.registration.RegistrationModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment implements ComplaintMVP.ComplaintView, ComplaintAdapter.ComplaintListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int VOTED = 1;
    private static final java.lang.String TAG = ComplaintFragment.class.getSimpleName();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    CommonMethod.onSetToolbarTitle listener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private ComplaintAdapter adapter;
    private ComplaintMVP.ComplaintPresenter presenter;
    private LinearLayoutManager layoutManager;
    private CityTableManipulate cityManipulate;
    private CityModel city;


    public ComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintFragment newInstance(String param1, String param2) {
        ComplaintFragment fragment = new ComplaintFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter = new ComplaintPresenterImp(this);
        cityManipulate = new CityTableManipulate(mContext);
        initPagination();
        init();
        presenter.onLoadMoreItem(getString(R.string.url_complaint), CommonMethod.FROM_COMPLAINT);
        listener.onSetTitle(getString(R.string.complaint));

        return view;
    }

    private void initPagination() {

    }

    private void init() {
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ComplaintAdapter(mContext, CommonMethod.FROM_COMPLAINT, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(presenter.scrollListener(layoutManager, CommonMethod.FROM_COMPLAINT));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        listener = (CommonMethod.onSetToolbarTitle) mContext;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mContext != null) {
            mContext = null;
            listener = null;
            presenter.onDetach();
        }
    }

    @Override
    public void onComplaintListAvailable(List<ComplaintModel> o) {
        ComplaintModel complaintModel = o.get(0);
        String stringJson = new Gson().toJson(complaintModel);
        Logs.d(TAG,"we are converting complaint to json:"+stringJson);
        complaintModel = new Gson().fromJson(stringJson,new TypeToken<ComplaintModel>(){}.getType());
        Logs.d(TAG,"we are converting String json to Compliant Model:"+complaintModel);
        try {
            RegistrationModel registrationModel = new Gson().fromJson(stringJson, new TypeToken<RegistrationModel>(){}.getType());
            Logs.d(TAG,"Successfully converted Complaint To Registration Model:"+registrationModel);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Logs.d(TAG,"We can not convert Complaint to RegistrationModel because both have diff feild :");
        }
        JSONObject object = null;
        try {
            object = new JSONObject(stringJson);
            if (object.getBoolean("flag")) {

                Logs.d(TAG,"Json ComplaintModel have flag:");
            }else
            {
                Logs.d(TAG,"Json ComplaintModel can not have flag:");

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        for (ComplaintModel model : o
                ) {
            city = cityManipulate.getCity(String.valueOf(model.getCityFkid()), CityModel.FIELD_ID);
            model.setCityName(city != null ? city.getName() : null);
        }


        adapter.addItems(o);
    }

    @Override
    public void onNoMoreList() {

    }

    @Override
    public void onShowPaginationLoading(boolean show) {
        if (show) {
            adapter.addFooter();
//            recyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    adapter.notifyItemInserted();
//                }
//            });
        } else {
            adapter.removeFooter();
        }
    }

    @Override
    public void onPaginationError(boolean show) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onSuccessLike(ComplaintModel model, int position) {
        model.setVoted(true);
        model.setClickable(false);
        model.setLikestatus(VOTED);
        model.setLikeList(null);
        adapter.hideProgress(position);

    }

    @Override
    public void onFailedLike(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgress(int position) {
        adapter.showProgress(position);

    }


    @Override
    public void onComplaintItemClicked(int position, ComplaintModel model) {
        Fragment fragment = ComplaintDeatilsFragment.newInstance(model, "");
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(ComplaintDeatilsFragment.class.getSimpleName()).commit();


    }

    @Override
    public void onVoteUpClicked(int position, ComplaintModel model) {
        showDialogue(model, position);

    }

    private void showDialogue(final ComplaintModel model, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        // set title
        alertDialogBuilder.setTitle("Vote");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want  give vote ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity

                        presenter.onVoteUp(getString(R.string.url_like), model.getPkid(), model, position);

                        dialog.dismiss();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    @Override
    public void onShareClicked(int position, final ComplaintModel model) {
        Observable.create(new ObservableOnSubscribe<Uri>() {
            @Override
            public void subscribe(ObservableEmitter<Uri> e) throws Exception {
                e.onNext( getLocalBitmapUri(model.getImagePath()));
                e.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Uri>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Uri uri) {
                shareItem(mContext,(model.getComplaintName()),generateTextToshare(model), uri);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });





    }

    private void shareItem(Context mContext, String title, String body, Uri uri) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        if (uri != null) {
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        }else
        {
            sharingIntent.setType("text/plain");
        }
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    public Uri getLocalBitmapUri(String path) {


        if(!TextUtils.isEmpty(path)) {
            Logs.d("Path:" + path);

//            File file = null;
//            String fileName = Uri.parse(CommonMethod.BASE_URL +path).getLastPathSegment();
//            file = new File(mContext.getCacheDir(),fileName);
//            Logs.d("Path:" + file.getAbsolutePath());


            String paths = null;
            try {
                 URL url = new URL(CommonMethod.BASE_URL+path);
                Bitmap imag = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                paths = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), imag, "", null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Uri.parse(paths);
        }else
        {
            return null;
        }



    }

    private String generateTextToshare(Object model) {
//        if (model instanceof TempComplaintModel) {
//            TempComplaintModel models = (TempComplaintModel) model;
//            return "This  Complaint \n" + models.getComplaintName() + "\n Description: " + models.getDescription() + "Address:\n" + models.getCityName();
//
//        } else {
            ComplaintModel modelComplaint = (ComplaintModel) model;
            return "This  Complaint \n" + modelComplaint.getComplaintName() + "\n Description: " + modelComplaint.getComplaintDescription() + "\nAddress:" + modelComplaint.getCityName();

//        }
    }




}
