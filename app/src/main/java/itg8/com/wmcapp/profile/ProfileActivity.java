package itg8.com.wmcapp.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.cilty.CityFragment;
import itg8.com.wmcapp.cilty.model.CityModel;
import itg8.com.wmcapp.common.BaseActivity;
import itg8.com.wmcapp.common.CommonCallback;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.common.CustomDialogFragment;
import itg8.com.wmcapp.complaint.ComplaintVoteFragment;
import itg8.com.wmcapp.complaint.UnSendComplaintFragment;
import itg8.com.wmcapp.home.HomeActivity;
import itg8.com.wmcapp.notification.NotificationFragment;
import itg8.com.wmcapp.signup.LoginFragment;

public class ProfileActivity extends BaseActivity implements LoginFragment.OnAttachActivityListener, CityFragment.OnFragmentInteractionListener, CommonCallback.OnImagePickListener, CustomDialogFragment.DialogItemClickListener, ProfileFragment.ActivityFinishListener , CommonMethod.onSetToolbarTitle{


    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    String[] items = {"Pick From Camera", "Pick From File"};


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    String from;
    private Fragment fragment = null;
    private CommonCallback.OnDialogClickListner listner;
    private CityModel cityModel;
    private String lastFrom="";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    fragment = ProfileFragment.newInstance("", "");
                    from = getString(R.string.profile);
                    break;
                case R.id.navigation_complaint:
                    fragment = UnSendComplaintFragment.newInstance("", "");
                    from = getString(R.string.complaint_profile);

                    break;
                case R.id.navigation_notifications:
                    fragment = NotificationFragment.newInstance("", "");
                    from = getString(R.string.title_notifications);
                    break;
                case R.id.navigation_vote:
                    fragment = ComplaintVoteFragment.newInstance("", "");
                    from = getString(R.string.title_vote);
                    break;
            }
            if (fragment != null) {
                if(!Objects.equals(lastFrom, from)) {
                    callFragmentWithoutStack(fragment);
                    toolbar.setTitle(from);
                    lastFrom=from;
                    return true;
                }
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        lastFrom = getString(R.string.profile);
//        if (fragment != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(from);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            ft.replace(R.id.frame_container, ProfileFragment.newInstance("", ""));
//            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
//            callFragment(fragment);
//        }
        if (getIntent().getBooleanExtra(CommonMethod.FROM_FIRST_TIME_LOGIN, false)) {
            navigation.setVisibility(View.GONE);
        } else {
            navigation.setVisibility(View.VISIBLE);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onAttachActivity() {
        finish();
    }


    @Override
    public void onFragmentInteraction() {
        onBackPressed();


    }


    public void setDialogCallbackListener(CommonCallback.OnDialogClickListner callbacks) {
        listner = callbacks;
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
    public void onActivityFinish() {
        finish();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSetTitle(String name) {
        toolbar.setTitle(getString(R.string.profile));
    }
}
