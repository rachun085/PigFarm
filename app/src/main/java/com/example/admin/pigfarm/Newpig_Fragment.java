package com.example.admin.pigfarm;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.R;
import com.example.admin.pigfarm.SelectDateFragment;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class Newpig_Fragment extends Fragment {

    Button btnSubmitNewpig;
    EditText edit_id, edit_opendate, edit_birthday, edit_breed, edit_dadId, edit_momId, edit_form, edit_reserveID;
    String farm_id;
    ImageView img_calOpen1, img_calBD1;
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    public Newpig_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_newpig, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences shared = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        farm_id = shared.getString("farm_id", "missing");

        edit_id = getView().findViewById(R.id.edit_id1);
        edit_opendate = getActivity().findViewById(R.id.edit_opendate1);
        edit_birthday = getView().findViewById(R.id.edit_birthday1);
        edit_breed = getView().findViewById(R.id.edit_breed1);
        edit_dadId = getView().findViewById(R.id.edit_dadId1);
        edit_momId = getView().findViewById(R.id.edit_momId1);
        edit_form = getView().findViewById(R.id.edit_form1);
        edit_reserveID = getView().findViewById(R.id.edit_reserveID);
        btnSubmitNewpig = getView().findViewById(R.id.btn_youngpig);
        img_calOpen1 = getView().findViewById(R.id.img_calOpen1);
        img_calBD1 = getView().findViewById(R.id.img_calBD1);

        String date_n = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date());
        edit_opendate.setText(date_n);


        btnSubmitNewpig.setOnClickListener(onSubmitClickListener);

        img_calOpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        img_calBD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }
        });
    }

    public void showDatePickerDialog(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear = monthOfYear + 1;
            edit_opendate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };

    public void showDatePickerDialog2(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, monthOfYear);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            monthOfYear = monthOfYear + 1;
            edit_birthday.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };


    private View.OnClickListener onSubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new InsertAsyn().execute("http://pigaboo.xyz/Insert_ProfilePig.php");

        }
    };

    public class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("pig_id", edit_id.getText().toString())
                        .add("pig_recorddate", edit_opendate.getText().toString())
                        .add("pig_birthday", edit_birthday.getText().toString())
                        .add("pig_breed", edit_breed.getText().toString())
                        .add("pig_idbreeder", edit_dadId.getText().toString())
                        .add("pig_idbreeder2", edit_momId.getText().toString())
                        .add("pig_from", edit_form.getText().toString())
                        .add("pig_idreserve", edit_reserveID.getText().toString())
                        .add("farm_id",farm_id)
                        .add("idrecordtype_pig","1")
                        .build();

                Request _request = new Request.Builder().url(strings[0]).post(_requestBody).build();
                _okHttpClient.newCall(_request).execute();
                return "successfully";
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null){
                Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                edit_id.setText("");
                edit_birthday.setText("");
                edit_breed.setText("");
                edit_dadId.setText("");
                edit_momId.setText("");
                edit_form.setText("");
                edit_reserveID.setText("");
            }else {
                Toast.makeText(getActivity(), "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
