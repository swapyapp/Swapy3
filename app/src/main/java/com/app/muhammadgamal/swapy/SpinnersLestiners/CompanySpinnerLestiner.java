package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.SignUpActivity;

public class CompanySpinnerLestiner implements OnItemSelectedListener {

    public static String company;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Choose company")){
            //do nothing
            SignUpActivity.COMPANY_CHOSEN = 1;
        } else {
            SignUpActivity.COMPANY_CHOSEN = 0;
            company = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
