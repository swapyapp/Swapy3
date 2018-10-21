package com.app.muhammadgamal.swapy.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.app.muhammadgamal.swapy.Activities.ProfileActivity;
import com.app.muhammadgamal.swapy.Activities.SwapCreationActivity;
import com.app.muhammadgamal.swapy.R;
import com.app.muhammadgamal.swapy.SwapData.SwapAdapter;
import com.app.muhammadgamal.swapy.SwapData.SwapDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    ImageView imgFilter;
    Dialog filterDialog;
    ImageView imgCloseFilterDialog;
    private SwapAdapter swapAdapter;
    // List view that represent teh swap data
    ListView swapList;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");
        Button homeSwapButton = rootView.findViewById(R.id.btnHomeSwapList);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mSwapDataBaseReference = mFirebaseDatabase.getReference().child("swaps");

        FloatingActionButton fab_add_swap = rootView.findViewById(R.id.fab_add_swap);
        fab_add_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SwapCreationActivity.class);
                startActivity(intent);
            }
        });

        progressBar = rootView.findViewById(R.id.progressBar_home);
        progressBar.setVisibility(View.VISIBLE);

        ChildEventListener mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
                swapAdapter.add(swapDetails);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mSwapDataBaseReference.addChildEventListener(mChildEventListener);

        final List<SwapDetails> swapBodyList = new ArrayList<>();
        swapAdapter = new SwapAdapter(getContext(), R.layout.home_list_item,swapBodyList);
        ListView listView = rootView.findViewById(R.id.homeList);
        listView.setAdapter(swapAdapter);
//        if (homeSwapButton != null) {
//            homeSwapButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = (Integer) view.getTag();
//                    Intent intent = new Intent(rootView.getContext(), ProfileActivity.class);
//                    intent.putExtra("swapper info", swapBodyList.get(position));
//                    startActivity(intent);
//                }
//            });
//
//        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                int position = (Integer) view.getTag();
                String imageTransitionName = getString(R.string.image_transition_name);
                String listItemTransitionName = getString(R.string.list_item_transition_name);
                View swapper_image = view.findViewById(R.id.swapper_image);
                View cardView = view.findViewById(R.id.listItemCardView);
                Pair<View, String> p1 = Pair.create(swapper_image, imageTransitionName);
                Pair<View, String> p2 = Pair.create(cardView, listItemTransitionName);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), p1, p2);
                SwapDetails swapDetails = swapBodyList.get(i);
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("swapper info", swapDetails);
                startActivity(intent, options.toBundle());
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
