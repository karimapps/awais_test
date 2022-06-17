package com.karimapps.mliving.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog {
    static ProgressDialog progressDialog;

    public static void intialize(Context context, boolean cancelable){
        progressDialog =new ProgressDialog(context);
        progressDialog.setCancelable(cancelable);
    }

    public static void show(String message){
        if (progressDialog!=null) {
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }
    public static void dismiss(){
        if (progressDialog!=null) {
            progressDialog.dismiss();
        }
    }
    public static void startAnim(AVLoadingIndicatorView avi){
        avi.setVisibility(View.VISIBLE);
        avi.show();
        // or avi.smoothToShow();
    }
    public static void stopAnim(AVLoadingIndicatorView avi){
        avi.setVisibility(View.GONE);
        avi.hide();
        // or avi.smoothToShow();
    }

}
