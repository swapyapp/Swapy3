package com.app.muhammadgamal.swapy.SpinnersLestiners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class PreferredShiftSpinnerLestiner implements AdapterView.OnItemSelectedListener {

    String PreferredShift;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getItemAtPosition(position).equals("Preferred Shift")){
            //do nothing
        } else {
            PreferredShift = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), PreferredShift, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}