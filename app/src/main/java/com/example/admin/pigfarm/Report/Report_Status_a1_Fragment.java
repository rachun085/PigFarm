package com.example.admin.pigfarm.Report;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.admin.R;

public class Report_Status_a1_Fragment extends Fragment {
    String farm_id;
    String pdf;
    int selectedItem;
    Spinner spin_type,spin_sortby;
    Button btn_submit_report_status;


    public Report_Status_a1_Fragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_status_a1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences shared = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        farm_id = shared.getString("farm_id", "missing");

        spin_type = getView().findViewById(R.id.spin_type);
        spin_sortby = getView().findViewById(R.id.spin_sortby);
        btn_submit_report_status = getView().findViewById(R.id.btn_submit_report_status);


        final String[] eventStr = getResources().getStringArray(R.array.type_of_status);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, eventStr);
        adapterType.setDropDownViewResource(R.layout.spinner_item);
        spin_type.setAdapter(adapterType);


        final String[] eventStr2 = getResources().getStringArray(R.array.sort_by_status);
        final ArrayAdapter<String> adapterSort = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, eventStr2);
        adapterSort.setDropDownViewResource(R.layout.spinner_item);
        spin_sortby.setAdapter(adapterSort);

        spin_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    selectedItem = adapterView.getSelectedItemPosition();
                    if (position == 0){
                        spin_sortby.setEnabled(false);
                        spin_sortby.setClickable(false);
                        spin_sortby.setAdapter(adapterSort);
                    }else{
                        spin_sortby.setEnabled(true);
                        spin_sortby.setClickable(true);
                        spin_sortby.setAdapter(adapterSort);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_submit_report_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (String.valueOf(spin_type.getSelectedItem()).equals("พ่อ")){

                    pdf = "http://pigaboo.xyz/Report/Status_Dad.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }

                if (String.valueOf(spin_type.getSelectedItem()).equals("แม่") && String.valueOf(spin_sortby.getSelectedItem()).equals("เบอร์หู")){

                    pdf = "http://pigaboo.xyz/Report/Status_Mom_by_ID.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }

                else if (String.valueOf(spin_type.getSelectedItem()).equals("แม่") && String.valueOf(spin_sortby.getSelectedItem()).equals("ท้องที่")){

                    pdf = "http://pigaboo.xyz/Report/Status_Mom_by_Pregnant.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);

                    startActivity(intent);
                }

                else if (String.valueOf(spin_type.getSelectedItem()).equals("แม่") && String.valueOf(spin_sortby.getSelectedItem()).equals("สถานภาพ")){

                    pdf = "http://pigaboo.xyz/Report/Status_Mom_by_Status.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }

                else if (String.valueOf(spin_type.getSelectedItem()).equals("แม่") && String.valueOf(spin_sortby.getSelectedItem()).equals("วันสุดท้าย")){

                    pdf = "http://pigaboo.xyz/Report/Status_Mom_by_LastDay.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }

                else if (String.valueOf(spin_type.getSelectedItem()).equals("ทั้งหมด") && String.valueOf(spin_sortby.getSelectedItem()).equals("เบอร์หู")){

                    pdf = "http://pigaboo.xyz/Report/Status_All_by_ID.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }

                else if (String.valueOf(spin_type.getSelectedItem()).equals("ทั้งหมด") && String.valueOf(spin_sortby.getSelectedItem()).equals("ท้องที่")){

                    pdf = "http://pigaboo.xyz/Report/Status_All_by_Pregnant.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }

                else if (String.valueOf(spin_type.getSelectedItem()).equals("ทั้งหมด") && String.valueOf(spin_sortby.getSelectedItem()).equals("สถานภาพ")){

                    pdf = "http://pigaboo.xyz/Report/Status_All_by_Status.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }

                else if (String.valueOf(spin_type.getSelectedItem()).equals("ทั้งหมด") && String.valueOf(spin_sortby.getSelectedItem()).equals("วันสุดท้าย")){

                    pdf = "http://pigaboo.xyz/Report/Status_All_by_LastDay.php";
                    Intent intent = new Intent(getActivity(), WebView_Report_Status.class);
                    intent.putExtra("url",pdf);
                    startActivity(intent);
                }



            }
        });
    }


}
