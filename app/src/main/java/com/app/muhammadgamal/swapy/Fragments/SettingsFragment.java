package com.app.muhammadgamal.swapy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.muhammadgamal.swapy.Activities.NavDrawerActivity;
import com.app.muhammadgamal.swapy.Activities.SignInActivity;
import com.app.muhammadgamal.swapy.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Settings");

        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        Button btnLogout = rootView.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(rootView.getContext(), SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
