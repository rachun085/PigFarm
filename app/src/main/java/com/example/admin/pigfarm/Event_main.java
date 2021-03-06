package com.example.admin.pigfarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.R;

public class Event_main extends AppCompatActivity {
    Spinner event_spinner;
    ImageView img_back2;
    TextView txt_farmName2,txt_unitName2;
    String getfarm_name,getunit_name,getfarm_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_note);

        event_spinner = findViewById(R.id.spin_event);
        img_back2 = findViewById(R.id.img_back2);
        txt_farmName2 = findViewById(R.id.txt_farmName2);
        txt_unitName2 = findViewById(R.id.txt_unitName2);

        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        getfarm_name = farm.getString("farm_name", "");
        getunit_name = farm.getString("unit_name", "");
        getfarm_id = farm.getString("farm_id","");
        txt_farmName2.setText(getfarm_name);
        txt_unitName2.setText(getunit_name);


        img_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Event_main.this);
                builder.setCancelable(true);
                builder.setMessage("บันทึกข้อมูลแล้วใช่หรือไม่");
                builder.setPositiveButton("ใช่",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Event_main.this, Home.class);
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
        });

        final String[] eventStr = getResources().getStringArray(R.array.Event_pig);
        ArrayAdapter<String> adapterEvent = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, eventStr);
        adapterEvent.setDropDownViewResource(R.layout.spinner_item);
        event_spinner.setAdapter(adapterEvent);

        event_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (String.valueOf(event_spinner.getSelectedItem()).equals("ผสมพันธุ์")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Breed_Fragment breed_fragment = new Breed_Fragment();  //ผสมพันธุ์
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    breed_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, breed_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("ตรวจท้อง")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    CheckPregnant_Fragment checkpregnant_fragment = new CheckPregnant_Fragment(); //ตรวจท้อง
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    checkpregnant_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, checkpregnant_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("แท้ง")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Pregnent_Fragment pregnant_fragment = new Pregnent_Fragment(); //แท้ง
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    pregnant_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, pregnant_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("คลอด")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Maternity_Fragment maternity_fragment = new Maternity_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    maternity_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, maternity_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("ฝากเลี้ยง")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Entrustment_Fragment entrustment_fragment = new Entrustment_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    entrustment_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, entrustment_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("หย่านม")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Wean_Fragment wean_fragment = new Wean_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    wean_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, wean_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("รับเลี้ยงลูก")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Adopt_Fragment adopt_fragment = new Adopt_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    adopt_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, adopt_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("คัดทิ้ง")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Exclude_Fragment exclude_fragment = new Exclude_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    exclude_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, exclude_fragment);
                    transaction.commit();
                }


                else if (String.valueOf(event_spinner.getSelectedItem()).equals("หมายเหตุ")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Note_Fragment note_fragment = new Note_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    note_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, note_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("ลูกหมูตาย")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Piggydead_Fragment piggydead_fragment = new Piggydead_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    piggydead_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, piggydead_fragment);
                    transaction.commit();
                }


                else if (String.valueOf(event_spinner.getSelectedItem()).equals("เป็นสัดแต่ไม่ผสมพันธุ์")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Rutnotbreeded_Fragment rutnotbreeded_fragment = new Rutnotbreeded_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    rutnotbreeded_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, rutnotbreeded_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("ท้องลม")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Blightedovum_Fragment blightedovum_fragment = new Blightedovum_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    blightedovum_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, blightedovum_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("แยกหย่านม")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Wean2_Fragment wean2_fragment = new Wean2_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    wean2_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, wean2_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("ได้รับยารักษา")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Treat_Fragment treat_fragment = new Treat_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    treat_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, treat_fragment);
                    transaction.commit();
                }

                else if (String.valueOf(event_spinner.getSelectedItem()).equals("ป่วยเป็นโรค")){
                    String textbreed = event_spinner.getSelectedItem().toString();
                    Bundle bundle = new Bundle();
                    Getsick_Fragment getsick_fragment = new Getsick_Fragment();
                    bundle.putString("textbreed", textbreed);
                    bundle.putString("farm_id", getfarm_id);
                    getsick_fragment.setArguments(bundle);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_event, getsick_fragment);
                    transaction.commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
