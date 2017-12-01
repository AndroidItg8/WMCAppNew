package itg8.com.wmcapp.signup;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseActivity;
import itg8.com.wmcapp.common.CommonMethod;
import itg8.com.wmcapp.registration.RegistrationFragment;
import itg8.com.wmcapp.signup.adapter.LoginViewPagerAdapter;


public class LoginActivity extends BaseActivity implements LoginFragment.OnAttachActivityListener,   RegistrationFragment.OnAttachRegistrationListener ,
        CommonMethod.onSetToolbarTitle {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_Layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        init();

    }

    @Override
    public void onAttachActivity() {
        finish();
    }

    private void init() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        LoginViewPagerAdapter adapter = new LoginViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new RegistrationFragment(), "Registration");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onAttachFragmnet() {
//      onBackPressed();
        init();

    }

    @Override
    public void onSetTitle(String name) {
        toolbar.setTitle(name);
    }


}
