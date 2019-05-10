package com.example.admin.pigfarm.Report;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.admin.R;

public class Report_Home extends AppCompatActivity {

    private String farm_id;
    CardView card_groupmanage, card_status_of_dadmom,card_history_of_dadmom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_home);

        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        farm_id = farm.getString("farm_id", "");

        card_groupmanage = findViewById(R.id.card_groupmanage);
        card_status_of_dadmom = findViewById(R.id.card_status_of_dadmom);
        card_history_of_dadmom = findViewById(R.id.card_history_of_dadmom);

        card_groupmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report_Home.this, Report_Group.class);
                startActivity(intent);
            }
        });

        card_status_of_dadmom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report_Home.this, Report_Status.class);
                startActivity(intent);
            }
        });

        card_history_of_dadmom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Report_Home.this, Report_History.class);
                startActivity(intent);
            }
        });


    }
}
