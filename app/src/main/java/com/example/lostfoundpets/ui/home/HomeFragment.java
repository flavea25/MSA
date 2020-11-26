package com.example.lostfoundpets.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lostfoundpets.R;

public class HomeFragment extends Fragment {

    private ImageButton lostButton;
    private ImageButton foundButton;
    private ImageButton adoptButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        lostButton = root.findViewById(R.id.lost_button);
        foundButton = root.findViewById(R.id.found_button);
        adoptButton = root.findViewById(R.id.adopt_button);

        return root;
    }
}