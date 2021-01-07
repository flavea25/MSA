package com.example.lostfoundpets.ui.addpost;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;

public class AddPostFragment extends Fragment {

    private final PostModel newPost = new PostModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_post, container, false);

        manageSpinners(root);
        manageRadioButtons(root);
        manageButtons(root);

        return root;
    }

    private void manageButtons(View root) {
        Button confirm = root.findViewById(R.id.button_confirm);
        confirm.setOnClickListener(v -> {
            TextInputEditText description = root.findViewById(R.id.description);
            newPost.setDetails(description.getText().toString());

            //TODO post to FireBase DataBase -> MACAR SA AFISEZE
            Toast.makeText(this.getActivity(), "Your post has been published!", Toast.LENGTH_SHORT).show();

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.home_fragment, HomeFragment.class,null)
                    .addToBackStack(null)
                    .commit();
        });

        Button cancel = root.findViewById(R.id.button_cancel);
        cancel.setOnClickListener(v -> getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.home_fragment, HomeFragment.class,null)
                .addToBackStack(null)
                .commit());
    }

    private void manageRadioButtons(View root) {
        RadioButton lostChoice = root.findViewById(R.id.radio_lost);
        lostChoice.setOnClickListener(v -> newPost.setCategory(lostChoice.getText().toString()));

        RadioButton foundChoice = root.findViewById(R.id.radio_found);
        foundChoice.setOnClickListener(v -> newPost.setCategory(foundChoice.getText().toString()));

        RadioButton adoptChoice = root.findViewById(R.id.radio_adopt);
        adoptChoice.setOnClickListener(v -> newPost.setCategory(adoptChoice.getText().toString()));
    }

    private void manageSpinners(View root) {
        String[] colors = {"Brown", "Black", "White", "Tiger", "Red", "Orange", "Mixed", "IDK"};
        String[] pets = {"Dog", "Cat", "Other"};
        String[] sexes = {"Female", "Male", "IDK"};
        String[] locations = {"Dumbravita", "Calea Aradului", "Complexul Studentesc", "Dacia", "Calea Sagului", "Mosnita Noua", "IDK"};

        Spinner colorSpinner = root.findViewById(R.id.spinner_color);
        Spinner sexSpinner = root.findViewById(R.id.spinner_sex);
        Spinner petSpinner = root.findViewById(R.id.spinner_pet);
        Spinner locationSpinner = root.findViewById(R.id.spinner_location);

        ArrayAdapter<String> colorsAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, colors);
        ArrayAdapter<String> sexesAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, sexes);
        ArrayAdapter<String> petsAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, pets);
        ArrayAdapter<String> locationsAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, locations);

        manageSpinner(colorSpinner, colorsAdapter, colors, "color");
        manageSpinner(sexSpinner, sexesAdapter, sexes, "sex");
        manageSpinner(petSpinner, petsAdapter, pets, "pet");
        manageSpinner(locationSpinner, locationsAdapter, locations, "location");
    }

    private void manageSpinner(Spinner spinner, ArrayAdapter<String> adapter, String[] option, String methodUsed) {
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (methodUsed){
                    case "color": newPost.setColor(option[position]); break;
                    case "sex": newPost.setSex(option[position]); break;
                    case "pet": newPost.setPet(option[position]); break;
                    case "location": newPost.setLocation(option[position]); break;
                    default: break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}