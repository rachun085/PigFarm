package com.example.admin.pigfarm.ManageData_Page;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.R;
import com.example.admin.pigfarm.Event_main;
import com.example.admin.pigfarm.Home;
import com.example.admin.pigfarm.LoginActivity;
import com.example.admin.pigfarm.Open_Profile;

public class MainActivity_ManageData extends AppCompatActivity{

    private Toolbar toolbar1;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    NavigationView navigation;
    private String getfarm_name;
    private String getunit_name,getfarm_id;
    TextView textfarm_drawer, textunit_drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawerlayout);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,new RecyclerFragment())
                    .commit();
        }

        navigation = findViewById(R.id.navigation_view);
        View headerView = navigation.getHeaderView(0);
        textfarm_drawer = headerView.findViewById(R.id.textfarm_drawer);
        textunit_drawer = headerView.findViewById(R.id.textunit_drawer);

        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        getfarm_name = farm.getString("farm_name", "");
        getunit_name = farm.getString("unit_name", "");
        getfarm_id = farm.getString("farm_id","");
        textfarm_drawer.setText("ฟาร์ม : "+getfarm_name);
        textunit_drawer.setText("ยูนิต : "+getunit_name);

        toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(MainActivity_ManageData.this, drawerLayout,R.string.drawer_open, R.string.drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.nav_home:
                        Intent intent = new Intent(MainActivity_ManageData.this, Home.class);
                        startActivity(intent);

                        break;
                    case R.id.nav_openprofile:
                        Intent intent2 = new Intent(MainActivity_ManageData.this, Open_Profile.class);
                        startActivity(intent2);

                        break;
                    case R.id.nav_event:
                        Intent intent3 = new Intent(MainActivity_ManageData.this, Event_main.class);
                        startActivity(intent3);

                        break;
                    case R.id.nav_datapig:
                        Intent intent4 = new Intent(MainActivity_ManageData.this, MainActivity_ManageData.class);
                        startActivity(intent4);

                        break;
                    case R.id.nav_logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_ManageData.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setCancelable(true);
                        builder.setMessage("คุณต้องการออกระบบใช่หรือไม่");
                        builder.setPositiveButton("ใช่",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = shared.edit();
                                        editor.clear();
                                        editor.commit();

                                        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor2 = farm.edit();
                                        editor2.clear();
                                        editor2.apply();

                                        Intent intent = new Intent(MainActivity_ManageData.this, LoginActivity.class);
                                        startActivity(intent);
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
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


}
