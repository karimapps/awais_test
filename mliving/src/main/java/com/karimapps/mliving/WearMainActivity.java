package com.karimapps.mliving;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

public class WearMainActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wear_activity_wear_main);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();

        FirebaseApp.initializeApp(this);

//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        String refreshedTokens = FirebaseInstanceId.getInstance().getToken();
//        String refreshedTokenss = FirebaseInstanceId.getInstance().getToken();
//
//        String vvv = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());
//        String vvvs = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());
//        String vvvss = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());
    }
}
