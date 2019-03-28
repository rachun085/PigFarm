package com.example.admin.pigfarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.R;

public class Open_Profile extends AppCompatActivity {

    TextView txt_farmOpen2,txt_unitOpen2;
    CardView card_geneMom, card_newnote, card_geneDad;
    ImageView img_back;
    String getfarm_name2,getunit_name2,getfarm_id;
    ViewPager pager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_profile);

        txt_farmOpen2 = findViewById(R.id.txt_farmOpen2);
        txt_unitOpen2 = findViewById(R.id.txt_unitOpen2);

        card_geneDad = findViewById(R.id.card_geneDad);
        card_newnote = findViewById(R.id.card_newnote);
        card_geneMom = findViewById(R.id.card_geneMom);

        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        getfarm_name2 = farm.getString("farm_name", "");
        getunit_name2 = farm.getString("unit_name", "");
        getfarm_id = farm.getString("farm_id","");
        txt_farmOpen2.setText(getfarm_name2);
        txt_unitOpen2.setText(getunit_name2);


        card_geneMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Newpig_Fragment newpig_fragment = new Newpig_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_fragment, newpig_fragment);
                transaction.commit();

                card_geneMom.setCardBackgroundColor(Color.parseColor("#000000"));
                card_newnote.setCardBackgroundColor(Color.parseColor("#F76196"));
                card_geneDad.setCardBackgroundColor(Color.parseColor("#F76196"));
            }
        });

        card_geneDad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Newbredder_Fragment newbredder_fragment = new Newbredder_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_fragment, newbredder_fragment);
                transaction.commit();

                card_geneDad.setCardBackgroundColor(Color.parseColor("#000000"));
                card_newnote.setCardBackgroundColor(Color.parseColor("#F76196"));
                card_geneMom.setCardBackgroundColor(Color.parseColor("#F76196"));
            }
        });

        card_newnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reprofile_Fragment reprofile_fragment = new Reprofile_Fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_fragment, reprofile_fragment);
                transaction.commit();

                card_newnote.setCardBackgroundColor(Color.parseColor("#000000"));
                card_geneDad.setCardBackgroundColor(Color.parseColor("#F76196"));
                card_geneMom.setCardBackgroundColor(Color.parseColor("#F76196"));
            }
        });

    }

    public void onBackClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Open_Profile.this);
        builder.setCancelable(true);
        builder.setMessage("บันทึกข้อมูลแล้วใช่หรือไม่");
        builder.setPositiveButton("ใช่",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Open_Profile.this, Home.class);
                        startActivity(intent);
                        finish();

                    }
                });
        builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    }

