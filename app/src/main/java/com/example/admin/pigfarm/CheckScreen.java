package com.example.admin.pigfarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.R;

public class CheckScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);


        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(2000);
                    SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String val = shared.getString("username", "");
                    if (val.length() == 0) {

                        Intent intent = new Intent(CheckScreen.this, LoginActivity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        Intent intent = new Intent(CheckScreen.this, Home.class);
                        startActivity(intent);
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };

        thread.start();
    }

}

