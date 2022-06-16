package com.android.timetochangecontactlenses;

import static com.android.timetochangecontactlenses.db.MyConstants.APP_PREFERENCES_RSCH;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.timetochangecontactlenses.databinding.ActivityMainBinding;
import com.android.timetochangecontactlenses.db.MyConstants;
import com.android.timetochangecontactlenses.db.Note;
import com.android.timetochangecontactlenses.db.SQLiteManager;
import com.android.timetochangecontactlenses.user.AlarmClass;
import com.android.timetochangecontactlenses.user.UserActivity;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    String curDate = "";
    /// private MyDbManager myDbManager;
    private TextView tvPower, tvBC, tvDIA, tvLastTime, tvNext;
    private ProgressBar progress_day;
    private LinearLayout reminderWindow;
    private Button userAct, bSave;
    SharedPreferences mSettings;
    private int replacement_schedule;
    ArrayList<String> arrayList;
    private ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Note> notes;
    private MaterialTimePicker picker;
    private ActivityMainBinding binding;
    private Calendar calendar;
    public AlarmClass alarmClass;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Boolean cheakReminder;
    public int witchday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkFirstStart();
        // setContentView(R.layout.activity_main);
        // init();
        createNotificationChannel();
//        listView = findViewById(R.id.note_listView);
        loadFromDBMemory();
        //  setNoteAdapter();
//        myDbManager = new MyDbManager(this);
//        myDbManager.openDb();
        Log.d("MyLog", "Open");
    }

    public void loadFromDBMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.getInstance(this);
        // ContentValues values = new ContentValues();
        notes = sqLiteManager.populateNoteListArray();
        int a = notes.size();

        Log.d("MyLog", "SIZEARRAy" + a);

        if (a > 0) {
            Note note = notes.get(notes.size() - 1);
            tvLastTime.setText(note.getFirst().toString());
            Log.d("MyLog", "FIRSRTTT" + note.getFirst());
            tvNext.setText(note.getLast().toString());
        }
        howDays();


    }

    public void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "ALarm cancelled", Toast.LENGTH_LONG).show();

    }

    public void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Log.d("MyLog", "GETTIME" + calendar.getTimeInMillis());
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "ALarm set Successfully", Toast.LENGTH_LONG).show();
    }

    private void checkFirstStart() {

        SharedPreferences sp = getSharedPreferences("hasVisited",
                Context.MODE_PRIVATE);
        mSettings = getSharedPreferences(MyConstants.APP_PREFERENCES, Context.MODE_PRIVATE);
        String date = mSettings.getString(APP_PREFERENCES_RSCH, "");
        // проверяем, первый ли раз открывается программа (Если вход первый то вернет false)
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        Log.d("MyLOg", "зашел в чек");
        if (!hasVisited) {
            // Сработает если Вход первый
            Log.d("MyLOg", "ВХОД ПЕРВЫЙ");
            //Ставим метку что вход уже был
            firstTime(sp);
            //Ниже запускаем активность которая нужна при первом входе

        } else if (date.equals("null") || date.equals("") || date.equals("empty")) {

            firstTime(sp);
        } else {
            init();
            //Сработает если вход в приложение уже был
            //Ниже запускаем активность которая нужна при последующих входах
        }
    }

    public void firstTime(SharedPreferences sp) {
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("hasVisited", true);
        e.commit(); //После этого hasVisited будет уже true и будет означать, что вход уже был
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // myDbManager.openDb();
        Log.d("MyLog", "Open2");
//        for(String title : myDbManager.getFromDB()){
//            tvLastTime.append(title);
//        }
    }

//    private void setNoteAdapter() {
//        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(), Note.noteArrayList);
//        listView.setAdapter(noteAdapter);
//    }

    public void init() {
//        arrayList=myDbManager.getFromDB();
//        arrayAdapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
//        listView.setAdapter(arrayAdapter);
//
//      for(String title : myDbManager.getFromDB()){
//          tvLastTime.append(title);
//      }
        mSettings = getSharedPreferences(MyConstants.APP_PREFERENCES, Context.MODE_PRIVATE);
        tvPower = findViewById(R.id.tVPower);
        tvBC = findViewById(R.id.tvBC);
        tvDIA = findViewById(R.id.tvDia);
        tvLastTime = findViewById(R.id.tvLast);
        tvNext = findViewById(R.id.tvNext);
        bSave = findViewById(R.id.buttonSave);
        progress_day = findViewById(R.id.progress_days);
        cheakReminder = false;
        alarmClass = new AlarmClass();
        cheakAlarm();

//        binding.selectTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePicker();
//            }
//        });
        binding.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
        binding.cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
        bSave.setOnClickListener(onClickSave());
        tvPower.setText(mSettings.getString(MyConstants.APP_PREFERENCES_Power, ""));
        tvBC.setText(mSettings.getString(MyConstants.APP_PREFERENCES_BC, ""));
        tvDIA.setText(mSettings.getString(MyConstants.APP_PREFERENCES_DIA, ""));
        String days = mSettings.getString(APP_PREFERENCES_RSCH, "");
        replacement_schedule = Integer.parseInt(days);
        Log.d("MyLog", "DAYS" + replacement_schedule);
        //  Log.d("MyLog", "SIZE" + myDbManager.getFromDB().size());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker materialDatePicker = builder.build();
        MaterialDatePicker.Builder builder2 = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker materialDatePicker2 = builder.build();

        builder.setTitleText("Select a date");
        builder2.setTitleText("Select a date");
        binding.selectDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyLog", "ДАТА НАПОМИНАНИЯ");
                cheakReminder = true;
                cheakAlarm();
                materialDatePicker2.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });
        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long s = Long.parseLong(selection.toString());
                Log.d("MyLog", "kkkkkkkkkkkkkkjj");
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(s);
                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                String formattedDate = format.format(calendar.getTime());
                Log.d("MyLog", "kkkkkkkkkkkkkk");
                binding.tvNotif.setText(formattedDate);
                showTimePicker();
            }
        });
        tvLastTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MyLog", "PFFFFFFFFFFFFFFFFF");
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long s = Long.parseLong(selection.toString());
                tvLastTime.setText(materialDatePicker.getHeaderText());
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(s);
                calendar.add(Calendar.DAY_OF_MONTH, +replacement_schedule);
                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
                String formattedDate = format.format(calendar.getTime());
                tvNext.setText(formattedDate);

                //  myDbManager.insertToDb(tvLastTime.getText().toString(),tvNext.getText().toString());
            }
        });
    }

    private void cheakAlarm() {
        if (cheakReminder == false) {
            binding.reminderWindow.setVisibility(View.GONE);
            binding.progressDays.setVisibility(View.VISIBLE);
//            binding.cancelAlarm.setVisibility(View.GONE);
//            binding.textView2.setVisibility(View.GONE);
//            binding.setAlarm.setVisibility(View.GONE);
        } else {
//            binding.cancelAlarm.setVisibility(View.VISIBLE);
//            binding.textView2.setVisibility(View.VISIBLE);
//            binding.setAlarm.setVisibility(View.VISIBLE);
            binding.reminderWindow.setVisibility(View.VISIBLE);
            binding.progressDays.setVisibility(View.GONE);
        }
    }

    public void howDays() {
        try {
            //The code you are trying to exception handle
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
            String date = dateFormat.format(Calendar.getInstance().getTime());
            // Date date1=new SimpleDateFormat("dd MMM yyyy").parse(date);
            String firstDay = tvLastTime.getText().toString();
            Date date1 = dateFormat.parse(firstDay);
            Date date2 = dateFormat.parse(date);
            long milliseconds = date2.getTime() - date1.getTime();
            int daysOf = (int) (milliseconds / (24 * 60 * 60 * 1000)) + 1;
            TextView howDays = findViewById(R.id.howDaysQ);
            howDays.setText(daysOf + "" + getString(R.string.how_days));
            witchday = daysOf;
            int percent = (daysOf * 100 / replacement_schedule);
            progress_day.setProgress(percent);
            cheakUseDays(replacement_schedule - witchday);


            Log.d("MyLog", "dayof" + daysOf + " rep_sh " + replacement_schedule + " progress " + percent);
        } catch (Exception e) {
            //The handling for the code
        }
//        Date date2=new SimpleDateFormat("dd MMM yyyy").parse(firstDay);
//        Duration duration = Duration.between(date1.getTime(), date2.getTime());
    }


    public void saveNote() {
        // SQLiteManager sqLiteManager1 = SQLiteManager.instanceOfDataBase(this);
        String first = tvLastTime.getText().toString();
        String second = tvNext.getText().toString();
        int id = notes.size();
        Note newNote = new Note(id, first, second);
        notes.add(newNote);
        SQLiteManager.getInstance(this).addNoteToDatabase(newNote);
        // sqLiteManager.populateNoteListArray();
        int a = notes.size();
        howDays();
        Log.d("MyLog", "SIZEARRAy" + a);

        //  finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // myDbManager.closeDb();
        Log.d("MyLog", "CLose ");

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            CharSequence name = "foxandroidReminderChannel";
            String description = "Chanel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(), "foxandroid");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (picker.getHour() > 12) {
//                    binding.selectTime.setText(
//                            String.format("%02d", (picker.getHour() - 12) + " : " + String.format("%02d", picker.getMinute()) + "PM")
//
//                    );
//                } else {
//                    binding.selectTime.setText(picker.getHour() + " : " + picker.getMinute() + "AM");
//                }
                //binding.selectTime.setText(picker.getHour() + " : " + picker.getMinute());
                String formattedDate2 = binding.tvNotif.getText().toString();
                String s = formattedDate2;
                String selectTime = picker.getHour() + " : " + picker.getMinute();
                //binding.selectTime.getText().toString();
                binding.tvNotif.setText(formattedDate2 + " " + selectTime);
                String pattern = "dd MMM yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date date = null;
                try {
                    date = simpleDateFormat.parse(s);
                    Log.d("MyLog", "день" + date.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, date.getDate());

                calendar.set(Calendar.MONTH, date.getMonth() + 1);
                calendar.set(Calendar.YEAR, date.getYear());
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Log.d("MyLog", "календарь " + calendar.get(Calendar.MONTH) + " /" + calendar.get(Calendar.DAY_OF_MONTH) + " /" + "/" +
                        calendar.get(Calendar.HOUR_OF_DAY) + "/" + calendar.get(Calendar.MINUTE) + "///" + calendar.getTimeInMillis());

            }
        });

    }

    public void onClick(View view) {

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.saveData);
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("MyLog", "curdate" + curDate);

            }
        });

    }

    ;

    private View.OnClickListener onClickSave() {

        return view -> {

            bb();
            //builder.setCancelable(true);
        };
    }

    public void bb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.titleAlertSave);  // заголовок
        builder.setMessage(R.string.alertText); // сообщение
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(MainActivity.this, R.string.dateChangeButton,
                        Toast.LENGTH_LONG).show();
                saveNote();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(MainActivity.this, R.string.noChangeDate, Toast.LENGTH_LONG)
                        .show();
            }
        });
        builder.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                Intent intent = new Intent(this, UserActivity.class);
                intent.putExtra("itIsFirst", "lll");
                intent.putExtra("nextDay", tvNext.getText().toString());
                startActivity(intent);
                return true;
            case R.id.allDates:
                Intent intent1 = new Intent(this, listDates.class);
//                 intent1.putExtra();
                startActivity(intent1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void cheakUseDays(int days) {
        Log.d("MyLog", "CheakUseDays " + days);
        if (days < replacement_schedule && days == 2 && days == 3 && days == 4) {
//            Toast.makeText(MainActivity.this, R.string.timeChange,
//                    Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.reminder)
                    .setMessage(getString(R.string.howdays_before_change) + days + getString(R.string.q2_days))
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Закрываем диалоговое окно
                            dialog.cancel();
                        }
                    }).
                    create()
                    .show();
        } else if (days == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.reminder)
                    .setMessage(R.string.todayDayChange)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Закрываем диалоговое окно
                            dialog.cancel();
                        }
                    }).create()
                    .show();
        } else if (days < replacement_schedule && days == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.reminder)
                    .setMessage(getString(R.string.howdays_before_change) + days + getString(R.string.one_day))
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Закрываем диалоговое окно
                            dialog.cancel();
                        }
                    }).create().show();
        } else if (days > replacement_schedule) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.reminder)
                    .setMessage(getString(R.string.timeChange))
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Закрываем диалоговое окно
                            dialog.cancel();
                        }
                    }).create()
                    .show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.reminder)
                    .setMessage(getString(R.string.howdays_before_change) + days + getString(R.string.q_days))
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Закрываем диалоговое окно
                            dialog.cancel();
                        }
                    }).create()
                    .show();
        }
    }
}