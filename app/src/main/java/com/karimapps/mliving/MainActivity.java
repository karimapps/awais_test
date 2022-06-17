package com.karimapps.mliving;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karimapps.mliving.Common.Constants;
import com.karimapps.mliving.Common.Utills;
import com.karimapps.mliving.fragments.HomeFragment;
import com.karimapps.mliving.fragments.MessagesFragment;
import com.karimapps.mliving.fragments.MoreFragment;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout llhome, llmessages, llmore;
    private Context context;
    Timer timer;
    TimerTask doAsynchronousTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context = this;


//        ScheduledExecutorService scheduler =
//                Executors.newSingleThreadScheduledExecutor();
//
//        scheduler.scheduleAtFixedRate
//                (new Runnable() {
//                    public void run() {
//                        Log.d("timer","timer si running********");
//                    }
//                }, 0, 2, TimeUnit.SECONDS);

//        linkViews();

 startTimerTask();

    }
    private void startTimerTask() {
        final Handler handler = new Handler();
        timer = new Timer();
        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        Log.d("timer","timer is running********");

                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000);
    }

    private void linkViews() {

        llhome = findViewById(R.id.llhome);
        llmessages = findViewById(R.id.llmesages);
        llmore = findViewById(R.id.llmore);


        llhome.setOnClickListener(this);
        llmessages.setOnClickListener(this);
        llmore.setOnClickListener(this);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.maincontainer, fragment);
        fragmentTransaction.commit();

        loadReminders();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llhome:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = new HomeFragment();
                fragmentTransaction.replace(R.id.maincontainer, fragment);
                fragmentTransaction.commit();
                break;
            case R.id.llmesages:
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                Fragment fragment2 = new MessagesFragment();
                fragmentTransaction2.replace(R.id.maincontainer, fragment2);
                fragmentTransaction2.commit();
                break;
            case R.id.llmore:
                FragmentTransaction fragmentTransaction23 = getSupportFragmentManager().beginTransaction();
                Fragment fragment23 = new MoreFragment();
                fragmentTransaction23.replace(R.id.maincontainer, fragment23);
                fragmentTransaction23.commit();
                break;

        }
    }

    private void loadReminders() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference imagesRef = rootRef.child("reminder");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    String url = ds.getValue(String.class);
//                    Log.d("TAG", url);
                    String cat_id = ds.getKey();
                    String sw_dentist = dataSnapshot.child("sw_dentist").getValue(String.class);
                    String sw_eyetest = dataSnapshot.child("sw_eyetest").getValue(String.class);
                    String sw_freashair = dataSnapshot.child("sw_freashair").getValue(String.class);
                    String sw_hearing_test = dataSnapshot.child("sw_hearing_test").getValue(String.class);
                    String sw_plank = dataSnapshot.child("sw_plank").getValue(String.class);
                    String sw_pushup = dataSnapshot.child("sw_pushup").getValue(String.class);
                    String sw_squat = dataSnapshot.child("sw_squat").getValue(String.class);
                    String sw_walk = dataSnapshot.child("sw_walk").getValue(String.class);


                    Utills.savePreferences(Constants.sw_dentist, sw_dentist, context);
                    Utills.savePreferences(Constants.sw_eyetest, sw_eyetest, context);
                    Utills.savePreferences(Constants.sw_freashair, sw_freashair, context);
                    Utills.savePreferences(Constants.sw_hearing_test, sw_hearing_test, context);
                    Utills.savePreferences(Constants.sw_plank, sw_plank, context);
                    Utills.savePreferences(Constants.sw_pushup, sw_pushup, context);
                    Utills.savePreferences(Constants.sw_squat, sw_squat, context);
                    Utills.savePreferences(Constants.sw_walk, sw_walk, context);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        imagesRef.addListenerForSingleValueEvent(valueEventListener);


//        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//        Query query = mFirebaseDatabaseReference.child("cats").orderByChild("cat_name").equalTo(cat_name);
//        query.addValueEventListener(valueEventListener);
//
//        ValueEventListener valueEventListener = new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot)
//            {
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                    String url = ds.getValue(String.class);
////                    Log.d("TAG", url);
//                    String cat_id = ds.getKey();
//                    String cat_name = ds.child("cat_name").getValue(String.class);
//                    String image = ds.child("image").getValue(String.class);
//
//                    Cats ob = new Cats(cat_id, cat_name, image);
//                    catsArrayList.add(ob);
//                }
//
//                adapter = new AllCatsAdapter(getActivity(), catsArrayList);
//                rec_categories.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError)
//            {
//
//            }
//        };
    }

}
