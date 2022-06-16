package com.android.timetochangecontactlenses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SharedPreferencesExample extends Activity {
    private static final String MY_SETTINGS = "my_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);
        if (hasVisited
        ) {
//            Intent intent = new Intent(SharedPreferencesExample.this, UserActivity.class);
//
//            startActivity(intent);
        }

        if (!hasVisited) {
            Intent intent2 = new Intent(SharedPreferencesExample.this, MainActivity.class);
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit(); // не забудьте подтвердить изменения
            startActivity(intent2);
        }
    }
}

