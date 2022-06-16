package com.android.timetochangecontactlenses;

import static com.android.timetochangecontactlenses.R.color.textColor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.timetochangecontactlenses.db.Note;
import com.android.timetochangecontactlenses.db.SQLiteManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class listDates extends AppCompatActivity {
    public ArrayList<String> dates;
    ArrayList<Note> notes;
    ListView listViewDates;
    ImageButton deleteButton;
    ArrayList<String> chosenList;
    SQLiteManager sqLiteManager;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dates);
        listViewDates = findViewById(R.id.listView);
        dates = new ArrayList<>();
        chosenList = new ArrayList<String>();
        sqLiteManager = SQLiteManager.getInstance(this);

        getArray();
        listViewDates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {

                int cntChoice = listViewDates.getCount();

                SparseBooleanArray sparseBooleanArray = listViewDates
                        .getCheckedItemPositions();
                listViewDates.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void chosenListSIze() {
        int cntChoice = listViewDates.getCount();
//     SparseBooleanArray sparseBooleanArray = listViewDates
//             .getCheckedItemPositions();
//     listViewDates.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//     for (int i = 0; i < cntChoice; i++) {
//
//         if (sparseBooleanArray.get(i) == true) {
//             chosenList.add(itemClicked.toString());
//
//         } else if (sparseBooleanArray.get(i) == false) {
//             chosenList = findStringIterator(itemClicked.toString(), chosenList);
//         }
//
//
//     }
        SparseBooleanArray checkedItems = listViewDates.getCheckedItemPositions();
        if (checkedItems != null) {
            for (int i = 0; i < checkedItems.size(); i++) {
                if (checkedItems.valueAt(i)) {
                    String item = listViewDates.getAdapter().getItem(
                            checkedItems.keyAt(i)).toString();
                    chosenList.add(item);
                }
            }
        }

    }


    public ArrayList<String> findStringIterator(
            String date, ArrayList<String> dates) {
        Iterator iterator = dates.iterator();
        while (iterator.hasNext()) {
            String nextDate = (String) iterator.next();
            if (nextDate.equals(date)) {

                dates.remove(nextDate);

            }
        }
        Log.d("MyLog", "size chosen" + dates.size());
        return dates;
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    public void getArray() {
        String date;

        // ContentValues values = new ContentValues();
        notes = sqLiteManager.populateNoteListArray();
        int a = notes.size();
        Log.d("MyLog", "SIZEINDATES" + a);

        for (int i = 0; i < notes.size(); i++) {
            date = notes.get(i).getFirst() + " - " + notes.get(i).getLast();
            dates.add(date);

        }
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_multiple_choice, dates) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                CheckedTextView checkedTxtView = (CheckedTextView) super.getView(position, convertView, parent);
                String yourValue = dates.get(position);
                checkedTxtView.setText(yourValue);
                checkedTxtView.setTextColor(getResources().getColor(R.color.buttonColor));
                return checkedTxtView;
            }

        };


        // устанавливаем для списка адаптер
        listViewDates.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemDelete:
                bb();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void bb() {
        AlertDialog.Builder builder = new AlertDialog.Builder(listDates.this);
        builder.setTitle(R.string.titleAlertSave);  // заголовок
        builder.setMessage(R.string.delete_date); // сообщение
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(listDates.this, R.string.dates_delete,
                        Toast.LENGTH_LONG).show();

                deleteChosenList();


            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(listDates.this, R.string.dates_noDelete, Toast.LENGTH_LONG)
                        .show();
            }
        });
        builder.show();
    }

    public void deleteChosenList() {
        chosenListSIze();
        for (int i = 0; i < chosenList.size(); i++) {
            sqLiteManager.deleteNoteFromDatabase(chosenList.get(i));

            dates.remove(chosenList.get(i));
            adapter.notifyDataSetChanged();

        }


    }
}