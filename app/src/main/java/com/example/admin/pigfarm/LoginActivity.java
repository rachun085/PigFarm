package com.example.admin.pigfarm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    private Handler handler = new Handler();
    String username,password,url;
    Button btnSubmit;
    private ProgressDialog progressbar;
    TextView txtUsername,txtPassword,linkregis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnSubmit =findViewById(R.id.btnSubmit);


//        linkregis = findViewById(R.id.linkregis);

//        linkregis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent2 = new Intent(LoginActivity.this, Register.class);
//                startActivity(intent2);
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar = new ProgressDialog(LoginActivity.this);
                progressbar.setMessage("กรุณารอสักครู่...");
                progressbar.setCancelable(false);
                progressbar.setIndeterminate(true);


               username = txtUsername.getText().toString();
               password = txtPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty() ) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                    builder.setCancelable(true);
                    builder.setMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
                    builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();


                } else {
                    progressbar.show();
                    url = "http://pigaboo.xyz/login2.php?username="+username+"&password="+password;
                    StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Login(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    );
                    RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this.getApplicationContext());
                    requestQueue.add(stringRequest);

                }
            }
        });
    }

    public void Login(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            if (result.length() == 0) {
                progressbar.dismiss();
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setCancelable(false);
                builder.setMessage("ชื่อผู้ใช้ / รหัสผ่านไม่ถูกต้อง");
                builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);
                String farm_id = collectData.getString("farm_id");
                String user = collectData.getString("username");
                String pass = collectData.getString("password");
                String farm_name = collectData.getString("farm_name");
                String unit_name = collectData.getString("unit_name");


                SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();

                editor.putString("pass",pass);
                editor.putString("username",user);
                editor.putString("farm_id",farm_id);
                editor.commit();

                SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = farm.edit();
                editor2.putString("farm_name",farm_name);
                editor2.putString("unit_name",unit_name);
                editor2.putString("farm_id",farm_id);
                editor2.commit();

                Toast.makeText(LoginActivity.this, "ยินดีต้อนรับ! "+username, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, Home.class);
                startActivity(intent);
                finish();
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }

    }

}

