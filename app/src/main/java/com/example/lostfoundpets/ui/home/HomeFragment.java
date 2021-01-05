package com.example.lostfoundpets.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.adopt.AdoptFragment;
import com.example.lostfoundpets.ui.found.FoundFragment;
import com.example.lostfoundpets.ui.lost.LostFragment;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton lostButton = root.findViewById(R.id.lost_button);
        ImageButton foundButton = root.findViewById(R.id.found_button);
        ImageButton adoptButton = root.findViewById(R.id.adopt_button);

        lostButton.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.home_fragment, LostFragment.class,null)
//                    .addToBackStack(null)
                    .commit();
            makeButtonsDisappear(lostButton, foundButton, adoptButton);
        });
        foundButton.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.home_fragment, FoundFragment.class,null)
//                    .addToBackStack(null)
                    .commit();
            makeButtonsDisappear(lostButton, foundButton, adoptButton);
        });
        adoptButton.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.home_fragment, AdoptFragment.class,null)
//                    .addToBackStack(null)
                    .commit();
            makeButtonsDisappear(lostButton, foundButton, adoptButton);
        });

        return root;
    }

    private void makeButtonsDisappear(ImageButton lostButton, ImageButton foundButton, ImageButton adoptButton) { //TODO replace
        lostButton.setVisibility(View.GONE);
        foundButton.setVisibility(View.GONE);
        adoptButton.setVisibility(View.GONE);
    }
}