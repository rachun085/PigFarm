package com.example.admin.pigfarm.Report;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Report_Group extends AppCompatActivity {

    private String farm_id,pdf;
    Spinner spin_type_of_report, spin_place_mom;
    Button btn_submit_report;
    EditText edt_lengthtime, edt_checkpregnant, edt_reportmom, edt_startdate, edt_enddate;
    private String lengthtime,checkpregnant;
    Calendar StartDate = Calendar.getInstance();
    Calendar EndDate = Calendar.getInstance();
    ImageView calendar_start, calendar_end;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_group);

        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        farm_id = farm.getString("farm_id", "");


        spin_type_of_report = findViewById(R.id.spin_type_of_report);
        spin_place_mom = findViewById(R.id.spin_place_mom);
        btn_submit_report = findViewById(R.id.btn_submit_report);
        edt_checkpregnant = findViewById(R.id.edt_checkpregnant);
        edt_reportmom = findViewById(R.id.edt_reportmom);
        edt_startdate = findViewById(R.id.edt_startdate);
        edt_enddate = findViewById(R.id.edt_enddate);
        calendar_start = findViewById(R.id.calendar_start);
        calendar_end = findViewById(R.id.calendar_end);

        String date_start = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());
        edt_startdate.setText(date_start);

        String date_end = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());
        edt_enddate.setText(date_end);

        calendar_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker_Start();
            }
        });

        calendar_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker_End();
            }
        });

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
                            checkpregnant = edt_checkpregnant.getText().toString();

                            pdf = "http://pigaboo.xyz/Report/All_Report.php";
                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            intent.putExtra("url",pdf);
                            intent.putExtra("start_date",edt_startdate.getText().toString());
                            intent.putExtra("end_date",edt_enddate.getText().toString());
                            startActivity(intent);
                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("หมูสาวยังไม่ได้ผสม")){
                            checkpregnant = edt_checkpregnant.getText().toString();

                            pdf = "http://pigaboo.xyz/Report/Momnotbreed_Report.php";
                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            intent.putExtra("url",pdf);
                            intent.putExtra("start_date",edt_startdate.getText().toString());
                            intent.putExtra("end_date",edt_enddate.getText().toString());
                            startActivity(intent);


                            }


                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("ผสม")){
                            checkpregnant = edt_checkpregnant.getText().toString();

                            pdf = "http://pigaboo.xyz/Report/Mombreed_Report.php";
                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            intent.putExtra("url",pdf);
                            intent.putExtra("start_date",edt_startdate.getText().toString());
                            intent.putExtra("end_date",edt_enddate.getText().toString());
                            startActivity(intent);

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("ไม่ท้อง")){
                            checkpregnant = edt_checkpregnant.getText().toString();

                            pdf = "http://pigaboo.xyz/Report/NoPregnant_Report.php";
                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            intent.putExtra("url",pdf);
                            intent.putExtra("start_date",edt_startdate.getText().toString());
                            intent.putExtra("end_date",edt_enddate.getText().toString());
                            startActivity(intent);

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("กำหนดเข้าคลอด")){
                            checkpregnant = edt_checkpregnant.getText().toString();

                            pdf = "http://pigaboo.xyz/Report/DueDate_Pregnant_Report.php";
                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            intent.putExtra("url",pdf);
                            intent.putExtra("start_date",edt_startdate.getText().toString());
                            intent.putExtra("end_date",edt_enddate.getText().toString());
                            startActivity(intent);

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("ต้องทำก่อนเข้าคลอด")){

                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("คลอด")){
                            checkpregnant = edt_checkpregnant.getText().toString();

                            pdf = "http://pigaboo.xyz/Report/Maternity_but_notwean_Report.php";
                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            intent.putExtra("url",pdf);
                            intent.putExtra("start_date",edt_startdate.getText().toString());
                            intent.putExtra("end_date",edt_enddate.getText().toString());
                            startActivity(intent);
                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("หย่านม")){
                            checkpregnant = edt_checkpregnant.getText().toString();

                            pdf = "http://pigaboo.xyz/Report/Wean_not_Breed_Report.php";
                            Intent intent = new Intent(Report_Group.this, PigData_Report.class);
                            intent.putExtra("url",pdf);
                            intent.putExtra("start_date",edt_startdate.getText().toString());
                            intent.putExtra("end_date",edt_enddate.getText().toString());
                            startActivity(intent);
                        }

                        else if (String.valueOf(spin_type_of_report.getSelectedItem()).equals("แม่ต้องทำวัคซีน")){

                        }
                    }

        });


    }

    public void showDatePicker_Start(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(Report_Group.this, date, StartDate.get(Calendar.YEAR), StartDate.get(Calendar.MONTH), StartDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            StartDate.set(Calendar.YEAR, year);
            StartDate.set(Calendar.MONTH, monthOfYear);
            StartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear = monthOfYear + 1;
            edt_startdate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };

    public void showDatePicker_End(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(Report_Group.this, datee, EndDate.get(Calendar.YEAR), EndDate.get(Calendar.MONTH), EndDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener datee = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            EndDate.set(Calendar.YEAR, year);
            EndDate.set(Calendar.MONTH, monthOfYear);
            EndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear = monthOfYear + 1;
            edt_enddate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };




}