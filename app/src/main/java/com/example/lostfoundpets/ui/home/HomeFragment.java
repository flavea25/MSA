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

        lostButton.setOnClickListener(v -> getActivity()
            .getSupportFragmentManager()
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.nav_host_fragment, LostFragment.class,null)
//            .addToBackStack("home")
            .commit());
        foundButton.setOnClickListener(v -> getActivity()
            .getSupportFragmentManager()
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.nav_host_fragment, FoundFragment.class,null)
            .commit());
        adoptButton.setOnClickListener(v -> getActivity()
            .getSupportFragmentManager()
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.nav_host_fragment, AdoptFragment.class,null)
            .commit());

        return root;
    }
}