package com.example.admin.pigfarm.ManageData_Page;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class DelEvent_Fragment extends android.support.v4.app.Fragment implements Eventdel_Adapter.OnItemLongClickListener, Eventdel_Adapter.OnItemClickListener{

    private RecyclerView rv2;
    List<DelEvent_items> delList;
    public String getpigno, getpigid,event_name,event_recorddate, event_id,detail_id;
    Eventdel_Adapter eventdel_adapter;
    private ProgressDialog progressDialog2;
    HashMap<String,String> hashMap = new HashMap<>();
    private String finalResult;
    private HttpParse httpParse = new HttpParse();
    String detail_id_final,event_name_detail;
    SwipeRefreshLayout swiperefresh;
    Handler handler;
    private String DeleteEvent = "http://pigaboo.xyz/Delete_Eventdata.php";

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
        getpigid = bundle2.getString("pig_id");
        getpigno = bundle2.getString("pig_no");

        swiperefresh = getView().findViewById(R.id.swiperefresh);


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
                detail_id = collectData.getString("detail_id");

                DelEvent_items delEvent_items = new DelEvent_items(detail_id,event_name,event_recorddate);
                delList.add(delEvent_items);
                Log.d("DETAIL_ID",detail_id);
            }

            eventdel_adapter = new Eventdel_Adapter(getActivity(), delList);
            rv2.setAdapter(eventdel_adapter);
            eventdel_adapter.setOnItemClickListener(DelEvent_Fragment.this);
            eventdel_adapter.setItemClickListener(DelEvent_Fragment.this);

        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void OnDeleteClick(int position) {
        removeItem(position);
    }

    @Override
    public void OnEditClick(int position) {
        editItem(position);
    }



    public void removeItem(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setCancelable(true);
        builder.setMessage("คุณต้องการลบใช่หรือไม่");
        builder.setPositiveButton("ใช่",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), detail_id, Toast.LENGTH_SHORT).show();
                        detail_id_final = delList.get(position).getDetail_id();

                        EventDelete(String.valueOf(detail_id_final));
                        confirmdelete(position);

                        Log.d("DETAIL_ID_ADAPTER",""+detail_id_final);

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

    public void EventDelete(final String detail_id){
        class EventDeleteClass extends AsyncTask<String,Void,String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog2 = ProgressDialog.show(getActivity(), "กำลังลบ...",null,true,true);
                Log.d("PRE_DELETE",detail_id);
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("detail_id",params[0]);

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
        eventDeleteClass.execute(detail_id);
    }

    private void editItem(int position) {

        detail_id_final = delList.get(position).getDetail_id();
        event_name_detail = delList.get(position).getevent_name();
        switch (event_name_detail){
            case "ผสมพันธุ์" :
                edt_Breed_Fragment edt_breed_fragment = new edt_Breed_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("detail_id",detail_id_final);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_breed_fragment.setArguments(bundle);
                ft.replace(R.id.container, edt_breed_fragment);
                ft.addToBackStack(null);
                ft.commit();
                break;

            case "ตรวจท้อง":
                edt_CheckPregnant_Fragment edt_checkPregnant_fragment = new edt_CheckPregnant_Fragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("detail_id",detail_id_final);

                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                ft1.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_checkPregnant_fragment.setArguments(bundle2);
                ft1.replace(R.id.container, edt_checkPregnant_fragment);
                ft1.addToBackStack(null);
                ft1.commit();
                break;

            case "แท้ง":
                edt_Pregnant_Fragment edt_pregnant_fragment = new edt_Pregnant_Fragment();
                Bundle bundle3 = new Bundle();
                bundle3.putString("detail_id",detail_id_final);

                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_pregnant_fragment.setArguments(bundle3);
                ft2.replace(R.id.container, edt_pregnant_fragment);
                ft2.addToBackStack(null);
                ft2.commit();
                break;

            case "คลอด":
                edt_Maternity_Fragment edt_maternity_fragment = new edt_Maternity_Fragment();
                Bundle bundle4 = new Bundle();
                bundle4.putString("detail_id",detail_id_final);

                FragmentTransaction ft3 = getFragmentManager().beginTransaction();
                ft3.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_maternity_fragment.setArguments(bundle4);
                ft3.replace(R.id.container, edt_maternity_fragment);
                ft3.addToBackStack(null);
                ft3.commit();
                break;

            case "ฝากเลี้ยง":
                edt_Entrustment_Fragment edt_entrustment_fragment = new edt_Entrustment_Fragment();
                Bundle bundle5 = new Bundle();
                bundle5.putString("detail_id",detail_id_final);

                FragmentTransaction ft4 = getFragmentManager().beginTransaction();
                ft4.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_entrustment_fragment.setArguments(bundle5);
                ft4.replace(R.id.container, edt_entrustment_fragment);
                ft4.addToBackStack(null);
                ft4.commit();
                break;

            case "หย่านม":
                edt_Wean_Fragment edt_wean_fragment = new edt_Wean_Fragment();
                Bundle bundle6 = new Bundle();
                bundle6.putString("detail_id",detail_id_final);

                FragmentTransaction ft5 = getFragmentManager().beginTransaction();
                ft5.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_wean_fragment.setArguments(bundle6);
                ft5.replace(R.id.container, edt_wean_fragment);
                ft5.addToBackStack(null);
                ft5.commit();
                break;

            case "รับเลี้ยงลูก":
                edt_Adopt_Fragment edt_adopt_fragment = new edt_Adopt_Fragment();
                Bundle bundle7 = new Bundle();
                bundle7.putString("detail_id",detail_id_final);

                FragmentTransaction ft6 = getFragmentManager().beginTransaction();
                ft6.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_adopt_fragment.setArguments(bundle7);
                ft6.replace(R.id.container, edt_adopt_fragment);
                ft6.addToBackStack(null);
                ft6.commit();
                break;

            case "คัดทิ้ง":
                edt_Exclude_Fragment edt_exclude_fragment = new edt_Exclude_Fragment();
                Bundle bundle8 = new Bundle();
                bundle8.putString("detail_id",detail_id_final);

                FragmentTransaction ft7 = getFragmentManager().beginTransaction();
                ft7.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_exclude_fragment.setArguments(bundle8);
                ft7.replace(R.id.container, edt_exclude_fragment);
                ft7.addToBackStack(null);
                ft7.commit();
                break;

            case "หมายเหตุ":
                edt_Note_Fragment edt_note_fragment = new edt_Note_Fragment();
                Bundle bundle9 = new Bundle();
                bundle9.putString("detail_id",detail_id_final);

                FragmentTransaction ft8 = getFragmentManager().beginTransaction();
                ft8.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_note_fragment.setArguments(bundle9);
                ft8.replace(R.id.container, edt_note_fragment);
                ft8.addToBackStack(null);
                ft8.commit();
                break;

            case "ลูกหมูตาย":
                edt_Piggydead_Fragment edt_piggydead_fragment = new edt_Piggydead_Fragment();
                Bundle bundle10 = new Bundle();
                bundle10.putString("detail_id",detail_id_final);

                FragmentTransaction ft9 = getFragmentManager().beginTransaction();
                ft9.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_piggydead_fragment.setArguments(bundle10);
                ft9.replace(R.id.container, edt_piggydead_fragment);
                ft9.addToBackStack(null);
                ft9.commit();
                break;

            case "เป็นสัดแต่ไม่ผสมพันธุ์":
                edt_Rutnotbreeded_Fragment edt_rutnotbreeded_fragment = new edt_Rutnotbreeded_Fragment();
                Bundle bundle11 = new Bundle();
                bundle11.putString("detail_id",detail_id_final);

                FragmentTransaction ft10 = getFragmentManager().beginTransaction();
                ft10.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_rutnotbreeded_fragment.setArguments(bundle11);
                ft10.replace(R.id.container, edt_rutnotbreeded_fragment);
                ft10.addToBackStack(null);
                ft10.commit();
                break;

            case "ท้องลม":
                edt_Blightedovum_Fragment edt_blightedovum_fragment = new edt_Blightedovum_Fragment();
                Bundle bundle12 = new Bundle();
                bundle12.putString("detail_id",detail_id_final);

                FragmentTransaction ft11 = getFragmentManager().beginTransaction();
                ft11.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_blightedovum_fragment.setArguments(bundle12);
                ft11.replace(R.id.container, edt_blightedovum_fragment);
                ft11.addToBackStack(null);
                ft11.commit();
                break;

            case "แยกหย่านม":
                edt_Wean2_Fragment edt_wean2_fragment = new edt_Wean2_Fragment();
                Bundle bundle13 = new Bundle();
                bundle13.putString("detail_id",detail_id_final);

                FragmentTransaction ft12 = getFragmentManager().beginTransaction();
                ft12.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_wean2_fragment.setArguments(bundle13);
                ft12.replace(R.id.container, edt_wean2_fragment);
                ft12.addToBackStack(null);
                ft12.commit();
                break;

            case "ได้รับยารักษา":
                edt_Treat_Fragment edt_treat_fragment = new edt_Treat_Fragment();
                Bundle bundle14 = new Bundle();
                bundle14.putString("detail_id",detail_id_final);

                FragmentTransaction ft13 = getFragmentManager().beginTransaction();
                ft13.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_treat_fragment.setArguments(bundle14);
                ft13.replace(R.id.container, edt_treat_fragment);
                ft13.addToBackStack(null);
                ft13.commit();
                break;

            case "ป่วยเป็นโรค":
                edt_Getsick_Fragment edt_getsick_fragment = new edt_Getsick_Fragment();
                Bundle bundle15 = new Bundle();
                bundle15.putString("detail_id",detail_id_final);

                FragmentTransaction ft14 = getFragmentManager().beginTransaction();
                ft14.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                edt_getsick_fragment.setArguments(bundle15);
                ft14.replace(R.id.container, edt_getsick_fragment);
                ft14.addToBackStack(null);
                ft14.commit();
                break;

        }
    }



    public void confirmdelete(int position){
        delList.remove(position);
        eventdel_adapter.notifyItemRemoved(position);
    }


}

