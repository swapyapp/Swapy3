package com.app.muhammadgamal.swapy.SwapData;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.ProfileActivity;
import com.app.muhammadgamal.swapy.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SwapAdapter extends ArrayAdapter<SwapDetails> {

    public SwapAdapter(Context context, int resource, List<SwapDetails> sampleArrayList) {
        super(context, resource, sampleArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_list_item, parent, false);
        }

        final Context context = convertView.getContext();

        final SwapDetails swapBody = getItem(position);
        Button homeSwapButton = convertView.findViewById(R.id.btnHomeSwapList);
        ImageView swapperImage = convertView.findViewById(R.id.swapper_image);
        TextView swapperName = convertView.findViewById(R.id.swapper_name);
        TextView swapperShiftTime = convertView.findViewById(R.id.swapper_shift_time);
        TextView swapperShiftDay = convertView.findViewById(R.id.swapper_shift_day);
        TextView swapperPreferredShift = convertView.findViewById(R.id.swapper_preferred_shift);
        TextView swapperShiftDate = convertView.findViewById(R.id.swapper_shift_date);
        ProgressBar progressBarListItem = convertView.findViewById(R.id.progressBarListItem);


//        String userId = "";
        if (swapBody != null) {
            swapperShiftTime.setText(swapBody.getSwapperShiftTime());
            swapperShiftDay.setText(swapBody.getSwapperShiftDay());
            swapperPreferredShift.setText(swapBody.getSwapperPreferredShift());
            swapperShiftDate.setText(swapBody.getSwapShiftDate());
            swapperName.setText(swapBody.getSwapperName());
            if (swapBody.getSwapperImageUrl()!= null){
                progressBarListItem.setVisibility(View.VISIBLE);
                Glide.with(swapperImage.getContext())
                        .load(swapBody.getSwapperImageUrl())
                        .into(swapperImage);
            }else {
                // set the swapper Image to default if no image provided
                Resources resources = context.getResources();
                Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
                swapperImage.setImageDrawable(photoUrl);
            }
            progressBarListItem.setVisibility(View.GONE);
//            userId = swapBody.getSwapperID();
        }

//        if (homeSwapButton != null) {
//            homeSwapButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = (Integer) view.getTag();
//                    Intent intent = new Intent(getContext(), ProfileActivity.class);
//                    intent.putExtra("swapper info", position);
//                    context.startActivity(intent);
//                }
//            });

//        }
//        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
//        userDb.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                if (user.getmProfilePhotoURL() != null) {
//                    Glide.with(swapperImage.getContext())
//                            .load(user.getmProfilePhotoURL())
//                            .into(swapperImage);
//                } else {
//                    // set the swapper Image to default if no image provided
//                    Resources resources = context.getResources();
//                    Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
//                    swapperImage.setImageDrawable(photoUrl);
//                }
//                swapperName.setText(user.getmUsername());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



        return convertView;
    }
}
