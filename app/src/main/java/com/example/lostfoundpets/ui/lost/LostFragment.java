package com.example.lostfoundpets.ui.lost;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.addpost.AddPostFragment;

public class LostFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LostViewModel lostViewModel = new ViewModelProvider(this).get(LostViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lost, container, false);

        ImageButton addButton = root.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.home_fragment, AddPostFragment.class,null)
//                    .addToBackStack(null)
                    .commit();
        });

        return root;
    }

}