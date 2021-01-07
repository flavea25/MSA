package com.example.lostfoundpets.ui.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.addpost.AddPostFragment;

public class DetailsFragment extends Fragment {
    String location, description, color, pet, sex, phoneNumber;
    ImageView petImage;

    public DetailsFragment(String location, String description, String color, String pet, String sex, String phoneNumber) {
        this.description = description;
        this.location = location;
        this.color = color;
        this.pet = pet;
        this.sex = sex;
        this. phoneNumber = phoneNumber;
        //TODO image
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_details, container, false);

        setParams(root);

        Button contact = root.findViewById(R.id.button_contact);
        contact.setOnClickListener(v -> {
            //TODO open phone app
                }
        );

        return root;
    }

    private void setParams(View root) {
        ImageView officialImage = root.findViewById(R.id.details_pet_image);
        officialImage.setImageResource(R.mipmap.ic_test_pet_image_foreground);
        TextView pet = root.findViewById(R.id.details_pet);
        pet.setText(this.pet);
        TextView color = root.findViewById(R.id.details_color);
        color.setText(this.color);
        TextView sex = root.findViewById(R.id.details_sex);
        sex.setText(this.sex);
        TextView location = root.findViewById(R.id.details_location_content);
        location.setText(this.location);
        TextView description = root.findViewById(R.id.details_description_content);
        description.setText(this.description);
    }
}