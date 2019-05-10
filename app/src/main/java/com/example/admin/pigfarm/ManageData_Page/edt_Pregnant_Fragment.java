package com.example.admin.pigfarm.ManageData_Page;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class edt_Pregnant_Fragment extends Fragment {

    String get_detail_id,getfarm_id,event_recorddate,event_name,note;
    EditText edit_eventname,edit_dateNote03,edit_msg03;
    Button btn_flacAct03;
    ImageView img_calNote03;
    ArrayList<String> listDad = new ArrayList<>();
    ArrayList<String> listItemsDad = new ArrayList<>();
    ProgressDialog progressDialog;
    private String finalResult;
    private HttpParse httpParse = new HttpParse();
    ArrayAdapter<String> adapter;
    Calendar myCalendar = Calendar.getInstance();
    String UpdateURL = "http://pigaboo.xyz/Update_Event.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edt_pregnant_evt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences farm = this.getActivity().getSharedPreferences("Farm", Context.MODE_PRIVATE);
        getfarm_id = farm.getString("farm_id","");


        Bundle bundle2 = getArguments();
        get_detail_id = bundle2.getString("detail_id");

        edit_eventname = getView().findViewById(R.id.edit_eventname);
        edit_dateNote03 = getView().findViewById(R.id.edit_dateNote03);
        btn_flacAct03 = getView().findViewById(R.id.btn_flacAct03);
        img_calNote03 = getView().findViewById(R.id.img_calNote03);
        edit_msg03 = getView().findViewById(R.id.edit_msg03);

        img_calNote03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        LoadData();

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
            edit_dateNote03.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };

    private void LoadData() {
        String url = "http://pigaboo.xyz/Query_PigID_By_Detail_ID.php?detail_id="+get_detail_id;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                QueryDataPregnant(response);

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

    private void QueryDataPregnant(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject collectData = result.getJSONObject(i);

                event_recorddate = collectData.getString("event_recorddate");
                event_name = collectData.getString("event_name");
                note = collectData.getString("note");


                edit_eventname.setText(event_name);
                edit_dateNote03.setText(event_recorddate);
                edit_msg03.setText(note);

            }

            btn_flacAct03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetDataFromEditText();
                    update_data(event_recorddate, note, get_detail_id);
                }
            });


        }catch(JSONException ex){
            ex.printStackTrace();
        }
    }

    private void GetDataFromEditText() {
        event_recorddate = edit_dateNote03.getText().toString();
        note = edit_msg03.getText().toString();
    }

    private void update_data(String event_recorddate, String note, String get_detail_id) {

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
                hashMap.put("note",params[2]);


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
        update_dataclass.execute(get_detail_id,event_recorddate,note);


    }
}
