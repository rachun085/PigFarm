package com.example.admin.pigfarm.ManageData_Page;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.R;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class Fragment_datapig extends android.support.v4.app.Fragment {

    private TextView text_recorddate,text_preglist, text_bd, text_breed, text_bredder, text_bredder1, text_from, text_reserveid,texttitle_preglist, text_pigid;
    String getpigno, getpigid, pig_no, pig_id, pig_preglist, pig_recorddate, pig_birthday, pig_breed, pig_idbreeder, pig_idbreeder2, pig_from, pig_idreserve,
            event_name,event_recorddate;
    List<Event_items> itemsList;
    RecyclerView recyclerView;
    ImageView editprofile,editevent;


    public Fragment_datapig() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_datapig, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle2 = getArguments();
        getpigno = bundle2.getString("pig_no");
        getpigid = bundle2.getString("pig_id");

        text_pigid = getView().findViewById(R.id.text_pigid);
        text_recorddate = getView().findViewById(R.id.text_recorddate);
        text_bd = getView().findViewById(R.id.text_bd);
        text_breed = getView().findViewById(R.id.text_breed);
        text_bredder = getView().findViewById(R.id.text_breeder);
        text_bredder1 = getView().findViewById(R.id.text_breeder1);
        text_from = getView().findViewById(R.id.text_from);
        text_reserveid = getView().findViewById(R.id.text_reserveid);
        text_preglist = getView().findViewById(R.id.text_preglist);
        texttitle_preglist = getView().findViewById(R.id.texttitle_preglist);
        editprofile = getView().findViewById(R.id.editprofile);
        editevent = getView().findViewById(R.id.editevent);

        recyclerView = getView().findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemsList = new ArrayList<>();


            loadDatapigs();

            editprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Edit_ProfilePig edit_profilePig = new Edit_ProfilePig();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("pig_no",getpigno);
                    bundle2.putString("pig_id",getpigid);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    edit_profilePig.setArguments(bundle2);
                    ft.replace(R.id.container, edit_profilePig);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

            editevent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DelEvent_Fragment delEvent_fragment = new DelEvent_Fragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("pig_no",getpigno);
                    bundle3.putString("pig_id",getpigid);
                    delEvent_fragment.setArguments(bundle3);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    ft.replace(R.id.container, delEvent_fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

    }

    private void loadDatapigs(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String url = "http://pigaboo.xyz/Query_DataPig.php?pig_id="+getpigid;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
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
                event_name = collectData.getString("event_name");
                event_recorddate = collectData.getString("event_recorddate");


                text_pigid.setText(getpigid);
                text_recorddate.setText(pig_recorddate);
                text_bd.setText(pig_birthday);
                text_breed.setText(pig_breed);
                text_bredder.setText(pig_idbreeder);
                text_bredder1.setText(pig_idbreeder2);
                text_reserveid.setText(pig_idreserve);
                text_from.setText(pig_from);
                if(pig_preglist.isEmpty()){
                    text_preglist.setVisibility(View.INVISIBLE);
                    texttitle_preglist.setVisibility(View.INVISIBLE);
                }else {
                    text_preglist.setVisibility(View.VISIBLE);
                    texttitle_preglist.setVisibility(View.VISIBLE);
                    text_preglist.setText(pig_preglist);
                }
                    Event_items event_items = new Event_items(event_name,event_recorddate);
                    itemsList.add(event_items);
            }
            Event_Adapter adapter = new Event_Adapter(getActivity(),itemsList);
            recyclerView.setAdapter(adapter);
        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
