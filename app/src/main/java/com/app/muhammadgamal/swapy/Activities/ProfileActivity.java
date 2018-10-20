package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileUserImg;
    TextView userProfileName, companyBranch, account, currentShift, preferredShift, userEmail, userPhone, textSentOrAcceptedRequest;
    Button buttonSwapRequest;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        SwapDetails swapDetails = intent.getParcelableExtra("swapper info");
        String swapperImageUrl = swapDetails.getSwapperImageUrl();
        String swapperName = swapDetails.getSwapperName();
        String swapperEmail = swapDetails.getSwapperEmail();
        String swapperShiftTime = swapDetails.getSwapperShiftDay();
        String swapperShiftDay = swapDetails.getSwapperShiftDay();
        String swapperPreferredShift = swapDetails.getSwapperPreferredShift();
        String swapperPhone = swapDetails.getSwapperPhone();
        String swapShiftDate = swapDetails.getSwapShiftDate();
        String swapperTeamLeader = swapDetails.getSwapperTeamLeader();
        String swapperID = swapDetails.getSwapperID();
        String swapperCompanyBranch = swapDetails.getSwapperCompanyBranch();
        String swapperAccount = swapDetails.getSwapperAccount();

        profileUserImg = (CircleImageView) findViewById(R.id.profileUserImg);
        if (swapperImageUrl != null){
            Glide.with(ProfileActivity.this)
                    .load(swapperImageUrl)
                    .into(profileUserImg);
        }else {
            // set the swapper Image to default if no image provided
            Resources resources = getApplicationContext().getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            profileUserImg.setImageDrawable(photoUrl);
        }

        userProfileName = (TextView) findViewById(R.id.userProfileName);
        userProfileName.setText(swapperName);
        companyBranch = (TextView) findViewById(R.id.companyBranch);
        companyBranch.setText(swapperCompanyBranch);
        account = (TextView) findViewById(R.id.account);
        account.setText(swapperAccount);
        currentShift = (TextView) findViewById(R.id.currentShift);
        currentShift.setText(swapperShiftTime);
        preferredShift = (TextView) findViewById(R.id.preferredShift);
        preferredShift.setText(swapperPreferredShift);
        userEmail = (TextView) findViewById(R.id.userEmail);
        userEmail.setText(swapperEmail);
        userPhone = (TextView) findViewById(R.id.userPhone);
        userPhone.setText(swapperPhone);
        textSentOrAcceptedRequest = (TextView) findViewById(R.id.textSentOrAcceptedRequest);

        buttonSwapRequest = (Button) findViewById(R.id.buttonSwapRequest);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);



    }
}
