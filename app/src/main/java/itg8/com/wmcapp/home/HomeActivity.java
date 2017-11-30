package itg8.com.wmcapp.home;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.NoticeBoardFragment;
import itg8.com.wmcapp.change_password.ChangePswdFragment;
import itg8.com.wmcapp.cilty.CityAdapter;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.cilty.mvp.CityMVP;
import itg8.com.wmcapp.common.BaseActivity;
import itg8.com.wmcapp.common.CommonCallback;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.CustomDialogFragment;
import itg8.com.wmcapp.common.Language;
import itg8.com.wmcapp.common.Logs;
import itg8.com.wmcapp.common.MyApplication;
import itg8.com.wmcapp.common.Prefs;
import itg8.com.wmcapp.complaint.ComplaintFragment;
import itg8.com.wmcapp.contact.ContactUsFragment;
import itg8.com.wmcapp.database.CityTableManipulate;
import itg8.com.wmcapp.emergency.EmergencyFragment;
import itg8.com.wmcapp.feedback.FeedbackFragment;
import itg8.com.wmcapp.news.NewsFragment;
import itg8.com.wmcapp.prabhag.PrabhagFragment;
import itg8.com.wmcapp.profile.ProfileActivity;
import itg8.com.wmcapp.profile.ProfileModel;
import itg8.com.wmcapp.signup.LoginActivity;
import itg8.com.wmcapp.suggestion.SuggestionFragment;
import itg8.com.wmcapp.torisum.TorisumFragment;
import itg8.com.wmcapp.widget.CircularImageView;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CommonCallback.OnImagePickListener,
        CustomDialogFragment.DialogItemClickListener,
        CommonMethod.OnBackPressListener,
        CommonMethod.OnMoveComplaintListener,
        CityMVP.CityView, CityAdapter.CityItemClickedListener, CommonMethod.onSetToolbarTitle {
    //   PrabhagFragment.onPrabhagClickedListener,
    private static final String TAG = HomeActivity.class.getSimpleName();

    private static int english = 0;
    private static int hindi = 1;
    private static int marathi = 2;
    Fragment fragment = null;
    CommonCallback.OnDialogClickListner listner;
    String[] items = {"Pick From Camera", "Pick From File"};
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    CommonMethod.ProfileSetListener listener;
    private int isFrom;
    private List<CityModel> list;
    private Snackbar snackbar;
    private CityMVP.CityPresenter presenter;
    private CityTableManipulate mDAOCity = null;
    private List<CityModel> cityList = null;
    private Language langauge = null;
    private CityAdapter cityAdapter;
    private NoticeBoardFragment fragments;
    private TextView lblName;
    private TextView lblMobile;
    private CircularImageView imageView;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle toggle;
    private int mItemToOpenWhenDrawerCloses = -1;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        MyApplication.getInstance().getDeletedNBList();
        int result = CommonMethod.calculateTerm();
        Logs.d("Result" + result);
        checkLogin();
//        startActivity(new Intent(this, TestActivity.class));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDAOCity = new CityTableManipulate(this);


        setDrawerToggle();


        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View view = navigationView.getHeaderView(0);
        if (view != null) {
            lblName = view.findViewById(R.id.lbl_header_name);
            lblMobile = view.findViewById(R.id.lbl_header_mobile);
            imageView = view.findViewById(R.id.imageView);
        }
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_registration).setVisible(false);
        navigationView.setNavigationItemSelectedListener(this);
        lblName.setText(Prefs.getString(CommonMethod.USER_NAME));
        lblMobile.setText(Prefs.getString(CommonMethod.USER_MOBILE));
        {
            MyApplication.getInstance().getProfile(new CommonMethod.ProfileSetListener() {
                @Override
                public void onSetProfile(ProfileModel model) {
                    if (model != null) {
                        Picasso.with(getApplicationContext()).load(CommonMethod.BASE_URL + model.getPicProfle()).into(imageView);
                        Prefs.putString(CommonMethod.USER_NAME, model.getFullName());
                        Prefs.putString(CommonMethod.USER_MOBILE, model.getContactNumber());
                        lblName.setText(Prefs.getString(CommonMethod.USER_NAME));
                        lblMobile.setText(Prefs.getString(CommonMethod.USER_MOBILE));
                    }
                }

                @Override
                public void onFailed(String s) {


                }
            });
        }
        fragment = HomeFragment.newInstance("", "");
        callFragmentWithoutStack(fragment);
    }

    private void setDrawerToggle() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {

            toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            populateDrawerItems(navigationView);
            toggle.setDrawerIndicatorEnabled(true);
            setSupportActionBar(toolbar);
            setDrawerUpdateToggle();
        } else {
            setSupportActionBar(toolbar);
        }

    }

    private void checkLogin() {
        if (Prefs.getString(CommonMethod.HEADER) == null) {
            callLoginActivity();
            finish();
        } else {


        }
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
                CityTableManipulate cityTableManipulate = new CityTableManipulate(this);
                openBottomSheetForCity(cityTableManipulate.getAll());
                break;
            case android.R.id.home:
                Logs.d("Home Clicked");
                onBackPressed();
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
        recyclerView = view.findViewById(R.id.recyclerView);
        FloatingActionButton btnDismiss = view.findViewById(R.id.btn_dismiss);
        EditText edtSearch = view.findViewById(R.id.edt_search);


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
        CityTableManipulate cityTableManipulate = new CityTableManipulate(this);
        this.list = cityTableManipulate.getAll();
        initCityRecyclerView(cityTableManipulate.getAll());
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();

    }

    private void initCityRecyclerView(List<CityModel> filteredModelList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cityAdapter = new CityAdapter(getApplicationContext(), filteredModelList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(cityAdapter);
    }

    private boolean onQueryTextChange(String s) {
        final List<CityModel> filteredModelList = filter(list, s);
        initCityRecyclerView(filteredModelList);
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
        String from = null;
        switch (item.getItemId()) {
            case R.id.nav_notice_board:
                from = "NoticeBoard";
                fragment = NoticeBoardFragment.newInstance("", "");
                break;
            case R.id.nav_complaint:
                from = "Complaint";
                fragment = ComplaintFragment.newInstance("", "");
                break;
            case R.id.nav_tourism:
                from = "Tourism";
                fragment = TorisumFragment.newInstance("", "");
                break;
            case R.id.nav_news:
                from = "News and Event";
                fragment = NewsFragment.newInstance("", "");
                break;
            case R.id.nav_praphag:
                from = "Prabhag";
                isFrom = CommonMethod.PRABHAG;
                fragment = PrabhagFragment.newInstance(isFrom);
                break;
            case R.id.nav_profile:
                from = "Profile";
                // fragment = ProfileFragment.newInstance("", "");
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
            case R.id.nav_suggestion:
                from = "Suggestion";
                fragment = SuggestionFragment.newInstance("", "");
                break;
            case R.id.nav_feedback:
                from = "Feedback";
                fragment = FeedbackFragment.newInstance("", "");
                break;
            case R.id.nav_emgs_no:
                from = "Emergency";
                fragment = EmergencyFragment.newInstance("", "");
                break;
            case R.id.nav_contact:
                from = "Contact";
                fragment = ContactUsFragment.newInstance("", "");
                break;
            case R.id.nav_change_pswd:
                from = "Change Password";
                fragment = ChangePswdFragment.newInstance("", "");
                break;
            case R.id.nav_registration:
                from = "Registration";
                callLoginActivity();
                break;
            case R.id.nav_logout:
                clearNLogout();
                break;
        }
        if (fragment != null) {
            callFragment(fragment);
            toolbar.setTitle(from);
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
        Prefs.remove(CommonMethod.USER_NAME);
        Prefs.remove(CommonMethod.USER_MOBILE);
        FirebaseAuth.getInstance().signOut();
        Log.d(TAG, "Firebase User:" + FirebaseAuth.getInstance().getCurrentUser());

        callLoginActivity();
        MyApplication.getInstance().deleteNoticeBoard();
        MyApplication.getInstance().setProfileModel(null);
        finish();


    }

    private void showWard() {
        isFrom = CommonMethod.WARD;
        callFragment(PrabhagFragment.newInstance(isFrom));
    }

    private void showWardMemberDetail() {
        isFrom = CommonMethod.WARD_MEMEBER;
//        callFragment(WardMemberFragment.newInstance(prabhagModel.getWardList().get(position).getMemberList()));
    }


    @Override
    public void onBackPressed() {
      drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        Logs.d(" Activity onBackPressed");
        toolbar.setTitle(getString(R.string.app_name));
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            // Lastly, it will rely on the system behavior for back
            // super.onBackPressed();


           // super.onBackPressed();
        }
        if(fragment instanceof HomeFragment)
        {
            setDrawerToggle();
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
    public void onGetCityList(final List<CityModel> list) {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
//                storeCity(list);
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Logs.d("Stored in db");
                        } else {
                            Logs.d("Fail to store in db");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        try {
//            saveBrandToDatabase(list);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        this.cityList = list;


    }

    private boolean storeCity(List<CityModel> list) {
        try {
            saveBrandToDatabase(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        mDAOCity.deleteAll();

        if (mDAOCity != null) {
            for (CityModel model : list) {
                int id = mDAOCity.create(model);
                //   Log.d(TAG, "CityId:" + id);
            }

        }
    }


    @Override
    public void onCityItemClicked(int position, CityModel cityModel) {
        cityModel.setSelected(true);
        Prefs.putInt(CommonMethod.SELECTED_CITY, cityModel.getID());
        cityAdapter.notifyDataSetChanged();

    }

    public void setDialogCallbackListener(CommonCallback.OnDialogClickListner callbacks) {
        listner = callbacks;

    }

    @Override
    public void onBackPress() {
        onBackPressed();
    }

    @Override
    public void moveComplaint() {
        callFragmentWithoutStack(ComplaintFragment.newInstance("", ""));
    }

    @Override
    public void onSetTitle(String name) {
        toolbar.setTitle(name);
    }

    @Override
    public void setDrawer() {
        setDrawerUpdateToggle();
    }

    private void setDrawerUpdateToggle() {
        if (toggle == null) {
            return;
        }
        boolean isRoot = getSupportFragmentManager().getBackStackEntryCount() == 0;
        Logs.d("Root:" + isRoot);
        Logs.d("getFragmentManager().getBackStackEntryCount():" + getFragmentManager().getBackStackEntryCount());
        toggle.setDrawerIndicatorEnabled(isRoot);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(!isRoot);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!isRoot);
            getSupportActionBar().setHomeButtonEnabled(!isRoot);
        }
        if (isRoot) {
            toggle.syncState();
        }
    }

    private final DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerClosed(View drawerView) {
            if (toggle != null) toggle.onDrawerClosed(drawerView);
            if (mItemToOpenWhenDrawerCloses >= 0) {


                Class activityClass = null;
                switch (mItemToOpenWhenDrawerCloses) {
//                    case R.id.navigation_allmusic:
//                        activityClass = MusicPlayerActivity.class;
//                        break;
//                    case R.id.navigation_playlists:
//                        activityClass = PlaceholderActivity.class;
//                        break;
//                }
//                if (activityClass != null) {
//                    startActivity(new Intent(ActionBarCastActivity.this, activityClass), extras);
//                    finish();
//                }
                }
            }
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if (toggle != null) toggle.onDrawerStateChanged(newState);
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            if (toggle != null) toggle.onDrawerSlide(drawerView, slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            if (toggle != null) toggle.onDrawerOpened(drawerView);
            if (getSupportActionBar() != null) getSupportActionBar()
                    .setTitle(R.string.app_name);
        }
    };

    private final FragmentManager.OnBackStackChangedListener mBackStackChangedListener =
            new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    setDrawerUpdateToggle();
                }
            };





    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (toggle != null) {
            toggle.syncState();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        if (mCastContext != null) {
//            mCastContext.addCastStateListener(mCastStateListener);
//        }
//
//        // Whenever the fragment back stack changes, we may need to update the
//        // action bar toggle: only top level screens show the hamburger-like icon, inner
//        // screens - either Activities or fragments - show the "Up" icon instead.
//        getFragmentManager().addOnBackStackChangedListener(mBackStackChangedListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (toggle != null) {
            toggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

//        if (mCastContext != null) {
//            mCastContext.removeCastStateListener(mCastStateListener);
//        }
//        getFragmentManager().removeOnBackStackChangedListener(mBackStackChangedListener);
//    }
//}
    //    @Override
//    public void onPrabhagSelected() {
//        if (isFrom == PRABHAG) {
//            showWard();
//        }else if(isFrom == WARD){
//            showWardMemberDetail();
//        }
//    }
//    private final FragmentManager.OnBackStackChangedListener mBackStackChangedListener =
//            new FragmentManager.OnBackStackChangedListener() {
//                @Override
//                public void onBackStackChanged() {
//                    setDrawerUpdateToggle();
//                }
//            };







    private void populateDrawerItems(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mItemToOpenWhenDrawerCloses = menuItem.getItemId();
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }

}










