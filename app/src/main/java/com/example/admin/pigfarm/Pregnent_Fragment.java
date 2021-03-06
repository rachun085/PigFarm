package com.example.admin.pigfarm;


import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class Pregnent_Fragment extends Fragment {
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    public static String gettextbreed,farm_id;
    Spinner spin_noteId03;
    EditText edit_dateNote03,edit_msg03;
    Button btn_flacAct03;
    private String[] event_date_array;
    private String[] pig_id_array;
    private List<String> mStrings_pig_id = new ArrayList<String>();
    private List<String> mStrings_event_date = new ArrayList<String>();
    private int pig_id_dropdown;
    ImageView img_calNote03;
    Calendar myCalendar = Calendar.getInstance();



    public Pregnent_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_act03, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            gettextbreed = getArguments().getString("textbreed");
            farm_id = getArguments().getString("farm_id");
            Toast.makeText(getActivity(), gettextbreed, Toast.LENGTH_SHORT).show();
        }

            spin_noteId03 = getView().findViewById(R.id.spin_noteId03);
            edit_dateNote03 = getView().findViewById(R.id.edit_dateNote03);
            edit_msg03 = getView().findViewById(R.id.edit_msg03);
            btn_flacAct03 = getView().findViewById(R.id.btn_flacAct03);
            img_calNote03 = getView().findViewById(R.id.img_calNote03);

        String date_n = new SimpleDateFormat("yyyy/MM/dd",
                Locale.getDefault()).format(new Date());
        edit_dateNote03.setText(date_n);

            String url = "http://pigaboo.xyz/Query_BreedID.php?farm_id="+farm_id;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    QueryAbort(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "ไม่สามารถดึงข้อมูลได้", Toast.LENGTH_SHORT).show();
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
            requestQueue.add(stringRequest);

            btn_flacAct03.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new InsertAsyn().execute("http://pigaboo.xyz/Insert_EventPregnant.php");
                }
            });

        img_calNote03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }


    private class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                spin_noteId03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        pig_id_dropdown = adapterView.getSelectedItemPosition();
                        Log.d("pig_123","value: "+pig_id_dropdown);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                OkHttpClient _okHttpClient = new OkHttpClient();
                RequestBody _requestBody = new FormBody.Builder()
                        .add("event_id", "3")
                        .add("event_recorddate", edit_dateNote03.getText().toString())
                        .add("pig_id", pig_id_array[pig_id_dropdown])
                        .add("note", edit_msg03.getText().toString())
                        .add("event_recorddate_for_abort", event_date_array[pig_id_dropdown])

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
                edit_msg03.setText("");

            }else {
                Toast.makeText(getActivity(), "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void QueryAbort(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");


            for (int i = 0; i < result.length(); i++) {
                JSONObject collectData = result.getJSONObject(i);
                mStrings_pig_id.add(collectData.getString("pig_id")) ;
                pig_id_array = new String[mStrings_pig_id.size()];
                pig_id_array = mStrings_pig_id.toArray(pig_id_array);
                list.add(collectData.getString("pig_id"));

                mStrings_event_date.add(collectData.getString("event_recorddate"));
                event_date_array = new String[mStrings_event_date.size()];
                event_date_array = mStrings_event_date.toArray(event_date_array);
            }

        }catch (JSONException ex) {
            ex.printStackTrace();
        }

        listItems.addAll(list);
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, listItems);
        spin_noteId03.setAdapter(adapter);
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
            edit_dateNote03.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
        }
    };
}
