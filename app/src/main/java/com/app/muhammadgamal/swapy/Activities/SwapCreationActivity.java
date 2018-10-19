package com.app.muhammadgamal.swapy.Activities;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.SwapPreferredTimeSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.SwapShiftDaySpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.SwapShiftTimeSpinnerLestiner;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SwapCreationActivity extends AppCompatActivity {
    // instance of the FireBase and reference to the database
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    static int SHIFT_TIME_SELECTED = 0; // 0 => AM & 1 => PM
    static int PREFERRED_TIME_SELECTED = 0; // 0 => AM & 1 => PM
    ImageView img_back_creation_body, img_save_creation_body;
    Spinner shifts_day_spinner, shifts_time_spinner, preferred_time_spinner;
    RelativeLayout creationBodyShiftTimeAM, creationBodyShiftTimePM, creationBodyPreferredTimeAM, creationBodyPreferredTimePM;
    EditText edit_text_shift_date, edit_text_team_leader_name;
    TextView creationBodyShiftTimeAMText, creationBodyShiftTimePMText, creationBodyPreferredTimeAMText, creationBodyPreferredTimePMText;
    ProgressBar creation_body_progress_bar;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_creation_body);

        Resources res = getResources();
        final Drawable notSelectedBackground = res.getDrawable(R.drawable.selection_background_light);
        final Drawable SelectedBackground = res.getDrawable(R.drawable.selection_background);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("swaps");

        edit_text_shift_date = (EditText) findViewById(R.id.edit_text_shift_date);
        edit_text_team_leader_name = (EditText) findViewById(R.id.edit_text_team_leader_name);

        creation_body_progress_bar = (ProgressBar) findViewById(R.id.creation_body_progress_bar);

        shifts_day_spinner = (Spinner) findViewById(R.id.shifts_day_spinner);
        shifts_time_spinner = (Spinner) findViewById(R.id.shifts_time_spinner);
        preferred_time_spinner = (Spinner) findViewById(R.id.preferred_time_spinner);

        creationBodyShiftTimeAMText = (TextView) findViewById(R.id.creationBodyShiftTimeAMText);
        creationBodyShiftTimePMText = (TextView) findViewById(R.id.creationBodyShiftTimePMText);
        creationBodyPreferredTimeAMText = (TextView) findViewById(R.id.creationBodyPreferredTimeAMText);
        creationBodyPreferredTimePMText = (TextView) findViewById(R.id.creationBodyPreferredTimePMText);

        creationBodyShiftTimeAM = (RelativeLayout) findViewById(R.id.creationBodyShiftTimeAM);
        creationBodyShiftTimeAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SHIFT_TIME_SELECTED = 0;
                creationBodyShiftTimeAM.setBackground(SelectedBackground);
                creationBodyShiftTimePM.setBackground(notSelectedBackground);
                creationBodyShiftTimeAMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyShiftTimePMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        creationBodyShiftTimePM = (RelativeLayout) findViewById(R.id.creationBodyShiftTimePM);
        creationBodyShiftTimePM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SHIFT_TIME_SELECTED = 1;
                creationBodyShiftTimePM.setBackground(SelectedBackground);
                creationBodyShiftTimeAM.setBackground(notSelectedBackground);
                creationBodyShiftTimePMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyShiftTimeAMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        creationBodyPreferredTimeAM = (RelativeLayout) findViewById(R.id.creationBodyPreferredTimeAM);
        creationBodyPreferredTimeAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PREFERRED_TIME_SELECTED = 0;
                creationBodyPreferredTimeAM.setBackground(SelectedBackground);
                creationBodyPreferredTimePM.setBackground(notSelectedBackground);
                creationBodyPreferredTimeAMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyPreferredTimePMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        creationBodyPreferredTimePM = (RelativeLayout) findViewById(R.id.creationBodyPreferredTimePM);
        creationBodyPreferredTimePM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PREFERRED_TIME_SELECTED = 1;
                creationBodyPreferredTimePM.setBackground(SelectedBackground);
                creationBodyPreferredTimeAM.setBackground(notSelectedBackground);
                creationBodyPreferredTimePMText.setTextColor(getResources().getColor(R.color.white));
                creationBodyPreferredTimeAMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        swapShiftDaySpinner();
        swapShiftTimeSpinner();
        swapPreferredTimeSpinner();

        img_back_creation_body = (ImageView) findViewById(R.id.img_back_creation_body);
        img_back_creation_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(getParent());
            }
        });
        img_save_creation_body = (ImageView) findViewById(R.id.img_save_creation_body);
        img_save_creation_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSwapToDatabase();
            }
        });
    }

    private void swapShiftDaySpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shift_day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shifts_day_spinner.setAdapter(adapter);
        shifts_day_spinner.setOnItemSelectedListener(new SwapShiftDaySpinnerLestiner());
    }
    private void swapShiftTimeSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shift_time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shifts_time_spinner.setAdapter(adapter);
        shifts_time_spinner.setOnItemSelectedListener(new SwapShiftTimeSpinnerLestiner());
    }
    private void swapPreferredTimeSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.preferred_shift_time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        preferred_time_spinner.setAdapter(adapter);
        preferred_time_spinner.setOnItemSelectedListener(new SwapPreferredTimeSpinnerLestiner());
    }

    //add the swap to FireBase RealTime database
    private void addSwapToDatabase() {
        String shiftAMorPM, preferredAMorPM;
        if (SHIFT_TIME_SELECTED == 0) {
            shiftAMorPM = " AM";
        } else {
            shiftAMorPM = " PM";
        }
        if (PREFERRED_TIME_SELECTED == 0) {
            preferredAMorPM = " AM";
        } else {
            preferredAMorPM = " PM";
        }
        String shiftDay = shifts_day_spinner.getSelectedItem().toString();
        String shiftDate = edit_text_shift_date.getText().toString().trim();
        String shiftTime = shifts_time_spinner.getSelectedItem().toString() + shiftAMorPM;
        String teamLeader = edit_text_team_leader_name.getText().toString();
        String preferredShift = preferred_time_spinner.getSelectedItem().toString() + preferredAMorPM;
        if (shifts_day_spinner.getSelectedItem().toString().equals("Day")){
            Toast.makeText(getApplicationContext(), "choose a day", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shiftDate.isEmpty()){
            edit_text_shift_date.setError("Enter your Shift's date");
            edit_text_shift_date.requestFocus();
            return;
        }
        if (shifts_time_spinner.getSelectedItem().toString().equals("Shift's time")){
            Toast.makeText(getApplicationContext(), "choose your shift's time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (teamLeader.isEmpty()){
            edit_text_team_leader_name.setError("Enter your team leader's name");
            edit_text_team_leader_name.requestFocus();
            return;
        }
        if (preferred_time_spinner.getSelectedItem().toString().equals("Preferred shift")){
            Toast.makeText(getApplicationContext(), "choose your preferred shift", Toast.LENGTH_SHORT).show();
            return;
        }
        SwapDetails SwapDetails = new SwapDetails(userId, shiftDay, shiftDate, shiftTime, teamLeader, preferredShift);
        creation_body_progress_bar.setVisibility(View.VISIBLE);
        img_save_creation_body.setVisibility(View.GONE);
        databaseReference.push().setValue(SwapDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                img_save_creation_body.setVisibility(View.VISIBLE);
                creation_body_progress_bar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Swap added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}