package com.example.admin.pigfarm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.R;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Register extends AppCompatActivity {

    EditText edit_owner, edit_user, edit_pass, edit_farm, edit_unit;
    Button btn_saveregis;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edit_owner = findViewById(R.id.edit_owner);
        edit_user = findViewById(R.id.edit_user);
        edit_pass = findViewById(R.id.edit_pass);
        edit_farm = findViewById(R.id.edit_farm);
        edit_unit = findViewById(R.id.edit_unit);
        btn_saveregis = findViewById(R.id.btn_saveregis);

        btn_saveregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InsertAsyn().execute("http://pigaboo.xyz/Register2.php");
            }
        });

    }
    private class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("farm_name", edit_farm.getText().toString())
                        .add("farm_owner", edit_owner.getText().toString())
                        .add("username", edit_user.getText().toString())
                        .add("password", edit_pass.getText().toString())
                        .add("unit_name", edit_unit.getText().toString())
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
                Toast.makeText(Register.this, "สมัครสมาชิกเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Register.this, LoginActivity.class);
                startActivity(i);
                finish();

            }else {
                Toast.makeText(Register.this, "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
