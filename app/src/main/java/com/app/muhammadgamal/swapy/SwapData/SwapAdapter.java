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
import android.widget.TextView;

import com.app.muhammadgamal.swapy.Activities.ProfileActivity;
import com.app.muhammadgamal.swapy.R;
import com.bumptech.glide.Glide;

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
        SwapDetails swapBody = getItem(position);
        ImageView swapperImage = convertView.findViewById(R.id.swapper_image);
        TextView swapperName = convertView.findViewById(R.id.swapper_name);
        TextView swapperShiftTime = convertView.findViewById(R.id.swapper_shift_time);
        TextView swapperShiftDay = convertView.findViewById(R.id.swapper_shift_day);
        TextView swapperPreferredShift = convertView.findViewById(R.id.swapper_preferred_shift);
        TextView swapperShiftDate = convertView.findViewById(R.id.swapper_shift_date);
        Button homeSwapButton = convertView.findViewById(R.id.btnHomeSwapList);


        User user = new User();
        boolean isPhoto = user.getmProfilePhotoURL() != null;
        // set the swapper Image to user Image
        if (isPhoto) {
            Glide.with(swapperImage.getContext())
                    .load(user.getmProfilePhotoURL())
                    .into(swapperImage);
        } else {
            // set the swapper Image to default if no image provided
            Resources resources = convertView.getResources();
            Drawable photoUrl = resources.getDrawable(R.drawable.male_circle_512);
            swapperImage.setImageDrawable(photoUrl);
        }

        final Context context = convertView.getContext();

        swapperName.setText(user.getmUsername());
        if (swapBody != null) {
            swapperShiftTime.setText(swapBody.getSwapperShiftTime());
            swapperShiftDay.setText(swapBody.getSwapperShiftDay());
            swapperPreferredShift.setText(swapBody.getSwapperPreferredShift());
            swapperShiftDate.setText(swapBody.getSwapShiftDate());
        }

        homeSwapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
