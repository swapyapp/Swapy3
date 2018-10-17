package com.app.muhammadgamal.swapy.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SpinnersLestiners.AccountSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.BranchSpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CompanySpinnerLestiner;
import com.app.muhammadgamal.swapy.SpinnersLestiners.CurrentShiftSpinnerLestiner;
import com.app.muhammadgamal.swapy.SwapData.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity  {

    // 0 => chosen
    // 1 => not chosen
    public static int COMPANY_CHOSEN = 0, BRANCH_CHOSEN = 0, ACCOUNT_CHOSEN = 0, CURREN_SHIFT_CHOSEN = 0;
    static int TIME_SELECTED = 0; // 0 => AM & 1 => PM
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    static int IMG_UPLOADED = 0;
    static int USER_INFO_SAVED = 0;
    TextView signInText, currentTimePMText, currentTimeAMText;
    Button signUpButton;
    ImageView userImageSignUp;
    Uri pickedImageUri;
    EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextName, editTextPhone;
    String profileImageUrl;
    Spinner spinnerCompany, spinnerCompanyBranch, spinnerAccount, currentShiftSpinner;
    ProgressBar progressBarImg;
    RelativeLayout currentTimeAM, currentTimePM;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Resources res = getResources();



        signInText = (TextView) findViewById(R.id.signInText);
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        progressBarImg = (ProgressBar) findViewById(R.id.progressBarImg);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        currentTimePMText = (TextView) findViewById(R.id.currentTimePMText);
        currentTimeAMText = (TextView) findViewById(R.id.currentTimeAMText);
        spinnerCompany = (Spinner) findViewById(R.id.spinnerCompany);
        spinnerCompanyBranch = (Spinner) findViewById(R.id.spinnerCompanyBranch);

        companySpinner();
        currentShiftSpinner();
        accountSpinner();
        branchSpinner();

        final Drawable notSelectedBackground = res.getDrawable(R.drawable.selection_background_light);
        final Drawable SelectedBackground = res.getDrawable(R.drawable.selection_background);

        currentTimeAM = (RelativeLayout) findViewById(R.id.currentTimeAM);
        currentTimeAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TIME_SELECTED = 0;
                currentTimeAM.setBackground(SelectedBackground);
                currentTimePM.setBackground(notSelectedBackground);
                currentTimeAMText.setTextColor(getResources().getColor(R.color.white));
                currentTimePMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        currentTimePM = (RelativeLayout) findViewById(R.id.currentTimePM);
        currentTimePM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TIME_SELECTED = 1;
                currentTimePM.setBackground(SelectedBackground);
                currentTimeAM.setBackground(notSelectedBackground);
                currentTimePMText.setTextColor(getResources().getColor(R.color.white));
                currentTimeAMText.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        signUpButton = (Button) findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        userImageSignUp = findViewById(R.id.userImageSignUp);
        userImageSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 22) {
                    requestPermissionAndOpenGallery();
                } else {
                    openGallery();
                }
            }
        });
    }



    private void companySpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.company, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCompany.setAdapter(adapter);
        spinnerCompany.setOnItemSelectedListener(new CompanySpinnerLestiner());
    }

    private void branchSpinner() {
//        if (CompanySpinnerLestiner.VODAFONE == 1){
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.vodafone_branch, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerCompanyBranch.setAdapter(adapter);
//            spinnerCompanyBranch.setOnItemSelectedListener(new BranchSpinnerLestiner());
//        }
//        if (CompanySpinnerLestiner.RAYA == 1){
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.raya_branch, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerCompanyBranch.setAdapter(adapter);
//            spinnerCompanyBranch.setOnItemSelectedListener(new BranchSpinnerLestiner());
//        }
//        else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branch, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCompanyBranch.setAdapter(adapter);
            spinnerCompanyBranch.setOnItemSelectedListener(new BranchSpinnerLestiner());
//        }
    }

    private void accountSpinner() {
        spinnerAccount = findViewById(R.id.spinnerAccount);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccount.setAdapter(adapter);
        spinnerAccount.setOnItemSelectedListener(new AccountSpinnerLestiner());
    }

    private void currentShiftSpinner() {
        currentShiftSpinner = findViewById(R.id.currentShiftSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.current_Shift, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentShiftSpinner.setAdapter(adapter);
        currentShiftSpinner.setOnItemSelectedListener(new CurrentShiftSpinnerLestiner());
    }

    private void saveUserInfoToFirebaseDatabase() {
        String AMorPM;
        if (TIME_SELECTED == 0) {
            AMorPM = " AM";
        } else {
            AMorPM = " PM";
        }
        String username = editTextName.getText().toString();
        String phoneNumber = editTextPhone.getText().toString();
        String userId = mAuth.getCurrentUser().getUid();
        User user;
        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        if (profileImageUrl != null) {
            signUpButton.setVisibility(View.GONE);
            user = new User(username, phoneNumber, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, CurrentShiftSpinnerLestiner.CurrentShift + AMorPM, profileImageUrl, 0,0,0);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 1;
                    Intent intent = new Intent(SignUpActivity.this, NavDrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 0;
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            signUpButton.setVisibility(View.GONE);
            user = new User(username, phoneNumber, CompanySpinnerLestiner.company, BranchSpinnerLestiner.Branch, AccountSpinnerLestiner.Account, CurrentShiftSpinnerLestiner.CurrentShift, null, 0,0,0);
            currentUserDb.setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 1;
                    Intent intent = new Intent(SignUpActivity.this, NavDrawerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    signUpButton.setVisibility(View.VISIBLE);
                    USER_INFO_SAVED = 0;
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);
    }

    private void requestPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(SignUpActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUpActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            } else {
                ActivityCompat.requestPermissions(SignUpActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null && data.getData() != null) {
            //user choose the image
            //replace the image in the UI
            pickedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), pickedImageUri);
                userImageSignUp.setImageBitmap(bitmap);
                uploadProfileImageToFirebase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadProfileImageToFirebase() {
        String fileName = UUID.randomUUID().toString();
        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("profilepics/" + fileName + ".jpg");
        if (pickedImageUri != null) {
            progressBarImg.setVisibility(View.VISIBLE);
            profileImageRef.putFile(pickedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    IMG_UPLOADED = 1;
                    progressBarImg.setVisibility(View.GONE);
                    profileImageUrl = taskSnapshot.getMetadata().getDownloadUrl().toString();
                    Toast.makeText(SignUpActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    IMG_UPLOADED = 0;
                    progressBarImg.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void signUp() {
        String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String userName = editTextName.getText().toString();
        final String phoneNumber = editTextPhone.getText().toString();
        if (userName.isEmpty()) {
            editTextName.setError("Please enter your Name");
            editTextName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password is 6");
            editTextPassword.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Please confirm your password");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Password Not matching");
            editTextConfirmPassword.requestFocus();
            return;
        }
        if (phoneNumber.isEmpty()) {
            editTextPhone.setError("Please enter your phone number");
            editTextPhone.requestFocus();
            return;
        }
        if (COMPANY_CHOSEN == 1) {
            Toast.makeText(this, "choose a company", Toast.LENGTH_SHORT).show();
            return;
        }
        if (BRANCH_CHOSEN == 1) {
            Toast.makeText(this, "choose a branch", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ACCOUNT_CHOSEN == 1) {
            Toast.makeText(this, "choose an account", Toast.LENGTH_SHORT).show();
            return;
        }
        if (CURREN_SHIFT_CHOSEN == 1) {
            Toast.makeText(this, "choose your current shift", Toast.LENGTH_SHORT).show();
            return;
        }


        if (pickedImageUri != null) {
            if (IMG_UPLOADED == 1) {
                signUpButton.setVisibility(View.GONE);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                signUpButton.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    saveUserInfoToFirebaseDatabase();
                                } else {
//                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
//                        Toast.makeText(getApplicationContext(), "you are already registered", Toast.LENGTH_LONG).show();
//                    } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                    }
                                }
                            }
                        });

            } else {
                Toast.makeText(SignUpActivity.this, "Please wait until Image is uploaded", Toast.LENGTH_LONG).show();
            }
        } else {
            signUpButton.setVisibility(View.GONE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            signUpButton.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {
                                saveUserInfoToFirebaseDatabase();
                            } else {
//                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
//                        Toast.makeText(getApplicationContext(), "you are already registered", Toast.LENGTH_LONG).show();
//                    } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                    }
                            }
                        }
                    });

        }


    }


}
