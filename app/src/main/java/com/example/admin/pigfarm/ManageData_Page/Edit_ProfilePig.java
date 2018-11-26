package com.example.admin.pigfarm.ManageData_Page;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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


import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Edit_ProfilePig extends Fragment {

    String getpigno, getpigid, pig_no, pig_id, pig_preglist, pig_recorddate, pig_birthday, pig_breed, pig_idbreeder, pig_idbreeder2, pig_from, pig_idreserve;
    EditText edit_earID,edit_new,edit_bd,edit_gene,edit_dadNo,edit_momNo,edit_location,edit_spareID,edit_preglist;
    TextView txt_preglist;
    Button btn_update;
    ProgressDialog progressDialog;
    String finalResult;
    HttpParse httpParse = new HttpParse();
    String UpdateURL = "http://pigaboo.xyz/Update_DataPig.php";


    public Edit_ProfilePig() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_profilepig, container, false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle2 = getArguments();
        getpigno = bundle2.getString("pig_no");
        getpigid = bundle2.getString("pig_id");

        edit_earID = getView().findViewById(R.id.edit_earID);
        edit_new = getView().findViewById(R.id.edit_new);
        edit_bd = getView().findViewById(R.id.edit_bd);
        edit_gene = getView().findViewById(R.id.edit_gene);
        edit_dadNo = getView().findViewById(R.id.edit_dadNo);
        edit_momNo = getView().findViewById(R.id.edit_momNo);
        edit_location = getView().findViewById(R.id.edit_location);
        edit_spareID = getView().findViewById(R.id.edit_spareID);
        edit_preglist = getView().findViewById(R.id.edit_preglist);
        txt_preglist = getView().findViewById(R.id.txt_preglist);
        btn_update = getView().findViewById(R.id.btn_update);



        loaddatapigtoedit();
    }

    private void loaddatapigtoedit(){
        String url = "http://pigaboo.xyz/Query_DataPig.php?pig_id="+getpigid;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "ไม่สามารถดึงข้อมูลได้ โปรดตรวจสอบการเชื่อมต่อ", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this.getContext().getApplicationContext());
        requestQueue.add(stringRequest);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);

                pig_no = collectData.getString("pig_no");
                pig_id = collectData.getString("pig_id");
                pig_preglist = collectData.getString("pig_preglist");
                pig_recorddate = collectData.getString("pig_recorddate");
                pig_birthday = collectData.getString("pig_birthday");
                pig_breed = collectData.getString("pig_breed");
                pig_idbreeder = collectData.getString("pig_idbreeder");
                pig_idbreeder2 = collectData.getString("pig_idbreeder2");
                pig_idreserve = collectData.getString("pig_idreserve");
                pig_from = collectData.getString("pig_from");


                edit_earID.setText(getpigid);
                edit_new.setText(pig_recorddate);
                edit_bd.setText(pig_birthday);
                edit_gene.setText(pig_breed);
                edit_dadNo.setText(pig_idbreeder);
                edit_momNo.setText(pig_idbreeder2);
                edit_location.setText(pig_idreserve);
                edit_spareID.setText(pig_from);
                if(pig_preglist.isEmpty()){
                    txt_preglist.setVisibility(View.INVISIBLE);
                    edit_preglist.setVisibility(View.INVISIBLE);
                }else {
                    edit_preglist.setVisibility(View.VISIBLE);
                    txt_preglist.setVisibility(View.VISIBLE);
                    edit_preglist.setText(pig_preglist);
                }

            }
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataFromEditText();
                    update_data(getpigno,getpigid,pig_recorddate,pig_birthday,pig_breed,pig_idbreeder,pig_idbreeder2,pig_from,pig_idreserve);
                }
            });


        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void GetDataFromEditText(){
        getpigid = edit_earID.getText().toString();
        pig_recorddate = edit_new.getText().toString();
        pig_birthday = edit_bd.getText().toString();
        pig_breed = edit_gene.getText().toString();
        pig_idbreeder = edit_dadNo.getText().toString();
        pig_idbreeder2 = edit_momNo.getText().toString();
        pig_from = edit_location.getText().toString();
        pig_idreserve = edit_spareID.getText().toString();
    }


    private void update_data (String pig_no, String pig_id, String pig_recorddate, String pig_birthday, String pig_breed, String pig_idbreeder, String pig_idbreeder2, String pig_from, String pig_idreserve){
        class update_dataClass extends AsyncTask<String,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(getActivity(),httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("getpigno",params[0]);
                hashMap.put("edit_earID",params[1]);
                hashMap.put("edit_new",params[2]);
                hashMap.put("edit_bd",params[3]);
                hashMap.put("edit_gene",params[4]);
                hashMap.put("edit_dadNo",params[5]);
                hashMap.put("edit_momNo",params[6]);
                hashMap.put("edit_location",params[7]);
                hashMap.put("edit_spareID",params[8]);

                finalResult = httpParse.postRequest(hashMap,UpdateURL);
                return finalResult;
            }
        }

        update_dataClass update_dataclass = new update_dataClass();
        update_dataclass.execute(pig_no,pig_id,pig_recorddate,pig_birthday,pig_breed,pig_idbreeder,pig_idbreeder2,pig_from,pig_idreserve);

    }

}

