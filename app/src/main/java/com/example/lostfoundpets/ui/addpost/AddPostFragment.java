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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AddPostFragment extends Fragment {

    private final PostModel newPost = new PostModel();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_post, container, false);

        processPhoneNumber();
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
            //TODO set photo
            postToFirebaseCloud();
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

    private void processPhoneNumber() {
        AtomicReference<String> pn = new AtomicReference<>("");
        db.collection("users")
            .whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail())
            .get()
            .addOnCompleteListener(task -> {
                if(task.isSuccessful() && !task.getResult().isEmpty()) {
                    newPost.setPhoneNumber(task.getResult().getDocuments().get(0).get("phoneNumber").toString());
                }
                else {
                    Toast.makeText(this.getActivity(), "Couldn't retrieve phone number! :(", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void postToFirebaseCloud() {
        if(newPost.details == null || newPost.details.equals("")) {
            Toast.makeText(getActivity(), "*Add description!", Toast.LENGTH_SHORT).show();
        }
        //TODO same check with photo

        else{
            Map<String, Object> post = new HashMap<>();
            post.put("category", newPost.getCategory());
            post.put("sex", newPost.getSex());
            post.put("color", newPost.getColor());
            post.put("status", "ACTIVE");
            post.put("details", newPost.getDetails());
            post.put("location", newPost.getLocation());
            post.put("pet", newPost.getPet());
            post.put("phoneNumber", newPost.getPhoneNumber());
            //TODO photoReference

            db.collection("posts")
                    .add(post)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(getActivity(), "Your post has been published!", Toast.LENGTH_SHORT).show();

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.home_fragment, HomeFragment.class,null)
                                .addToBackStack(null)
                                .commit();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Error adding document!", Toast.LENGTH_SHORT).show());
        }
    }

    private void manageRadioButtons(View root) {
        RadioGroup radioCategory = root.findViewById(R.id.radio_category);
        RadioButton radioInitial = root.findViewById(radioCategory.getCheckedRadioButtonId());
        newPost.setCategory(radioInitial.getText().toString());

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