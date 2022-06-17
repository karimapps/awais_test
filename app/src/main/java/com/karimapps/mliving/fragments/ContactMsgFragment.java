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

public class ContactMsgFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View fragment_view;
    private Button btn_back, btn_health, btn_food, btn_sleep, btn_fasting, btn_motivation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragment_view = inflater.inflate(R.layout.contact_messages_fragemnt, container, false);
        linkViews();

        return fragment_view;
    }

    private void linkViews() {
        btn_back = fragment_view.findViewById(R.id.btn_back);
//        btn_food = fragment_view.findViewById(R.id.btn_food);
//        btn_sleep = fragment_view.findViewById(R.id.btn_sleep);
//        btn_fasting = fragment_view.findViewById(R.id.btn_fasting);
//        btn_motivation = fragment_view.findViewById(R.id.btn_motivation);
//        btn_health = fragment_view.findViewById(R.id.btn_health);
//
        btn_back.setOnClickListener(this);
//        btn_health.setOnClickListener(this);
//        btn_food.setOnClickListener(this);
//        btn_sleep.setOnClickListener(this);
//        btn_fasting.setOnClickListener(this);
//        btn_motivation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_back:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new MessagesFragment();
                fragmentTransaction.replace(R.id.maincontainer, fragment);
                fragmentTransaction.commit();
                break;
//            case R.id.btn_health:
//                break;
//            case R.id.btn_food:
//                break;
//            case R.id.btn_sleep:
//                break;
//            case R.id.btn_fasting:
//                break;
//            case R.id.btn_motivation:
//                break;

        }

    }
}