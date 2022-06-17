package com.karimapps.mliving.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.karimapps.mliving.R;
import com.karimapps.mliving.ui.home.HomeViewModel;

public class MessagesFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View fragment_view;
    private LinearLayout llcontact, llads, llmesages, lldata;
    private Button btn_back, btn_health, btn_food, btn_sleep, btn_fasting, btn_motivation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragment_view = inflater.inflate(R.layout.messages_fragemnt, container, false);
        linkViews();

        return fragment_view;
    }

    private void linkViews() {

        llmesages = fragment_view.findViewById(R.id.llwelcome);
        llads = fragment_view.findViewById(R.id.llads);
        lldata = fragment_view.findViewById(R.id.lldata);
        llcontact = fragment_view.findViewById(R.id.llcontact);
//        btn_motivation = fragment_view.findViewById(R.id.btn_motivation);
//        btn_health = fragment_view.findViewById(R.id.btn_health);
//
        llmesages.setOnClickListener(this);
        llads.setOnClickListener(this);
        llcontact.setOnClickListener(this);
        lldata.setOnClickListener(this);
//        btn_fasting.setOnClickListener(this);
//        btn_motivation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llwelcome:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new WelcomeMsgFragment();
                fragmentTransaction.replace(R.id.maincontainer, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.llads:
                FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment2 = new AdsMsgFragment();
                fragmentTransaction2.replace(R.id.maincontainer, fragment2);
                fragmentTransaction2.commit();
                break;
            case R.id.llcontact:
                FragmentTransaction fragmentTransaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment3 = new ContactMsgFragment();
                fragmentTransaction3.replace(R.id.maincontainer, fragment3);
                fragmentTransaction3.commit();
                break;
            case R.id.lldata:
                FragmentTransaction fragmentTransaction4 = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment4 = new DataMsgFragment();
                fragmentTransaction4.replace(R.id.maincontainer, fragment4);
                fragmentTransaction4.commit();
                break;


        }

    }
}