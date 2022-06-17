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

public class FitnessFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    View fragment_view;
    private Button btn_back;
    private Context context;
    private int reminder_frequency;
    private IndicatorSeekBar indicatorSeekBar;
    private Switch sw_walk, sw_pushup, sw_freashair, sw_squat, sw_plank;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        fragment_view = inflater.inflate(R.layout.fitness_fragemnt, container, false);
        context = getActivity();

        linkViews();
        return fragment_view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void linkViews() {

        /*load once */

        mContentResolver = getActivity().getContentResolver();

        mapList = new ArrayList<>();
        mAlarmTime = new HashMap<>();
        mAlarmDate = new HashMap<>();
        mAlarmRepeat = new HashMap<>();


        alaramData();


        btn_back = fragment_view.findViewById(R.id.btn_back);

        indicatorSeekBar = fragment_view.findViewById(R.id.seekbar);
        sw_walk = fragment_view.findViewById(R.id.sw_walk);
        sw_pushup = fragment_view.findViewById(R.id.sw_pushup);
        sw_freashair = fragment_view.findViewById(R.id.sw_freashair);
        sw_squat = fragment_view.findViewById(R.id.sw_squat);
        sw_plank = fragment_view.findViewById(R.id.sw_plank);

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
            if (title.equalsIgnoreCase("sw_walk")) {
                sw_walk.setChecked(true);
            } else {
//                sw_walk.setChecked(false);
            }


            if (title.equalsIgnoreCase("sw_pushup")) {
                sw_pushup.setChecked(true);
            } else {
//                sw_pushup.setChecked(false);
            }

            if (title.equalsIgnoreCase("sw_pushup")) {
                sw_pushup.setChecked(true);
            } else {
//                sw_pushup.setChecked(false);
            }


            if (title.equalsIgnoreCase("sw_freashair")) {
                sw_freashair.setChecked(true);
            } else {
//                sw_freashair.setChecked(false);
            }

            if (title.equalsIgnoreCase("sw_squat")) {
                sw_squat.setChecked(true);
            } else {
//                sw_squat.setChecked(false);
            }


            if (title.equalsIgnoreCase("sw_plank")) {
                sw_plank.setChecked(true);
            } else {
//                sw_plank.setChecked(false);
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

        sw_walk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);


                setReminder(isChecked, "sw_walk", Utills.getPreferences(Constants.sw_walk, context));

            }

        });


        sw_pushup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                setReminder(isChecked, "sw_pushup", Utills.getPreferences(Constants.sw_pushup, context));

            }

        });

        sw_freashair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                setReminder(isChecked, "sw_freashair", Utills.getPreferences(Constants.sw_freashair, context));

            }

        });

        sw_squat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                setReminder(isChecked, "sw_squat", Utills.getPreferences(Constants.sw_squat, context));

            }

        });


        sw_plank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);

                setReminder(isChecked, "sw_plank", Utills.getPreferences(Constants.sw_plank, context));


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