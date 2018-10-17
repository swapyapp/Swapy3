package com.app.muhammadgamal.swapy.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.muhammadgamal.swapy.Activities.SwapCreationActivity;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapAdapter;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ImageView imgFilter;

    Dialog filterDialog;

    ImageView imgCloseFilterDialog;

    private SwapAdapter swapAdapter;

    // List view that represent teh swap data
    ListView swapList;

    private ListView listView;

    //Get reference to the data base
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mSwapDataBaseReference;

    private ValueEventListener mValueEventListener;


    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        getActivity().setTitle("Home");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSwapDataBaseReference = mFirebaseDatabase.getReference().child("swaps");




        FloatingActionButton fab = container.findViewById(R.id.fab_add_swap);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SwapCreationActivity.class);
                startActivity(intent);
            }
        });

        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
                swapAdapter.add(swapDetails);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<SwapDetails> swapBodyList = new ArrayList<>();
        swapAdapter = new SwapAdapter(getContext(), R.layout.home_list_item,swapBodyList);
        listView = getView().findViewById(R.id.list_item);
        listView.setAdapter(swapAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        filterDialog = new Dialog(getContext());
        RelativeLayout imgFilter = getView().findViewById(R.id.imgFilter);
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });
    }

    public void showFilterDialog() {

        filterDialog.setContentView(R.layout.dialog_filter);
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgCloseFilterDialog = filterDialog.findViewById(R.id.imgCloseFilterDialog);
        imgCloseFilterDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.dismiss();
            }
        });
        filterDialog.show();
    }
}
