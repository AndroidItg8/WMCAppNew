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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common_method.BaseActivity;
import itg8.com.wmcapp.common_method.CommonMethod;
import itg8.com.wmcapp.complaint.ComplaintFragment;
import itg8.com.wmcapp.signup.SignUpFragment;
import itg8.com.wmcapp.torisum.TorisumFragment;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        Fragment fragment= null;
       switch (item.getItemId())
       {
           case R.id.nav_notice_board:
               break;
           case R.id.nav_complaint:
               fragment = ComplaintFragment.newInstance("","");
               break;
           case R.id.nav_tourism:
               fragment = TorisumFragment.newInstance("","");
               break;
           case R.id.nav_news:
               break;
           case R.id.nav_ward:
               break;
           case R.id.nav_praphag:
               break;
           case R.id.nav_ward_member:
               break;
           case R.id.nav_registration:
               break;
           case R.id.nav_profile:
               fragment = ComplaintFragment.newInstance("","");
               break;
           case R.id.nav_forget_pswd:
               break;
           case R.id.nav_change_password:
               break;
           case R.id.nav_suggestion:
               break;
           case R.id.nav_feedback:
               break;
           case R.id.nav_emgs_no:
               break;
           case R.id.nav_contact:
               fragment = SignUpFragment.newInstance("","");
               break;
       }
       if(fragment!= null) {
          callFragment(fragment);
       }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
