package com.example.lostfoundpets.ui.lost;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostfoundpets.R;

public class LostFragment extends Fragment {

    public static LostFragment newInstance() {
        return new LostFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LostViewModel lostViewModel = new ViewModelProvider(this).get(LostViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lost, container, false);

        final TextView textView = root.findViewById(R.id.text_lost);
        lostViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}