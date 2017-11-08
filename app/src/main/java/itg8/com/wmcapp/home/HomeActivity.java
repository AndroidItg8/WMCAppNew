package itg8.com.wmcapp.home;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.NoticeBoardFragment;
import itg8.com.wmcapp.change_password.ChangePswdFragment;
import itg8.com.wmcapp.cilty.CityAdapter;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.cilty.mvp.CityMVP;
import itg8.com.wmcapp.cilty.mvp.CityPresenterImp;
import itg8.com.wmcapp.common.BaseActivity;
import itg8.com.wmcapp.common.CallType;
import itg8.com.wmcapp.common.CommonCallback;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.CustomDialogFragment;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.complaint.ComplaintFragment;
import itg8.com.wmcapp.contact.ContactUsFragment;
import itg8.com.wmcapp.database.BaseDatabaseHelper;
import itg8.com.wmcapp.emergency.EmergencyFragment;
import itg8.com.wmcapp.feedback.FeedbackFragment;
import itg8.com.wmcapp.news.NewsFragment;
import itg8.com.wmcapp.prabhag.PrabhagFragment;
import itg8.com.wmcapp.prabhag.WardMemberFragment;
import itg8.com.wmcapp.prabhag.dummy.DummyContent;
import itg8.com.wmcapp.prabhag.model.ContactModel;
import itg8.com.wmcapp.signup.LoginActivity;
import itg8.com.wmcapp.suggestion.SuggestionFragment;
import itg8.com.wmcapp.torisum.TorisumFragment;

import static itg8.com.wmcapp.common.CallType.PRABHAG;
import static itg8.com.wmcapp.common.CallType.WARD;
import static itg8.com.wmcapp.common.CallType.WARD_MEMBER;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, CommonCallback.OnImagePickListener,
        CustomDialogFragment.DialogItemClickListener,
        PrabhagFragment.OnListFragmentInteractionListener, CityMVP.CityView, CityAdapter.CityItemClickedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static int english = 0;
    private static int hindi = 1;
    private static int marathi = 2;
    Fragment fragment = null;
    CommonCallback.OnDialogClickListner listner;
    String[] items = {"Pick From Camera", "Pick From File"};
    private CallType isFrom;
    private List<CityModel> list;
    private Snackbar snackbar;
    private CityMVP.CityPresenter presenter;
    private Dao<CityModel, Integer> mDAOCity = null;
    private List<CityModel> cityList = null;
    private CityAdapter cityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new CityPresenterImp(this);
        presenter.onGetCity(getString(R.string.url_city));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (Prefs.getString(CommonMethod.HEADER) == null) {


        }
        fragment = NoticeBoardFragment.newInstance("", "");
        callFragment(fragment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_language:
                openBottomSheetForLanguage();
                break;
            case R.id.action_city:
                if (cityList != null && cityList.size() > 0)
                    openBottomSheetForCity(cityList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBottomSheetForCity(List<CityModel> cityList) {
        View view = getLayoutInflater().inflate(R.layout.fragment_city, null);

        final Dialog mBottomSheetDialog = new Dialog(HomeActivity.this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        Button btnDismiss = view.findViewById(R.id.btn_dismiss);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cityAdapter = new CityAdapter(getApplicationContext(), cityList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(cityAdapter);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();

    }

    private void openBottomSheetForLanguage() {
        View view = getLayoutInflater().inflate(R.layout.fragment_language, null);

        final Dialog mBottomSheetDialog = new Dialog(HomeActivity.this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        RadioGroup radioGroup = mBottomSheetDialog.findViewById(R.id.rg_language);

        Button btnDismiss = mBottomSheetDialog.findViewById(R.id.btn_dismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                int langauge = 0;
                switch (id) {
                    case R.id.rgb_english:
                        langauge = english;
                        break;
                    case R.id.rgb_hindi:
                        langauge = hindi;
                        break;
                    case R.id.rgb_marathi:
                        langauge = marathi;
                        break;
                    default:
                        break;
                }
                Prefs.putString(CommonMethod.LANGUAGE, String.valueOf(langauge));
                Logs.d("LANGUAGE:" + String.valueOf(langauge));


            }
        });


        mBottomSheetDialog.show();


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_notice_board:
                fragment = NoticeBoardFragment.newInstance("", "");
                break;
            case R.id.nav_complaint:
                fragment = ComplaintFragment.newInstance("", "");
                break;
            case R.id.nav_tourism:
                fragment = TorisumFragment.newInstance("", "");
                break;
            case R.id.nav_news:
                fragment = NewsFragment.newInstance("", "");
                break;
            case R.id.nav_praphag:
                isFrom = PRABHAG;
                fragment = PrabhagFragment.newInstance(1);
                break;
            case R.id.nav_profile:
                fragment = ComplaintFragment.newInstance("", "");
                break;
            case R.id.nav_suggestion:
                fragment = SuggestionFragment.newInstance("", "");
                break;
            case R.id.nav_feedback:
                fragment = FeedbackFragment.newInstance("", "");
                break;
            case R.id.nav_emgs_no:
                fragment = EmergencyFragment.newInstance("", "");
                break;
            case R.id.nav_contact:
                fragment = ContactUsFragment.newInstance("", "");
                break;
            case R.id.nav_change_pswd:
                fragment = ChangePswdFragment.newInstance("", "");
                break;
            case R.id.nav_registration:
                callLoginActivity();
                break;
            case R.id.nav_logout:
                clearNLogout();
                break;
        }
        if (fragment != null) {
            callFragment(fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callLoginActivity() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void clearNLogout() {
        Prefs.remove(CommonMethod.HEADER);
        FirebaseAuth.getInstance().signOut();
        Log.d(TAG, "Firebase User:" + FirebaseAuth.getInstance().getCurrentUser());
        callLoginActivity();
        finish();


    }


    private void showWard() {
        isFrom = WARD;
        callFragment(PrabhagFragment.newInstance(1));
    }

    private void showWardMemberDetail() {
        isFrom = WARD_MEMBER;
        callFragment(WardMemberFragment.newInstance(getDummyContacts()));
    }


    public List<ContactModel> getDummyContacts() {
        List<ContactModel> models = new ArrayList<>();
        models.add(new ContactModel());
        models.add(new ContactModel());
        return models;
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (isFrom == WARD)
                isFrom = PRABHAG;
            else if (isFrom == PRABHAG)
                isFrom = null;
            else if (isFrom == WARD_MEMBER)
                isFrom = WARD;
            super.onBackPressed();
        }
    }

    @Override
    public void onImagePickClick() {
        CustomDialogFragment.newInstance(items).show(getSupportFragmentManager(), CustomDialogFragment.TAG);
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            listner.onOpenCamera();
        } else if (position == 1) {
            listner.onSelectFromFileManager();
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        if (isFrom == PRABHAG) {
            showWard();
        } else if (isFrom == WARD) {
            showWardMemberDetail();
        }
    }

    @Override
    public void onGetCityList(List<CityModel> list) {
//        try {
//            saveBrandToDatabase(list);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        this.cityList = list;


    }

    @Override
    public void onFail(String message) {
        showTextSnackbar(message);


    }

    @Override
    public void onError(Object t) {
        showTextSnackbar(t.toString());


    }

    @Override
    public void showProgress() {
        // progressView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        //  progressView.setVisibility(View.GONE);


    }

    @Override
    public void onNoInternetConnect(boolean b) {
        showSnackbar(b);


    }


    public void showSnackbar(boolean isConnected) {

        int color;
        String message;
        if (!isConnected) {

            message = "Connected to Internet";
            color = Color.WHITE;
            hideSnackbar();

        } else {
            message = " Not connected to internet...Please try again";
            color = Color.RED;
        }
        snackbar = Snackbar.make(findViewById(R.id.fab), message, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        textView.setMaxLines(2);
        snackbar.show();


        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSnackbarOkClicked(view);

            }
        });
        snackbar.show();
    }

    private void onSnackbarOkClicked(View view) {
        presenter.onGetCity(getString(R.string.url_city));
    }

    public void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private void showTextSnackbar(String s) {
        snackbar = Snackbar
                .make(findViewById(R.id.fab), s, Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setText(s);

        textView.setMaxLines(2);

        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();

            }
        });
        snackbar.show();
    }

    private void saveBrandToDatabase(List<CityModel> list) throws SQLException {
        try {

            mDAOCity = BaseDatabaseHelper.getBaseInstance().getHelper(HomeActivity.this).getmDAOCity();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (mDAOCity != null) {
            BaseDatabaseHelper.getBaseInstance().clearCityTable();


            for (CityModel model : list) {
                try {
                    int id = mDAOCity.create(model);

                    cityList = mDAOCity
                            .queryBuilder()
                            .where()
                            .eq(CityModel.FIELD_ID, model.getID())
                            .query();
                    Log.d(TAG, "CityList:" + new Gson().toJson(cityList));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }


    }


    @Override
    public void onCityItemClicked(int position, CityModel cityModel) {
        cityModel.setSelected(true);
        Prefs.putInt(CommonMethod.SELECTED_CITY, cityModel.getID());
        cityAdapter.notifyDataSetChanged();

    }
}

