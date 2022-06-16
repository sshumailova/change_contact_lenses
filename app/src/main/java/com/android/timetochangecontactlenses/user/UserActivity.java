package com.android.timetochangecontactlenses.user;

import static java.lang.Integer.parseInt;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.android.timetochangecontactlenses.AlarmReceiver;
import com.android.timetochangecontactlenses.MainActivity;
import com.android.timetochangecontactlenses.R;
import com.android.timetochangecontactlenses.db.MyConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
//import com.android.timetochangecontactlenses.db.MyDbManager;

public class UserActivity extends AppCompatActivity {
    // имя настройки

    // private MyDbManager myDbManager;
    SharedPreferences mSettings;
    EditText editTextBC;
    EditText editTextUserName;
    EditText editTextPower;
    EditText editTextDIA;
    EditText howDays;
    CheckBox checkBox;
    TextView selection;
    Button bSave;
    String strNickName;
    String strPower;
    String strBC;
    String strDIA;
    String strDays;
    String nextDate;
    public static ArrayList<Integer> days;
    boolean cheakBoxTurnOn;
    private AlarmManager alarmManager;
    DialogChooseReminder myDialogFragment;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //  myDbManager=new MyDbManager(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // myDbManager.openDb();
    }

    private void init() {
        days = new ArrayList<>();
        mSettings = getSharedPreferences(MyConstants.APP_PREFERENCES, Context.MODE_PRIVATE);
        editTextPower = findViewById(R.id.editTextPower);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextBC = findViewById(R.id.editTextTextPersonName);
        editTextDIA = findViewById(R.id.editTextTextPersonName2);
        howDays = findViewById(R.id.editTextHowDays);
        bSave = findViewById(R.id.bSave);
        checkBox = findViewById(R.id.enabled);
        selection = findViewById(R.id.selection);
        cheakBoxTurnOn = false;
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem();
            }
        });
        if (getIntent() != null) {
            Log.d("MyLOg", "ИНТЕНТ НЕ НАЛЛ");
            //  Intent i = getIntent();
            //if(getIntent().getStringExtra("itIsFirst").equals("lll")) {
            Log.d("MyLOg", "ИНТЕНТ equals");
            nextDate=getIntent().getStringExtra("nextDay");
            editTextPower.setText(mSettings.getString(MyConstants.APP_PREFERENCES_Power, ""));
            editTextUserName.setText(mSettings.getString(MyConstants.APP_PREFERENCES_NameUser, ""));
            editTextBC.setText(mSettings.getString(MyConstants.APP_PREFERENCES_BC, ""));
            editTextDIA.setText(mSettings.getString(MyConstants.APP_PREFERENCES_DIA, ""));
            howDays.setText(mSettings.getString(MyConstants.APP_PREFERENCES_RSCH, ""));
            String days = mSettings.getString(MyConstants.APP_PREFERENCES_RSCH, "");
            // }
        }


    }

    public void onCheckboxClicked(View view) {
        // Получаем флажок

        // Получаем, отмечен ли данный флажок
        if (checkBox.isChecked()) {
            cheakBoxTurnOn = true;
            FragmentManager manager = getSupportFragmentManager();
            myDialogFragment = new DialogChooseReminder();
            myDialogFragment.show(manager, "myDialog");
        } else {

        }
    }


    private void onClickItem() {
        if (howDays.getText().toString().equals("") || howDays.getText().toString().equals("empty") || howDays.getText().toString().equals(null)) {
            Log.d("MyLog ", "ddd " + howDays.getText().toString());
            Toast.makeText(UserActivity.this, R.string.days_change_need, Toast.LENGTH_SHORT).show();
        } else {
            android.app.AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
            builder.setMessage(R.string.saveData);
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getData();
                    isSomethingChange();
                    Collections.copy(myDialogFragment.returnDays(), days);
                    reminderhelp(parseInt(howDays.getText().toString()));
                }
            });
            builder.show();
        }


    }

    public void getData() {

        strNickName = editTextUserName.getText().toString();
        strPower = editTextPower.getText().toString();
        strBC = editTextBC.getText().toString();
        strDIA = editTextDIA.getText().toString();
        strDays = howDays.getText().toString();
    }

    public void isSomethingChange() {

        //  myDbManager.insertToDb(strNickName,strPower,strBC,strDIA,);
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        startActivity(intent);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(MyConstants.APP_PREFERENCES_NameUser, strNickName);
        editor.putString(MyConstants.APP_PREFERENCES_Power, strPower);
        editor.putString(MyConstants.APP_PREFERENCES_BC, strBC);
        editor.putString(MyConstants.APP_PREFERENCES_DIA, strDIA);
        editor.putString(MyConstants.APP_PREFERENCES_RSCH, strDays);
        editor.apply();
    }

    public void reminderhelp(int days)  {
        Date date_next ;
        Date date_notif;
        Calendar cal_next = Calendar.getInstance();
        Calendar cal_reminder = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long current_year_in_milliseconds = 0;

        //"dd/MM/yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        try {
            date_next = new SimpleDateFormat("dd MMM yyyy").parse(nextDate);
            cal_next.setTime(date_next);
            cal_reminder.add(Calendar.DAY_OF_MONTH, +days);
           cal_reminder.add(Calendar.HOUR ,+ 10);
            Log.d("MyLog"," dddays" +Calendar.DAY_OF_MONTH);
            Date current_day = new Date(cal_reminder.getTimeInMillis());
            current_year_in_milliseconds = current_day.getTime();
            Log.d("MyLog", "TIMEE " + current_year_in_milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Title")
//                        .setContentText("Notification text")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                       ;
//                        //setWhen(current_year_in_milliseconds);
//
//        Notification notification = builder.build();
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notification);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            Log.d("MyLog", "GETTIME" + current_year_in_milliseconds);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, current_year_in_milliseconds, AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(this, "ALarm set Successfully", Toast.LENGTH_LONG).show();
        }
    }


