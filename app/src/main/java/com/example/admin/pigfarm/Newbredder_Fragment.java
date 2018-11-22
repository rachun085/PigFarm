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

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class Newbredder_Fragment extends Fragment {

    EditText edit_id3, edit_opendate3, edit_birthday3, edit_breed3, edit_dadId3, edit_momId3, edit_form3, edit_reserveID3;
    Button btn_saveBio3;
    String farm_id;


    public Newbredder_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bredder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences shared = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        farm_id = shared.getString("farm_id", "missing");


        bindWidget();
    }

    private void bindWidget() {
        edit_id3 = getView().findViewById(R.id.edit_id3);
        edit_opendate3 = getView().findViewById(R.id.edit_opendate3);
        edit_birthday3= getView().findViewById(R.id.edit_birthday3);
        edit_breed3 = getView().findViewById(R.id.edit_breed3);
        edit_dadId3= getView().findViewById(R.id.edit_dadId3);
        edit_momId3 = getView().findViewById(R.id.edit_momId3);
        edit_form3 = getView().findViewById(R.id.edit_form3);
        edit_reserveID3 = getView().findViewById(R.id.edit_reserveID3);
        btn_saveBio3 = getView().findViewById(R.id.btn_saveBio3);

        btn_saveBio3.setOnClickListener(onSubmitClickListener);
    }

    private View.OnClickListener onSubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new InsertAsyn().execute("http://pigaboo.xyz/Insert_ProfilePig.php");

        }
    };

    private class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("pig_id", edit_id3.getText().toString())
                        .add("pig_recorddate", edit_opendate3.getText().toString())
                        .add("pig_birthday", edit_birthday3.getText().toString())
                        .add("pig_breed", edit_breed3.getText().toString())
                        .add("pig_idbreeder", edit_dadId3.getText().toString())
                        .add("pig_idbreeder2", edit_momId3.getText().toString())
                        .add("pig_from", edit_form3.getText().toString())
                        .add("pig_idreserve", edit_reserveID3.getText().toString())
                        .add("farm_id",farm_id)
                        .add("idrecordtype_pig","3")
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
                edit_id3.setText("");
                edit_opendate3.setText("");
                edit_birthday3.setText("");
                edit_breed3.setText("");
                edit_dadId3.setText("");
                edit_momId3.setText("");
                edit_form3.setText("");
                edit_reserveID3.setText("");
            }else {
                Toast.makeText(getActivity(), "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
