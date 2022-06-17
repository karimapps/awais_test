package com.karimapps.mliving.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karimapps.mliving.MainActivity;
import com.karimapps.mliving.R;


public class Splash extends AppCompatActivity {

    RelativeLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        String refreshedTokens = FirebaseInstanceId.getInstance().getToken();
        String refreshedTokenss = FirebaseInstanceId.getInstance().getToken();

        String vvv = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());
        String vvvs = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());
        String vvvss = String.valueOf(FirebaseInstanceId.getInstance().getInstanceId());


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash.this, LoginRegisterActivity.class));
                    finish();
                }


            }
        }, 3000);

//


    }


}