package com.android.timetochangecontactlenses.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.android.timetochangecontactlenses.R;

import java.util.ArrayList;

public class DialogChooseReminder extends DialogFragment implements DialogInterface.OnClickListener {
    ArrayList<Integer> days;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        days = new ArrayList<>();
        final String[] daysNameArray = {getString(R.string.seven_days), getString(R.string.two_days), getString(R.string.one_dayreplace)};
        final boolean[] checkedItemsArray = {false, true, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.remind_replace))
                .setMultiChoiceItems(daysNameArray, checkedItemsArray,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which, boolean isChecked) {
                                checkedItemsArray[which] = isChecked;
                            }
                        })
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                for (int i = 0; i < checkedItemsArray.length; i++) {
                                    if (checkedItemsArray[i] == true)
                                        if (daysNameArray[i].equals(getString(R.string.seven_days))) {
                                            days.add(7);

                                        }
                                    if (daysNameArray[i].equals(getString(R.string.one_dayreplace))) {
                                        days.add(1);

                                    }
                                    if (daysNameArray[i].equals(getString(R.string.two_days))) {
                                        days.add(2);

                                    }
                                }

                            }
                        })

                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }

    public ArrayList<Integer> returnDays() {
        return days;
    }


//    ArrayAdapter<String> adapter;
//    ListView choose_list;
//    String[] dayOptions;
//    private final int IDD_LIST_CATS = 1;
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//
//            case IDD_LIST_CATS:
//
//                final String[] mCatsName ={"Тимка", "Пушок", "Кузя"};
//
//                builder = new AlertDialog.Builder(this);
//                builder.setTitle("Выбираем кота"); // заголовок для диалога
//
//                builder.setItems(mCatsName, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        // TODO Auto-generated method stub
//                        Toast.makeText(getApplicationContext(),
//                                "Выбранный кот: " + mCatsName[item],
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setCancelable(false);
//                return builder.create();
//
//            default:
//                return null;
//        }
//    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
//
//    }
//        final String LOG_TAG = "myLogs";
//
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            getDialog().setTitle("Title!");
//            View v = inflater.inflate(R.layout.dialog_choose, null);
//            choose_list = (ListView) findViewById(R.id.listView);
//            // устанавливаем режим выбора пунктов списка
//            choose_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//            // Создаем адаптер, используя массив из файла ресурсов
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                    this, R.array.dayOptions,
//                    android.R.layout.simple_list_item_single_choice);
//            choose_list.setAdapter(adapter);
//
//            Button btnChecked = (Button) findViewById(R.id.btnChecked);
//            btnChecked.setOnClickListener(this);
//
//            // получаем массив из файла ресурсов
//            names = getResources().getStringArray(R.array.names);
//        }
//
//    public void onClick(View arg0) {
//        // пишем в лог выделенный элемент
//        Log.d(LOG_TAG, "checked: " + names[lvMain.getCheckedItemPosition()]);
//    }
//}
    }

}
