package com.karimapps.mliving.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.karimapps.mliving.R;
import com.karimapps.mliving.activities.LoginRegisterActivity;
import com.karimapps.mliving.ui.home.HomeViewModel;

public class MoreFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View fragment_view;
    private Button btn_settings, btn_about, btn_policy, btn_terms, btn_help, btn_logout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragment_view = inflater.inflate(R.layout.more_fragemnt, container, false);
        linkViews();

        return fragment_view;
    }

    private void linkViews() {
        btn_settings = fragment_view.findViewById(R.id.btn_settings);
        btn_policy = fragment_view.findViewById(R.id.btn_policy);
        btn_terms = fragment_view.findViewById(R.id.btn_terms);
        btn_help = fragment_view.findViewById(R.id.btn_help);
        btn_logout = fragment_view.findViewById(R.id.btn_logout);
        btn_about = fragment_view.findViewById(R.id.btn_about);

        btn_settings.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_policy.setOnClickListener(this);
        btn_terms.setOnClickListener(this);
        btn_help.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_settings:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new SettingsFragment();
                fragmentTransaction.replace(R.id.maincontainer, fragment);
                fragmentTransaction.commit();

                break;
            case R.id.btn_about:
                FragmentTransaction fthealth = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmenth = new AboutFragment();
                fthealth.replace(R.id.maincontainer, fragmenth);
                fthealth.commit();

                break;
            case R.id.btn_policy:
                FragmentTransaction fthealthf = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmentf = new PolicyMoreFragment();
                fthealthf.replace(R.id.maincontainer, fragmentf);
                fthealthf.commit();
                break;
            case R.id.btn_terms:
                FragmentTransaction fthealts = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragments = new TermsMoreFragment();
                fthealts.replace(R.id.maincontainer, fragments);
                fthealts.commit();
                break;
            case R.id.btn_help:
                FragmentTransaction fthealtfa = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmentfa = new HelpMoreFragment();
                fthealtfa.replace(R.id.maincontainer, fragmentfa);
                fthealtfa.commit();
                break;
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                getActivity().finish();

                break;

        }

    }
}