package com.karimapps.mliving.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.karimapps.mliving.R;
import com.karimapps.mliving.ui.home.HomeViewModel;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View fragment_view;
    private Button btn_fitness, btn_health, btn_food, btn_sleep, btn_fasting, btn_motivation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragment_view = inflater.inflate(R.layout.home_fragemnt, container, false);
        linkViews();

        return fragment_view;
    }

    private void linkViews() {
        btn_fitness = fragment_view.findViewById(R.id.btn_fitness);
        btn_food = fragment_view.findViewById(R.id.btn_food);
        btn_sleep = fragment_view.findViewById(R.id.btn_sleep);
        btn_fasting = fragment_view.findViewById(R.id.btn_fasting);
        btn_motivation = fragment_view.findViewById(R.id.btn_motivation);
        btn_health = fragment_view.findViewById(R.id.btn_health);

        btn_fitness.setOnClickListener(this);
        btn_health.setOnClickListener(this);
        btn_food.setOnClickListener(this);
        btn_sleep.setOnClickListener(this);
        btn_fasting.setOnClickListener(this);
        btn_motivation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_fitness:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new FitnessFragment();
                fragmentTransaction.replace(R.id.maincontainer, fragment);
                fragmentTransaction.commit();

                break;
            case R.id.btn_health:
                FragmentTransaction fthealth = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmenth = new HealthFragment();
                fthealth.replace(R.id.maincontainer, fragmenth);
                fthealth.commit();

                break;
            case R.id.btn_food:
                FragmentTransaction fthealthf = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmentf = new FoodFragment();
                fthealthf.replace(R.id.maincontainer, fragmentf);
                fthealthf.commit();
                break;
            case R.id.btn_sleep:
                FragmentTransaction fthealts = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragments = new SleepFragment();
                fthealts.replace(R.id.maincontainer, fragments);
                fthealts.commit();
                break;
            case R.id.btn_fasting:
                FragmentTransaction fthealtfa = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmentfa = new FastingFragment();
                fthealtfa.replace(R.id.maincontainer, fragmentfa);
                fthealtfa.commit();
                break;
            case R.id.btn_motivation:
                FragmentTransaction fthealtm = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragmentm = new MotivationFragment();
                fthealtm.replace(R.id.maincontainer, fragmentm);
                fthealtm.commit();
                break;

        }

    }
}