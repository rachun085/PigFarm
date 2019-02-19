package com.example.admin.pigfarm.ManageData_Page;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.List;

public class DelEvent_Fragment extends android.support.v4.app.Fragment implements Eventdel_Adapter.OnItemLongClickListener{

    private RecyclerView rv2;
    List<DelEvent_items> delList;
    String getpigno, getpigid,event_name,event_recorddate, event_id;
    Eventdel_Adapter eventdel_adapter;
    ProgressDialog progressDialog2;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult;
    String finalResult;
    HttpParse httpParse = new HttpParse();
    String DeleteEvent = "http://pigaboo.xyz/Delete_Eventdata.php";



    public DelEvent_Fragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview_eventtodel, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle2 = getArguments();
        getpigno = bundle2.getString("pig_no");
        getpigid = bundle2.getString("pig_id");

        rv2 = getView().findViewById(R.id.recyclerview3);
        rv2.setHasFixedSize(true);
        rv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv2.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        delList = new ArrayList<>();


        delevent();


    }

    private void delevent(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "http://pigaboo.xyz/Query_DataPig.php?pig_id="+getpigid;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                showDataEvent(response);

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

    public void showDataEvent(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i<result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);

                event_id = collectData.getString("event_id");
                event_name = collectData.getString("event_name");
                event_recorddate = collectData.getString("event_recorddate");


                DelEvent_items delEvent_items = new DelEvent_items(event_name,event_recorddate);
                delList.add(delEvent_items);
            }

             eventdel_adapter = new Eventdel_Adapter(getActivity(), delList);
            rv2.setAdapter(eventdel_adapter);
            eventdel_adapter.setOnItemClickListener(DelEvent_Fragment.this);


        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void OnDeleteClick(int position) {
        removeItem(position);
    }

    public void removeItem(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setCancelable(true);
        builder.setMessage("คุณต้องการลบใช่หรือไม่");
        builder.setPositiveButton("ใช่",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventDelete(event_id);
                        confirmdelete(position);
                    }
                });
        builder.setNegativeButton("ไม่ใช่", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void EventDelete(final String event_id){
        class EventDeleteClass extends AsyncTask<String,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog2 = ProgressDialog.show(getActivity(), "กำลังลบ...",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("event_id",params[0]);

                finalResult = httpParse.postRequest(hashMap, DeleteEvent);

                return finalResult;

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();
                Toast.makeText(getActivity(), httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

        }
        EventDeleteClass eventDeleteClass = new EventDeleteClass();
        eventDeleteClass.execute(event_id);
    }



    public void confirmdelete(int position){
        delList.remove(position);
        eventdel_adapter.notifyItemRemoved(position);
    }

}

