package itg8.com.wmcapp.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.board.NoticeBoardFragment;
import itg8.com.wmcapp.common.BaseActivity;
import itg8.com.wmcapp.common.CallType;
import itg8.com.wmcapp.common.CommonCallback;
import itg8.com.wmcapp.common.CustomDialogFragment;
import itg8.com.wmcapp.complaint.ComplaintFragment;
import itg8.com.wmcapp.prabhag.PrabhagFragment;
import itg8.com.wmcapp.prabhag.WardMemberFragment;
import itg8.com.wmcapp.prabhag.dummy.DummyContent;
import itg8.com.wmcapp.prabhag.model.ContactModel;
import itg8.com.wmcapp.signup.SignUpFragment;
import itg8.com.wmcapp.torisum.TorisumFragment;

import static itg8.com.wmcapp.common.CallType.PRABHAG;
import static itg8.com.wmcapp.common.CallType.WARD;
import static itg8.com.wmcapp.common.CallType.WARD_MEMBER;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,CommonCallback.OnImagePickListener,
        CustomDialogFragment.DialogItemClickListener,
        PrabhagFragment.OnListFragmentInteractionListener {

    private CallType isFrom;
    private ComplaintFragment fragment;
    CommonCallback.OnDialogClickListner listner;
    String[] items = {"Pick From Camera", "Pick From File"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
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
                break;

            case R.id.nav_praphag:
                isFrom = PRABHAG;
               fragment= PrabhagFragment.newInstance(1);
                break;

            case R.id.nav_profile:
                fragment = ComplaintFragment.newInstance("", "");
                break;

            case R.id.nav_suggestion:
                break;
            case R.id.nav_feedback:
                break;
            case R.id.nav_emgs_no:
                break;
            case R.id.nav_contact:
                fragment = SignUpFragment.newInstance("", "");
                break;
        }
        if (fragment != null) {
            callFragment(fragment);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showWard() {
        isFrom = WARD;
        callFragment(PrabhagFragment.newInstance(1));
    }

    private void showWardMemberDetail() {
        isFrom=WARD_MEMBER;
        callFragment( WardMemberFragment.newInstance(getDummyContacts()));
    }



    public List<ContactModel> getDummyContacts() {
        List<ContactModel> models=new ArrayList<>();
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
        }else if(isFrom == WARD){
            showWardMemberDetail();
        }
    }
}

