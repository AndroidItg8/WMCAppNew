package itg8.com.wmcapp.setting;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import android.support.v7.preference.PreferenceManager;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.Unbinder;
import itg8.com.wmcapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrefsSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrefsSettingFragment extends PreferenceFragmentCompat {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PrefsSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrefsSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrefsSettingFragment newInstance(String param1, String param2) {
        PrefsSettingFragment fragment = new PrefsSettingFragment();
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
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_preferences);
        Preference myPref = findPreference(getString(R.string.pref_security_key));
//        bindPreferenceSummaryToValue()
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SecurityFragment fragment = SecurityFragment.newInstance("", "");
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                ft.replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName());
                ft.commit();
                return false;
            }
        });


//         final Preference noticePref = findPreference(getString(R.string.notice_pref));
//         noticePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//             @Override
//             public boolean onPreferenceChange(Preference preference, Object newValue) {
//                 noticePref.setEnabled(true);
//                 return true;
//             }
//         });


    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_prefs_setting, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }


    private static void bindPreferenceSummaryToValue(Preference preference) {
//        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
//
//        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
//                PreferenceManager
//                        .getDefaultSharedPreferences(preference.getContext())
//                        .getString(preference.getKey(), ""));
////    }
    }
}
