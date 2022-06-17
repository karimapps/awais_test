package com.karimapps.mliving.fragments;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.karimapps.mliving.Common.Constants;
import com.karimapps.mliving.Common.Utills;
import com.karimapps.mliving.R;
import com.karimapps.mliving.db.AppDataBase;
import com.karimapps.mliving.reminders.AlarmService;
import com.karimapps.mliving.reminders.ReminderContract;
import com.karimapps.mliving.reminders.ReminderItem;
import com.karimapps.mliving.reminders.ReminderParams;
import com.karimapps.mliving.reminders.ReminderType;
import com.karimapps.mliving.ui.home.HomeViewModel;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FastingFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View fragment_view;
    private Button btn_back;
    private TextView tv_monday, tv_tues, tv_wed, tv_thurs, tv_fri, tv_sat, tv_sun;
    private Context context;
    private int reminder_frequency;
    private IndicatorSeekBar indicatorSeekBar;
    private Switch sw_fasting_start111, sw_fasting_end, sw_motivation_quotes;
    private Button btn_fitness, btn_health, btn_food, btn_sleep, btn_fasting, btn_motivation;


    /*alaram variables*/

//    private EditText mContent, mTitle;


    private SimpleAdapter mAdapter;

    private String mTime, mDate;
    private int mRepeatMode;
    List<Map<String, String>> mapList;
    private Map<String, String> mAlarmTime, mAlarmDate, mAlarmRepeat;
    private Calendar mAlertTime;
    private ContentResolver mContentResolver;
    private ReminderItem mData;

    private static final String NONE = "None";
    private static final String HOURLY = "Hourly";
    private static final String DAILY = "Daily";
    private static final String WEEKLY = "Weekly";
    private static final String MONTHLY = "Monthly";
    private static final String YEARLY = "Yearly";

    private static final String[] REPEAT_MODES =
            new String[]{NONE, HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY};

    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm aa", Locale.CANADA);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy", Locale.CANADA);

    private static final String ITEM_TITLE = "header";
    private static final String ITEM_CONTENT = "content";

    private static final String TIME_SETTING = "Time";
    private static final String DATE_SETTING = "Date";
    private static final String REPEAT_SETTING = "Repeat";

    private static final int TIME_POSITION = 0;
    private static final int DATE_POSITION = 1;
    private static final int REPEAT_POSITION = 2;

    String MONDAY = "mon";
    String TUES = "tues";
    String WED = "wed";
    String THU = "thu";
    String FRI = "fri";
    String SAT = "sat";
    String SUN = "sun";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragment_view = inflater.inflate(R.layout.fasting_fragemnt, container, false);
        context = getActivity();
        linkViews();

        return fragment_view;
    }

    private void linkViews() {

        tv_monday = fragment_view.findViewById(R.id.tv_monday);
        tv_tues = fragment_view.findViewById(R.id.tv_tues);
        tv_wed = fragment_view.findViewById(R.id.tv_wed);
        tv_thurs = fragment_view.findViewById(R.id.tv_thurs);
        tv_fri = fragment_view.findViewById(R.id.tv_fri);
        tv_sat = fragment_view.findViewById(R.id.tv_sat);
        tv_sun = fragment_view.findViewById(R.id.tv_sun);


        tv_monday.setOnClickListener(this);
        tv_tues.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_thurs.setOnClickListener(this);
        tv_fri.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_sun.setOnClickListener(this);


        String mon = Utills.getPreferences(Constants.KEY_MON2, context);
        String tues = Utills.getPreferences(Constants.KEY_TUES2, context);
        String wed = Utills.getPreferences(Constants.KEY_WED2, context);
        String thu = Utills.getPreferences(Constants.KEY_THU2, context);
        String fri = Utills.getPreferences(Constants.KEY_FRI2, context);
        String sat = Utills.getPreferences(Constants.KEY_SAT2, context);
        String sun = Utills.getPreferences(Constants.KEY_SUN2, context);

        /*tvs*/


        if (mon.equalsIgnoreCase("1")) {


            tv_monday.setBackgroundResource(R.drawable.circleyello);
        } else {
            tv_monday.setBackgroundResource(R.drawable.circle);
        }


        if (tues.equalsIgnoreCase("1")) {

            tv_tues.setBackgroundResource(R.drawable.circleyello);
        } else {

            tv_tues.setBackgroundResource(R.drawable.circle);
        }


        if (wed.equalsIgnoreCase("1")) {

            tv_wed.setBackgroundResource(R.drawable.circleyello);
        } else {

            tv_wed.setBackgroundResource(R.drawable.circle);
        }


        if (thu.equalsIgnoreCase("1")) {

            tv_thurs.setBackgroundResource(R.drawable.circleyello);
        } else {

            tv_thurs.setBackgroundResource(R.drawable.circle);
        }


        if (fri.equalsIgnoreCase("1")) {

            tv_fri.setBackgroundResource(R.drawable.circleyello);
        } else {

            tv_fri.setBackgroundResource(R.drawable.circle);
        }


        if (sat.equalsIgnoreCase("1")) {

            tv_sat.setBackgroundResource(R.drawable.circleyello);
        } else {

            tv_sat.setBackgroundResource(R.drawable.circle);
        }


        if (sun.equalsIgnoreCase("1")) {

            tv_sun.setBackgroundResource(R.drawable.circleyello);
        } else {

            tv_sun.setBackgroundResource(R.drawable.circle);
        }





        /*load once */

        mContentResolver = getActivity().getContentResolver();

        mapList = new ArrayList<>();
        mAlarmTime = new HashMap<>();
        mAlarmDate = new HashMap<>();
        mAlarmRepeat = new HashMap<>();


        alaramData();


        btn_back = fragment_view.findViewById(R.id.btn_back);

        indicatorSeekBar = fragment_view.findViewById(R.id.seekbar);
        sw_fasting_start111 = fragment_view.findViewById(R.id.sw_fasting_start111);
        sw_fasting_end = fragment_view.findViewById(R.id.sw_fasting_end);
        sw_motivation_quotes = fragment_view.findViewById(R.id.sw_motivation_quotes);


//        String walk = Utills.getPreferences(Constants.KEY_WALK_REMINDER, context);
//        String pushup = Utills.getPreferences(Constants.KEY_PUSHUP_REMINDER, context);
//        String freshair = Utills.getPreferences(Constants.KEY_FRESHAIR_REMINDER, context);
//        String squat = Utills.getPreferences(Constants.KEY_SQUAT_REMINDER, context);
//        String plank = Utills.getPreferences(Constants.KEY_PLANK_REMINDER, context);
        String frequency = Utills.getPreferences(Constants.KEY_FREQUENCY, context);


        AppDataBase appDataBase = new AppDataBase(context);
        appDataBase.open();
        for (int i = 0; i < appDataBase.getAllReminders().size(); i++) {

            String title = appDataBase.getAllReminders().get(i).getTitle();
            if (title.equalsIgnoreCase("sw_fasting_start111")) {
                sw_fasting_start111.setChecked(true);
            } else {
//                sw_fasting_start111.setChecked(false);
            }


            if (title.equalsIgnoreCase("sw_fasting_end")) {
                sw_fasting_end.setChecked(true);
            } else {
//                sw_fasting_end.setChecked(false);
            }

            if (title.equalsIgnoreCase("sw_fasting_end")) {
                sw_fasting_end.setChecked(true);
            } else {
//                sw_fasting_end.setChecked(false);
            }


            if (title.equalsIgnoreCase("sw_motivation_quotes")) {
                sw_motivation_quotes.setChecked(true);
            } else {
//                sw_motivation_quotes.setChecked(false);
            }


        }

        appDataBase.close();


        btn_back.setOnClickListener(this);


        if (!TextUtils.isEmpty(frequency)) {
            int frq_int = Integer.parseInt(frequency);
            Log.i("TAG", "" + frq_int);
            indicatorSeekBar.setProgress(frq_int);
        } else {
            indicatorSeekBar.setProgress(3);
        }
        reminder_frequency = 3;
        indicatorSeekBar.setProgress(reminder_frequency);


        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                Utills.savePreferences(Constants.KEY_FREQUENCY, seekParams.progress + "", context);

//                btn_back.setText(seekParams.progress + "");
                Log.i("TAG1", "" + seekParams.seekBar);
                Log.i("TAG2", "" + seekParams.progress);

                reminder_frequency = seekParams.progress;
                Log.i("TAG3", "" + seekParams.progressFloat);
                Log.i("TAG4", "" + seekParams.fromUser);
                //when tick count > 0
                Log.i("TAG5", "" + seekParams.thumbPosition);
                Log.i("TAG6", "" + seekParams.tickText);
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });

        loadReminders();

    }

    private void alaramData() {


        mRepeatMode = 0;

        Intent intent = getActivity().getIntent();
        mData = intent.getParcelableExtra("data");
        mAlertTime = Calendar.getInstance();



        /*modified */

        mData = new ReminderItem();
        Calendar current = Calendar.getInstance();
        mTime = TIME_FORMAT.format(current.getTime());
        mDate = DATE_FORMAT.format(current.getTime());
        mAlertTime.setTimeInMillis(current.getTimeInMillis());

        mData.setTimeInMillis(current.getTimeInMillis());
        mData.setFrequency(mRepeatMode);

        /*modified*/


        mAlarmTime.put(ITEM_TITLE, TIME_SETTING);
        mAlarmTime.put(ITEM_CONTENT, mTime);
        mAlarmDate.put(ITEM_TITLE, DATE_SETTING);
        mAlarmDate.put(ITEM_CONTENT, mDate);
        mAlarmRepeat.put(ITEM_TITLE, REPEAT_SETTING);
        mAlarmRepeat.put(ITEM_CONTENT, REPEAT_MODES[mRepeatMode]);

        mapList.add(mAlarmTime);
        mapList.add(mAlarmDate);
        mapList.add(mAlarmRepeat);

        mAdapter = new SimpleAdapter(getActivity(), mapList, android.R.layout.simple_list_item_2,
                new String[]{ITEM_TITLE, ITEM_CONTENT}, new int[]{
                android.R.id.text1, android.R.id.text2});

//        ListView listView = new ListView(context);

        ListView listView = fragment_view.findViewById(R.id.alert_settings);
        listView.setAdapter(mAdapter);
    }

    private void loadReminders() {

        sw_fasting_start111.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);


                setReminder(isChecked, "sw_fasting_start111", "This is Fasting start Reminder");

            }

        });


        sw_fasting_end.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                setReminder(isChecked, "sw_fasting_end", "This is Fasting end Reminder");

            }

        });

        sw_motivation_quotes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                setReminder(isChecked, "sw_motivation_quotes", "This is fasting motivational quotes");

            }

        });


    }

    private void setReminder(boolean isChecked, String sw_tilte, String reminder) {

        if (isChecked) {


            AppDataBase appDataBase = new AppDataBase(context);
            appDataBase.open();
            appDataBase.insertReminder("Alert", sw_tilte, "Walk", "123123", reminder_frequency + "");
            appDataBase.close();

            alaramData();
            mData.setTitle("Fitness");
            mData.setContent(reminder);
            mData.setFrequency(reminder_frequency);
            promptSave(mData);
//            Toast.makeText(context, "Walk Reminder is enabled", Toast.LENGTH_SHORT).show();
        } else {

            if (mData != null) {
                AppDataBase appDataBase = new AppDataBase(context);
                appDataBase.open();
                String id = appDataBase.getReminderByTitle(sw_tilte).get(0).getId();
                appDataBase.close();

                AppDataBase appDataBase2 = new AppDataBase(context);
                appDataBase2.open();
                appDataBase2.delete(id);
                appDataBase2.close();

                int id_int = Integer.parseInt(id);
                Intent delete = new Intent(context, AlarmService.class);
                delete.putExtra(ReminderParams.ID, id_int);
                delete.setAction(AlarmService.CANCEL);
                Uri uri = ContentUris.withAppendedId(ReminderContract.Notes.CONTENT_URI, id_int);
                mContentResolver.delete(uri, null, null);
                getActivity().startService(delete);
                terminateActivity();
//                Toast.makeText(context, "Walk Reminder is disabled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_back:

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new HomeFragment();
                fragmentTransaction.replace(R.id.maincontainer, fragment);
                fragmentTransaction.commit();
                break;


            /*tvs*/
            case R.id.tv_monday:

                if (MONDAY.equalsIgnoreCase("mon")) {
                    MONDAY = "m";
                    Utills.savePreferences(Constants.KEY_MON2, "1", context);
                    tv_monday.setBackgroundResource(R.drawable.circleyello);
                } else {
                    MONDAY = "mon";
                    Utills.savePreferences(Constants.KEY_MON2, "0", context);
                    tv_monday.setBackgroundResource(R.drawable.circle);
                }


                break;
            case R.id.tv_tues:
                if (TUES.equalsIgnoreCase("tues")) {
                    TUES = "t";
                    Utills.savePreferences(Constants.KEY_TUES2, "1", context);
                    tv_tues.setBackgroundResource(R.drawable.circleyello);
                } else {
                    TUES = "tues";
                    Utills.savePreferences(Constants.KEY_TUES2, "0", context);
                    tv_tues.setBackgroundResource(R.drawable.circle);
                }

                break;
            case R.id.tv_wed:
                if (WED.equalsIgnoreCase("wed")) {
                    WED = "w";
                    Utills.savePreferences(Constants.KEY_WED2, "1", context);
                    tv_wed.setBackgroundResource(R.drawable.circleyello);
                } else {
                    WED = "wed";
                    Utills.savePreferences(Constants.KEY_WED2, "0", context);
                    tv_wed.setBackgroundResource(R.drawable.circle);
                }

                break;
            case R.id.tv_thurs:
                if (THU.equalsIgnoreCase("thu")) {
                    THU = "th";
                    Utills.savePreferences(Constants.KEY_THU2, "1", context);
                    tv_thurs.setBackgroundResource(R.drawable.circleyello);
                } else {
                    THU = "thu";
                    Utills.savePreferences(Constants.KEY_THU2, "0", context);
                    tv_thurs.setBackgroundResource(R.drawable.circle);
                }

                break;
            case R.id.tv_fri:
                if (FRI.equalsIgnoreCase("fri")) {
                    FRI = "f";
                    Utills.savePreferences(Constants.KEY_FRI2, "1", context);
                    tv_fri.setBackgroundResource(R.drawable.circleyello);
                } else {
                    FRI = "fri";
                    Utills.savePreferences(Constants.KEY_FRI2, "0", context);
                    tv_fri.setBackgroundResource(R.drawable.circle);
                }

                break;
            case R.id.tv_sat:
                if (SAT.equalsIgnoreCase("sat")) {
                    SAT = "sa";
                    Utills.savePreferences(Constants.KEY_SAT2, "1", context);
                    tv_sat.setBackgroundResource(R.drawable.circleyello);
                } else {
                    SAT = "sat";
                    Utills.savePreferences(Constants.KEY_MON2, "0", context);
                    tv_sat.setBackgroundResource(R.drawable.circle);
                }

                break;
            case R.id.tv_sun:
                if (SUN.equalsIgnoreCase("sun")) {
                    SUN = "su";
                    Utills.savePreferences(Constants.KEY_SUN2, "1", context);
                    tv_sun.setBackgroundResource(R.drawable.circleyello);
                } else {
                    SUN = "sun";
                    Utills.savePreferences(Constants.KEY_SUN2, "0", context);
                    tv_sun.setBackgroundResource(R.drawable.circle);
                }

                break;


        }

    }

    private void promptSave(final ReminderItem mData) {
//        mData.setTitle("title");
//        mData.setContent("Content");
//        createSaveDialog(mData).show();

        saveAlert(mData);

        terminateActivity();
    }

    private AlertDialog createSaveDialog(final ReminderItem item) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.confirm)
                .setMessage(R.string.save_prompt)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        saveAlert(item);
                        terminateActivity();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        terminateActivity();
                        dialog.dismiss();
                    }
                })
                .create();
    }

    private void terminateActivity() {
//        NavUtils.navigateUpFromSameTask(getActivity());
    }

    private void saveAlert(final ReminderItem item) {
        if (item.getId() > 0) {
            Intent cancelPrevious = new Intent(context,
                    AlarmService.class);
            cancelPrevious.putExtra(ReminderParams.ID, item.getId());
            cancelPrevious.setAction(AlarmService.CANCEL);
            getActivity().startService(cancelPrevious);

            ContentValues values = new ContentValues();
            values.put(ReminderContract.Alerts.TITLE, item.getTitle());
            values.put(ReminderContract.Alerts.CONTENT, item.getContent());
            values.put(ReminderContract.Alerts.TIME, item.getTimeInMillis());
            values.put(ReminderContract.Alerts.FREQUENCY, item.getFrequency());
            Uri uri = ContentUris.withAppendedId(ReminderContract.Alerts.CONTENT_URI, item.getId());
            mContentResolver.update(uri, values, null, null);

            Log.d("id", "id is " + item.getId());
            createAlarm(item.getId());
        } else {
            ContentValues values = new ContentValues();
            values.put(ReminderContract.Alerts.TYPE, ReminderType.ALERT.getName());
            values.put(ReminderContract.Alerts.TITLE, item.getTitle());
            values.put(ReminderContract.Alerts.CONTENT, item.getContent());
            values.put(ReminderContract.Alerts.TIME, item.getTimeInMillis());
            values.put(ReminderContract.Alerts.FREQUENCY, item.getFrequency());
            Uri uri = mContentResolver.insert(ReminderContract.Notes.CONTENT_URI,
                    values);
            if (uri != null) {
                createAlarm(Integer.parseInt(uri.getLastPathSegment()));
            }
        }
    }

    private void createAlarm(int id) {
        Intent alarm = new Intent(context, AlarmService.class);
        alarm.putExtra(ReminderParams.ID, id);
        alarm.setAction(AlarmService.CREATE);
        context.startService(alarm);
    }
}