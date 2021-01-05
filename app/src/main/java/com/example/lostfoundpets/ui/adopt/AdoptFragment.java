package com.example.lostfoundpets.ui.adopt;

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

public class AdoptFragment extends Fragment {
    
    public static AdoptFragment newInstance() {
        return new AdoptFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AdoptViewModel adoptViewModel = new ViewModelProvider(this).get(AdoptViewModel.class);
        View root = inflater.inflate(R.layout.fragment_adopt, container, false);
        
        final TextView textView = root.findViewById(R.id.text_adopt);
        adoptViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}