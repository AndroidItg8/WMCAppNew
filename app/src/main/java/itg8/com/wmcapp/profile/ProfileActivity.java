package itg8.com.wmcapp.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.wmcapp.R;
import itg8.com.wmcapp.common.BaseActivity;
import itg8.com.wmcapp.complaint.ComplaintVoteFragment;
import itg8.com.wmcapp.complaint.UnSendComplaintFragment;
import itg8.com.wmcapp.notification.NotificationFragment;

public class ProfileActivity extends BaseActivity {


    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.container)
    RelativeLayout container;
    private Fragment fragment = null;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    fragment = ProfileFragment.newInstance("", "");
                   // toolbar.setTitle("Customer care");
                    break;
                case R.id.navigation_complaint:
                    fragment = UnSendComplaintFragment.newInstance("", "");
                    break;
                case R.id.navigation_notifications:
                    fragment = NotificationFragment.newInstance("", "");
                    break;
                case R.id.navigation_vote:
                    fragment = ComplaintVoteFragment.newInstance("", "");
                    break;
            }
            if (fragment != null) {
                callFragment(fragment);
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        fragment = ProfileFragment.newInstance("", "");
        if(fragment!= null)
        {
            callFragment(fragment);

        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
