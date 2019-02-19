package com.example.admin.pigfarm.Report;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.admin.R;

public class Report_Group extends AppCompatActivity {

    private String farm_id;
    Spinner spin_type_of_report, spin_place_mom;
    Button btn_submit_report;
    EditText edt_lengthtime, edt_checkpregnant, edt_reportmom;
    private String lengthtime,checkpregnant;
    private WebView webview;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_group);

        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        farm_id = farm.getString("farm_id", "");



        spin_type_of_report = findViewById(R.id.spin_type_of_report);
        spin_place_mom = findViewById(R.id.spin_place_mom);
        btn_submit_report = findViewById(R.id.btn_submit_report);
        edt_lengthtime = findViewById(R.id.edt_lengthtime);
        edt_checkpregnant = findViewById(R.id.edt_checkpregnant);
        edt_reportmom = findViewById(R.id.edt_reportmom);

        final String[] eventStr = getResources().getStringArray(R.array.type_of_report);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, eventStr);
        adapterType.setDropDownViewResource(R.layout.spinner_item);
        spin_type_of_report.setAdapter(adapterType);


        final String[] eventStr2 = getResources().getStringArray(R.array.choice);
        ArrayAdapter<String> adapterChoice = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, eventStr2);
        adapterChoice.setDropDownViewResource(R.layout.spinner_item);
        spin_place_mom.setAdapter(adapterChoice);


        btn_submit_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("ทั้งหมด")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("หมูสาวยังไม่ได้ผสม")){
                            lengthtime = edt_lengthtime.getText().toString();
                            if (edt_checkpregnant.getText().toString() == ""){
                                 edt_checkpregnant.setText("35");
                                }

                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            String pdf = "http://pigaboo.xyz/Report/Momnotbreed_Report.php?farm_id=" + farm_id+"&lengthtime="+lengthtime+"&checkpregnant="+edt_checkpregnant.getText().toString();
                            intent.putExtra("url",pdf);
                            startActivity(intent);



                            }



                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("ผสม")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("ไม่ท้อง")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("กำหนดเข้าคลอด")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("ต้องทำก่อนเข้าคลอด")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("คลอด")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("หย่านท")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("แม่ต้องทำวัคซีน")){

                        }
                    }

        });


    }
}