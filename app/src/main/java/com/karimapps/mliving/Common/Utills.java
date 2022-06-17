package com.karimapps.mliving.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.karimapps.mliving.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;



/**
 * Created by user on 13-Oct-16.
 */

public class Utills {


    public static boolean isAds_enable = true;
    public static int adsCountShow = 0;
    public static int isComingfromback = 0;
    public static Bitmap thumbnail;
    //    public static String ServerMain="http://10.0.2.2/";
//    public static String ServerMain="http://192.168.78.110/";
    public static String ServerMain = "http://forextrendeals.com/";
    public static String Server = ServerMain + "RubiksRace/public/api/";
    public static String Upload_New_Game = Server + "game/addentry";
    public static String Submit_Score = Server + "leaderboard/addentry";
    public static String Load_Leaderboard = Server + "leaderboard";
    public static String Last_Save_Game = Server + "game";
    public static String FCMDevices_Url = Server + "fcmdevices/addentry";
    public static String leaderDate = "";
    public static String leaderDate1 = "";

    public static String FB_ADS_Dashboard_banner = "185499788664062_206587616555279";
    public static String FB_ADS_Game_banner = "185499788664062_206651536548887";
    public static String FB_ADS_Chat_banner = "185499788664062_206603439887030";
    public static String FB_ADS_LeaderBoard_banner = "185499788664062_206603346553706";
    public static String FB_ADS_Complete_Game_banner = "185499788664062_206651733215534";

    public static String FB_ADS_Game_interstitial = "185499788664062_206652309882143";
    public static String FB_ADS_Complete_Game_interstitial = "185499788664062_206652536548787";

    public static String FB_ADS_Complete_Game_native = "185499788664062_206652669882107";
    public static String FIREBASE_APP_ID="AAAA8Y-cX50:APA91bEtaf6x-AEZwt0w6xC1pSYjlEdwaY_nki6FMux8zQW9xI8ar8bSN4NJ0BnVIARzK9c01g09dfOmo0XKb28vLDHhTWhiAmB9KpvgmAljtkzoWjADKA8mmgxt3lRjEL7N_tPRlu1z";


    public static String TOKEN = "firebase_token";
    public static String NAME = "name";
    public static String MYUSERNAME = "username";
    public static String EMAIL = "email";
    public static String CLUE = "clueString";
    public static String PUZZLE = "puzzleString";

    public static String PLAYER_USERNAME = "player_username";

    public static String GAME_KEY = "game_key";

    public static String TOKEN2 = "palyer2_token";
    public static String NAME2 = "palyer2_name";

    public static String NEW_VIEW_kEY = "new_view";
    public static String TOTAL_WINS_KEY = "scores";
    public static String TOTAL_LOSS_KEY = "loss";

    public static int width = 62;
    public static int height = 62;
    public static int widthclue = 105;
    public static int heightclue = 105;

    public static int gvheight = 50;
    public static int gvheight2 = 50;
    public static String UserName = "";
    public static ArrayList<Integer> PuzzleGrid = new ArrayList<>();
    public static ArrayList<Integer> secondPlayerList = new ArrayList<>();
    public static ArrayList<Integer> PuzzleGrid2 = new ArrayList<>();
    public static ArrayList<Integer> PuzzleGridClues = new ArrayList<>();


    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
    public static SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

    public static int blankCounter, blueCounter, greenCounter, orangeCounter, redCounter, yellowCounter, whiteCounter = 0;




    public int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public int pxToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = sharedPreferences.getString(key, "");
        return userName;
    }



    public static boolean savePreferences(String key, String value,
                                          Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;
    }


    public static int getPreferencesInt(String key, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userName = sharedPreferences.getInt(key, 0);
        return userName;
    }

    public static boolean savePreferencesInt(String key, int value,
                                             Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static void showLog(String key, String message) {
        Log.d(key, message);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }



    public static String[] SplitName(String fullname) {
        String[] separated = fullname.split(" ");
        return separated;
    }

    public static String SplitEmail(String mEmail) {
        String username = "";
        String email_name[] = mEmail.split("@");

        if (email_name.length > 1) {
            username = email_name[0];
        }
        return username;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static void shareAppLink(Context context) {
        try {
            String appPackageName = context.getPackageName();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
            String sAux = "\nLet me recommend you this Game\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + appPackageName + " \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Share link!"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public static void shareAppData(Context context, String message) {
        try {
            String appPackageName = context.getPackageName();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
            String sAux = message + "\n\n\nShared by this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + appPackageName + " \n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "Share link!"));
        } catch (Exception e) {
            //e.toString();
        }
    }



    public static String generateCacheKeyWithParam(String url, Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            url += entry.getKey() + "=" + entry.getValue();
        }
        return url;
    }
}
