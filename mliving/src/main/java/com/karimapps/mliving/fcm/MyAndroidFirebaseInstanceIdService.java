//package com.karimapps.mwear.mliving.fcm;
//
///**
// * Created by Shahzad Ahmad on 11/24/2016.
// */
//
//import android.util.Log;
//
//
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceId;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {
//
//    private static final String TAG = "MyAndroidFCMIIDService";
//
//
//    @Override
//    public void onTokenRefresh() {
//        //Get hold of the registration token
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        //Log the token
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//        Utills.savePreferences("token",refreshedToken,getApplicationContext());
//        Utills.savePreferences(Utills.TOKEN,refreshedToken,getApplicationContext());
//        sendRegistrationToServer(refreshedToken);
//
//    }
//    private void sendRegistrationToServer(String token) {
//        //Implement this method if you want to store the token on your server
//
//        String url = Utills.FCMDevices_Url;
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("imei", "");
//        params.put("token", token);
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                url, new JSONObject(params)
//                , new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("Resposnse", response.toString());
//                Utills.savePreferences("isFailed","0",getApplicationContext());
////                Utills.savePreferences("send_token", token, context);
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Error", "Error: " + error.getMessage());
//                Utills.savePreferences("isFailed","1",getApplicationContext());
//
//            }
//        });
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//
//    }
//}
