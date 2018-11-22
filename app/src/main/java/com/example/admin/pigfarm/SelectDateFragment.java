package com.example.admin.pigfarm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.R;

import java.util.Calendar;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText edit_opendate , edit_birthday;
    private volatile Newpig_Fragment newpig = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        edit_opendate = getActivity().findViewById(R.id.edit_opendate1);
        edit_birthday = getActivity().findViewById(R.id.edit_birthday1);


        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {

        String data = yy + "/" + (mm + 1) + "/" + dd;
        edit_opendate.setText(data);

        String data2 = yy+ "/"+(mm+1)+"/"+dd;
        edit_birthday.setText(data2);
    }






}


