package com.example.admin.pigfarm.Report;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.admin.R;

public class Report_Status extends AppCompatActivity {

    private String farm_id;
    CardView card_dadmom,card_NewMom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_status);


        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        farm_id = farm.getString("farm_id", "");

        card_dadmom = findViewById(R.id.card_dadmom);

        card_dadmom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Report_Status_a1_Fragment report_status_a1_fragment = new Report_Status_a1_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.left_to_right, R.anim.right_to_left, R.anim.left_to_right, R.anim.right_to_left);
                transaction.replace(R.id.report_status_fragment, report_status_a1_fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
