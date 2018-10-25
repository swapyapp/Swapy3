package com.app.muhammadgamal.swapy.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.ProfileActivity;
import com.app.muhammadgamal.swapy.Activities.SwapCreationActivity;
import com.app.muhammadgamal.swapy.Common;
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

import static com.app.muhammadgamal.swapy.Common.isNetworkAvailable;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ImageView imgFilter;
    Dialog filterDialog;
    ImageView imgCloseFilterDialog;
    // List view that represent teh swap data
    ListView swapList;
    TextView empty_view, empty_view2;
    SwipeRefreshLayout homeSwipeRefresh;
    FloatingActionButton fab_add_swap;
    NetworkInfo networkInfo;
    ConnectivityManager cm;
    DatabaseReference mSwapDataBaseReference;
    FirebaseDatabase mFirebaseDatabase;
    Button homeSwapButton;
    View rootView;
    ListView listView;
    private SwapAdapter swapAdapter;
    private ProgressBar progressBar;


    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");


        homeSwapButton = rootView.findViewById(R.id.btnHomeSwapList);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mSwapDataBaseReference = mFirebaseDatabase.getReference().child("swaps");

        cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();

        fab_add_swap = rootView.findViewById(R.id.fab_add_swap);


        progressBar = rootView.findViewById(R.id.progressBar_home);
        empty_view = rootView.findViewById(R.id.empty_view);
        empty_view2 = rootView.findViewById(R.id.empty_view2);
        progressBar.setVisibility(View.VISIBLE);
        empty_view2.setVisibility(View.GONE);
        fab_add_swap.setVisibility(View.GONE);

        fetchData();

        //handle the SwipeRefreshLayout
        homeSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.homeSwipeRefresh);
        homeSwipeRefresh.setOnRefreshListener(this);
        homeSwipeRefresh.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        fab_add_swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SwapCreationActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    //show swaps in home fragment
    @SuppressLint("RestrictedApi")
    private void fetchData() {
        // If there is a network connection, fetch data
        if (Common.isNetworkAvailable(getContext()) || Common.isWifiAvailable(getContext())) {
            ChildEventListener mChildEventListener = new ChildEventListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    SwapDetails swapDetails = dataSnapshot.getValue(SwapDetails.class);
                    //check if there is no swaps
                    if (swapDetails.getSwapperID() == null) {
                        empty_view.setText(R.string.no_swaps_found);
                        empty_view2.setVisibility(View.VISIBLE);
                    } else {
                        swapAdapter.add(swapDetails);
                    }

                    progressBar.setVisibility(View.GONE);
                    fab_add_swap.setVisibility(View.VISIBLE);
                    empty_view.setVisibility(View.GONE);
                    empty_view2.setVisibility(View.GONE);

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
            Collections.reverse(swapBodyList);
            swapAdapter = new SwapAdapter(getContext(), R.layout.home_list_item, swapBodyList);
            listView = rootView.findViewById(R.id.homeList);
            listView.setVisibility(View.VISIBLE);
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

                    //image and card views transitions
                    String imageTransitionName = getString(R.string.image_transition_name);
                    String listItemTransitionName = getString(R.string.list_item_transition_name);
                    View swapper_image = view.findViewById(R.id.swapper_image);
                    View cardView = view.findViewById(R.id.listItemCardView);
                    Pair<View, String> p1 = Pair.create(swapper_image, imageTransitionName);
                    Pair<View, String> p2 = Pair.create(cardView, listItemTransitionName);
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), p1, p2);


                    SwapDetails swapDetails = swapBodyList.get(adapterView.getCount() - i - 1);
                    Intent intent = new Intent(getContext(), ProfileActivity.class);
                    intent.putExtra("swapper info", swapDetails);
                    startActivity(intent, options.toBundle());
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            if (listView != null) {
                listView.setVisibility(View.INVISIBLE);
            }
            empty_view.setVisibility(View.VISIBLE);
            empty_view.setText(R.string.no_internet_connection);
            empty_view2.setVisibility(View.VISIBLE);
            fab_add_swap.setVisibility(View.GONE);
        }
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

    @Override
    public void onRefresh() {
        fetchData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                homeSwipeRefresh.setRefreshing(false);
            }
        }, 4000);
    }
}
