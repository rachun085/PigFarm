package com.example.admin.pigfarm.ManageData_Page;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.util.List;

public class RecyclerFragment extends Fragment implements MyAdapter.OnItemClickListener {

    private RecyclerView rv;
    MyAdapter adapter;
    List<Model> profilepig;
    String username, password, pig_id, pig_no,getfarm_name,getunit_name,getfarm_id = "";



    public RecyclerFragment newInstance(){

        RecyclerFragment recyclerFragment = new RecyclerFragment();
        return recyclerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_data, null);
        SharedPreferences shared = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        username = shared.getString("username", "");
        password = shared.getString("pass", "");

        SharedPreferences farm = this.getActivity().getSharedPreferences("Farm", Context.MODE_PRIVATE);
        getfarm_name = farm.getString("farm_name", "");
        getunit_name = farm.getString("unit_name", "");
        getfarm_id = farm.getString("farm_id","");


        rv = rootView.findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));


        profilepig = new ArrayList<>();

        loadProducts();

        return rootView;


    }

    @Override
    public String toString() {
        return "RecyclerFragment";
    }

    private void loadProducts(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "http://pigaboo.xyz/Query_ProfilePig.php?farm_id="+getfarm_id;
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

                    pig_id = collectData.getString("pig_id");
                    pig_no = collectData.getString("pig_no");

                    Model model = new Model(pig_id,pig_no);
                    profilepig.add(model);

            }



            adapter = new MyAdapter(getActivity(), profilepig);
            rv.setAdapter(adapter);
            adapter.setOnItemClickListener(RecyclerFragment.this);

        }catch (JSONException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onItemClick(int position) {
        Model clickItem = profilepig.get(position);
        Fragment_datapig fragment_datapig = new Fragment_datapig();
        Bundle bundle2 = new Bundle();
        bundle2.putString("pig_id",clickItem.getPig_id());
        bundle2.putString("pig_no",clickItem.getPig_no());
        FragmentTransaction ft = getFragmentManager().beginTransaction();
                fragment_datapig.setArguments(bundle2);
                ft.replace(R.id.container, fragment_datapig);
                ft.addToBackStack(null);
                ft.commit();

    }


}
