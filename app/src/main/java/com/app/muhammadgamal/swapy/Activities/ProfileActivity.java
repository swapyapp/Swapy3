package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private final static String LOG_TAG = ProfileActivity.class.getSimpleName();

    CircleImageView profileUserImg;
    TextView userProfileName, companyBranch, account, currentShift, preferredShift, userEmail, userPhone, textSentOrAcceptedRequest;
    Button buttonSwapRequest;
    ProgressBar progressBar, progressBarProfileActivityImage;
    private FirebaseAuth mAuth;
    private String requestMessage;
    private TextView swapDone;
    //The FireBase store that will contain the map of the notifications for each user with his ID
    private FirebaseFirestore mFireStore;
    private DatabaseReference notificationDB;
    private DatabaseReference databaseReference;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        final String currentUserId = mAuth.getCurrentUser().getUid();

        mFireStore = FirebaseFirestore.getInstance();
        notificationDB = FirebaseDatabase.getInstance().getReference().child("Notifications");

        Intent intent = getIntent();
        SwapDetails swapDetails = intent.getParcelableExtra("swapper info");
        final String swapperID = swapDetails.getSwapperID();
        final String swapperName = swapDetails.getSwapperName();
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


        //set the request message
        requestMessage = swapperName + "" +(R.string.notification_message);

        progressBarProfileActivityImage = (ProgressBar) findViewById(R.id.progressBarProfileActivityImage);
        profileUserImg = (CircleImageView) findViewById(R.id.profileUserImg);
        progressBarProfileActivityImage.setVisibility(View.VISIBLE);
        if (swapperImageUrl != null){
            Glide.with(ProfileActivity.this)
                    .load(swapperImageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBarProfileActivityImage.setVisibility(View.GONE);
                            return false;
                        }
                    })
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
        buttonSwapRequest.bringToFront();
        progressBar = (ProgressBar) findViewById(R.id.progressBar_profile);


        //if the user opens his swap the swap request button view will be gone
        if (swapperID.equals(currentUserId)) {
            buttonSwapRequest.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

        swapDone = findViewById(R.id.textSentOrAcceptedRequest);

        databaseReference = (DatabaseReference) FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userName = user.getmUsername();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buttonSwapRequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                buttonSwapRequest.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                //set the request message
                //requestMessage = swapperName + "" +(R.string.notification_message);
                requestMessage = userName + "" + " wants to swap his shift with your";

                Map <String, Object> notificationMessage = new HashMap<>();
                notificationMessage.put("message", requestMessage);
                notificationMessage.put("from", currentUserId);

                notificationDB.child(swapperID).push()
                        .setValue(notificationMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ProfileActivity.this, "Notification sent", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            swapDone.setVisibility(View.VISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this,"Something went wrong", Toast.LENGTH_LONG ).show();
                        Log.e(LOG_TAG, "Failed to insert row for " + currentUserId);
                    }
                });
            }
        });
    }
}
