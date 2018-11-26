package com.example.admin.pigfarm;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Reprofile_Fragment extends Fragment {

    EditText edit_id2, edit_pregNo2, edit_opendate2, edit_birthday2, edit_breed2, edit_dadId2, edit_momId2, edit_form2, edit_reserveID2;
    Button btn_saveBio2;
    String farm_id;


    public Reprofile_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences shared = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        farm_id = shared.getString("farm_id", "missing");


        bindWidget();
    }

    private void bindWidget() {
        edit_id2 = getView().findViewById(R.id.edit_id2);
        edit_pregNo2 = getView().findViewById(R.id.edit_pregNo2);
        edit_opendate2 = getView().findViewById(R.id.edit_opendate2);
        edit_birthday2 = getView().findViewById(R.id.edit_birthday2);
        edit_breed2 = getView().findViewById(R.id.edit_breed2);
        edit_dadId2 = getView().findViewById(R.id.edit_dadId2);
        edit_momId2 = getView().findViewById(R.id.edit_momId2);
        edit_form2 = getView().findViewById(R.id.edit_form2);
        edit_reserveID2 = getView().findViewById(R.id.edit_reserveID2);
        btn_saveBio2 = getView().findViewById(R.id.btn_saveBio2);

        String date_n = new SimpleDateFormat("yyyy/MM/dd",
                Locale.getDefault()).format(new Date());
        edit_opendate2.setText(date_n);

        btn_saveBio2.setOnClickListener(onSubmitClickListener);
    }

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
                        .add("pig_id", edit_id2.getText().toString())
                        .add("pig_preglist", edit_pregNo2.getText().toString())
                        .add("pig_recorddate", edit_opendate2.getText().toString())
                        .add("pig_birthday", edit_birthday2.getText().toString())
                        .add("pig_breed", edit_breed2.getText().toString())
                        .add("pig_idbreeder", edit_dadId2.getText().toString())
                        .add("pig_idbreeder2", edit_momId2.getText().toString())
                        .add("pig_from", edit_form2.getText().toString())
                        .add("pig_idreserve", edit_reserveID2.getText().toString())
                        .add("farm_id",farm_id)
                        .add("idrecordtype_pig","2")
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
                edit_id2.setText("");
                edit_breed2.setText("");
                edit_opendate2.setText("");
                edit_birthday2.setText("");
                edit_breed2.setText("");
                edit_dadId2.setText("");
                edit_momId2.setText("");
                edit_form2.setText("");
                edit_reserveID2.setText("");
            }else {
                Toast.makeText(getActivity(), "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
