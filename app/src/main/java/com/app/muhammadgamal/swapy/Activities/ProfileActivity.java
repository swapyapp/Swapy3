package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileUserImg;
    TextView userProfileName, companyBranch, account, currentShift, preferredShift, userEmail, userPhone, textSentOrAcceptedRequest;
    Button buttonSwapRequest;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        SwapDetails swapDetails = intent.getParcelableExtra("swapper info");
        String swapperID = swapDetails.getSwapperID();
        String swapperName = swapDetails.getSwapperName();
        String swapperEmail = swapDetails.getSwapperEmail();
        String swapperPhone = swapDetails.getSwapperPhone();
        String swapperCompanyBranch = swapDetails.getSwapperCompanyBranch();
        String swapperAccount = swapDetails.getSwapperAccount();
        String swapperImageUrl = swapDetails.getSwapperImageUrl();
        String swapperShiftDay = swapDetails.getSwapperShiftDay();
        String swapShiftDate = swapDetails.getSwapShiftDate();
        String swapperShiftTime = swapDetails.getSwapperShiftTime();
        String swapperTeamLeader = swapDetails.getSwapperTeamLeader();
        String swapperPreferredShift = swapDetails.getSwapperPreferredShift();

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
        companyBranch = (TextView) findViewById(R.id.profileCompanyBranch);
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

        if (swapperID.equals(currentUserId)){
            buttonSwapRequest.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }



    }
}
