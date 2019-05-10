package com.example.admin.pigfarm;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.text.DateFormat;
import java.text.ParseException;
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


public class Breed_Fragment extends Fragment {
    Spinner spin_noteId01,spin_dadId01;
    EditText edit_dateNote01;
    Button btn_flacAct01;
    String record_date,recorddate,getamount;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listDad = new ArrayList<>();
    ArrayList<String> listItems = new ArrayList<>();
    ArrayList<String> listItemsDad = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;
    public static String gettextbreed,farm_id;
    ImageView img_calNote01;
    Calendar myCalendar = Calendar.getInstance();
    Date date_before,date_record;
    Object pregnant;
    private String[] event_date_array;
    private String[] pig_id_array;
    private List<String> mStrings_pig_id = new ArrayList<String>();
    private List<String> mStrings_event_date = new ArrayList<String>();
    private String[] amount_pregnant_array;
    private List<String> mStrings_pregnant = new ArrayList<String>();
    private int pig_id_dropdown,amount;
    private List<String> sum_amount_pregnant = new ArrayList<String>();

    public Breed_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_act01, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            gettextbreed = getArguments().getString("textbreed");
            farm_id = getArguments().getString("farm_id");
            Toast.makeText(getActivity(), gettextbreed, Toast.LENGTH_SHORT).show();
        }

        spin_noteId01 = getView().findViewById(R.id.spin_noteId01);
        spin_dadId01 = getView().findViewById(R.id.spin_dadId01);
        edit_dateNote01 = getView().findViewById(R.id.edit_dateNote01);
        btn_flacAct01 = getView().findViewById(R.id.btn_flacAct01);
        img_calNote01 = getView().findViewById(R.id.img_calNote01);

        String date_n = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        edit_dateNote01.setText(date_n);



            String url = "http://pigaboo.xyz/Query_pigid.php?farm_id="+farm_id;
            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    showJSON(response);
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



            String url2 = "http://pigaboo.xyz/Query_DadID.php?farm_id="+farm_id;
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





        btn_flacAct01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spin_dadId01 != null && spin_dadId01.getSelectedItem() != null) {
                    String url3 = "http://pigaboo.xyz/Query_AmountPregnantById.php?farm_id="+farm_id+"&pig_id="+spin_noteId01.getSelectedItem().toString();
                    StringRequest stringRequest2 = new StringRequest(url3, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            QueryAmountPregnant(response);
                            new InsertAsyn().execute("http://pigaboo.xyz/Insert_EventBreed.php");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    );
                    RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity().getApplicationContext());
                    requestQueue2.add(stringRequest2);



              }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                    builder.setCancelable(false);
                    builder.setMessage("กรุณากรอกข้อมูลให้ครบถ้วน");
                    builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });



        img_calNote01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        spin_noteId01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                pig_id_dropdown = adapterView.getSelectedItemPosition();
                Log.d("pig_123","value: "+pig_id_dropdown);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
            edit_dateNote01.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    };

    public void QueryAmountPregnant(String response){
        try {
            JSONObject jsonObject3 = new JSONObject(response);
            JSONArray result3 = jsonObject3.getJSONArray("result");

            for (int i = 0; i<result3.length(); i++){
                JSONObject collectData3 = result3.getJSONObject(i);
                getamount = collectData3.getString("pig_amount_pregnant");
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


    private class InsertAsyn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try{

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date_before = formatter.parse(event_date_array[pig_id_dropdown]);
                date_record = formatter.parse(edit_dateNote01.getText().toString());

                if (date_record.getTime() < date_before.getTime()){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                            builder.setCancelable(false);
                            builder.setMessage("กรอกวันที่น้อยกว่าวันที่เข้าฟาร์มไม่ได้");
                            builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                    );
                return null;

                }else{
//                    amount = Integer.parseInt(amount_pregnant_array[pig_id_dropdown]);
                    OkHttpClient _okHttpClient = new OkHttpClient();
                    RequestBody _requestBody = new FormBody.Builder()
                            .add("event_id", "1")
                            .add("event_recorddate", edit_dateNote01.getText().toString())
                            .add("pig_id", spin_noteId01.getSelectedItem().toString())
                            .add("pig_breeder", spin_dadId01.getSelectedItem().toString())
                            .add("pig_amount_pregnant", getamount)
                            .build();

                    Request _request = new Request.Builder().url(strings[0]).post(_requestBody).build();
                    _okHttpClient.newCall(_request).execute();

                }
                return "successfully";
            }catch(IOException e){
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null){
                Toast.makeText(getActivity(), "บันทึกข้อมูลเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "ไม่สามารถบันทึกข้อมูลได้",Toast.LENGTH_SHORT).show();
            }
        }
    }


       public void showJSON(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            if (result.length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
                builder.setCancelable(false);
                builder.setMessage("ท่านยังไม่ได้เปิดประวัติสุกร");
                builder.setNegativeButton("ตกลง", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);
                mStrings_pig_id.add(collectData.getString("pig_id"));
                pig_id_array = new String[mStrings_pig_id.size()];
                pig_id_array = mStrings_pig_id.toArray(pig_id_array);
                list.add(collectData.getString("pig_id"));

                mStrings_event_date.add(collectData.getString("pig_recorddate"));
                event_date_array = new String[mStrings_event_date.size()];
                event_date_array = mStrings_event_date.toArray(event_date_array);
            }
        }catch (JSONException ex) {
            ex.printStackTrace();
        }

        listItems.addAll(list);
           adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, listItems);
           spin_noteId01.setAdapter(adapter);
    }



    public void QueryDadID(String response){
        try {
            JSONObject jsonObject2 = new JSONObject(response);
            JSONArray result2 = jsonObject2.getJSONArray("result");

            if (result2.length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
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
        adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, listItemsDad);
        spin_dadId01.setAdapter(adapter1);
    }







}
