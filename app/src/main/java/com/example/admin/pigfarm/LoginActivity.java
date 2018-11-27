package com.example.admin.pigfarm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    private int progressDurum = 0;
    private Handler handler = new Handler();
    private ProgressBar progresslogin;
    private static final int REFRESH_SCREEN = 1;
    String username,password,url;
    Button btnSubmit;
    TextView txtUsername,txtPassword,linkregis;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnSubmit =findViewById(R.id.btnSubmit);
        progresslogin = findViewById(R.id.progressLogin);
        progresslogin.setVisibility(View.INVISIBLE);
        linkregis = findViewById(R.id.linkregis);

        linkregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(LoginActivity.this, Register.class);
                startActivity(intent2);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               username = txtUsername.getText().toString();
               password = txtPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setCancelable(true);
                    builder.setMessage("กรุณากรอกข้อมูลให้ครบ");
                    builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    android.app.AlertDialog dialog = builder.create();
                    dialog.show();


                } else {

                    login(username, password);

                }
            }
        });
    }


    public void login(final String username, final String password){
        url = "http://pigaboo.xyz/login2.php?username="+username+"&password="+password;
        Log.i("Hiteshurl",""+url);
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                    String farm_id = jsonObject1.getString("farm_id");
                    String user = jsonObject1.getString("username");
                    String pass = jsonObject1.getString("password");

                    SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shared.edit();

                    editor.putString("pass",pass);
                    editor.putString("username",user);
                    editor.putString("farm_id",farm_id);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "ยินดีต้อนรับ! "+username, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, Home.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("HiteshURLerror",""+error);
            }
        });

        requestQueue.add(stringRequest);

    }
}

