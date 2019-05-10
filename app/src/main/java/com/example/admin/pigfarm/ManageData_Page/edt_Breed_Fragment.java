package com.example.admin.pigfarm.ManageData_Page;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.R;
import com.example.admin.pigfarm.ManageData_Page.HttpParse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.admin.pigfarm.Breed_Fragment.farm_id;

public class edt_Breed_Fragment extends Fragment {

    String get_detail_id,pig_breeder,event_recorddate,event_name,getfarm_id;
    EditText edit_evtname,edit_dateNote01;
    Spinner spin_dadId01;
    ArrayList<String> listDad = new ArrayList<>();
    ArrayList<String> listItemsDad = new ArrayList<>();
    ProgressDialog progressDialog;
    private String finalResult;
    private HttpParse httpParse = new HttpParse();
    ArrayAdapter<String> adapter;
    Button btn_flacAct01;
    ImageView img_calNote01;
    Calendar myCalendar = Calendar.getInstance();
    String UpdateURL = "http://pigaboo.xyz/Update_Event.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edt_breed_evt, container, false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences farm = this.getActivity().getSharedPreferences("Farm", Context.MODE_PRIVATE);
        getfarm_id = farm.getString("farm_id","");


        Bundle bundle2 = getArguments();
        get_detail_id = bundle2.getString("detail_id");

        edit_evtname = getView().findViewById(R.id.edit_evtname);
        edit_dateNote01 = getView().findViewById(R.id.edit_dateNote01);
        spin_dadId01 = getView().findViewById(R.id.spin_dadId01);
        btn_flacAct01 = getView().findViewById(R.id.btn_flacAct01);
        img_calNote01 = getView().findViewById(R.id.img_calNote01);



        img_calNote01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        LoadData();
        LoadDataDadID();
    }




    private void showDatePickerDialog() {
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
            edit_dateNote01.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };

    private void LoadData() {
        String url = "http://pigaboo.xyz/Query_PigID_By_Detail_ID.php?detail_id="+get_detail_id;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                QueryDataBreed(response);

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


    private void LoadDataDadID() {
        String url2 = "http://pigaboo.xyz/Query_DadID.php?farm_id="+getfarm_id;
        StringRequest stringRequest1 = new StringRequest(url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                QueryDadID(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ไม่สามารถดึงข้อมูลได้", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue1 = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        requestQueue1.add(stringRequest1);
    }

    private void QueryDadID(String response) {
        try {
            JSONObject jsonObject2 = new JSONObject(response);
            JSONArray result2 = jsonObject2.getJSONArray("result");

            if (result2.length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setCancelable(false);
                builder.setMessage("ท่านยังไม่ได้เปิดประวัติพ่อพันธุ์");
                builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

            for (int i = 0; i<result2.length(); i++){
                JSONObject collectData2 = result2.getJSONObject(i);
                listDad.add(collectData2.getString("pig_id"));
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }

        listItemsDad.addAll(listDad);
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, listItemsDad);
        spin_dadId01.setAdapter(adapter);
    }

    private void QueryDataBreed(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject collectData = result.getJSONObject(i);

                pig_breeder = collectData.getString("pig_breeder");
                event_recorddate = collectData.getString("event_recorddate");
                event_name = collectData.getString("event_name");


                edit_evtname.setText(event_name);
                edit_dateNote01.setText(event_recorddate);
                }

            btn_flacAct01.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetDataFromEditText();
                        update_data(event_recorddate, pig_breeder, get_detail_id);
                    }
                });


            }catch(JSONException ex){
                ex.printStackTrace();
            }
        }


    private void GetDataFromEditText() {
        event_recorddate = edit_dateNote01.getText().toString();
        pig_breeder = spin_dadId01.getSelectedItem().toString();
    }

    private void update_data(String event_recorddate, String pig_breeder, String get_detail_id) {
        class update_dataClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(getActivity(),"กำลังอัพเดตข้อมูล...",null,true,true);
                super.onPreExecute();

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("detail_id",params[0]);
                hashMap.put("event_recorddate",params[1]);
                hashMap.put("pig_breeder",params[2]);


                finalResult = httpParse.postRequest(hashMap,UpdateURL);
                return finalResult;
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                Toast.makeText(getActivity(),httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }
        }

        update_dataClass update_dataclass = new update_dataClass();
        update_dataclass.execute(get_detail_id,event_recorddate,pig_breeder);

    }
}

