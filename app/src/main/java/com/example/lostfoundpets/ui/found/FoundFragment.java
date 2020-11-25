package com.example.lostfoundpets.ui.found;

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

public class FoundFragment extends Fragment {

    public static FoundFragment newInstance() {
        return new FoundFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FoundViewModel foundViewModel = new ViewModelProvider(this).get(FoundViewModel.class);
        View root = inflater.inflate(R.layout.fragment_found, container, false);
        final TextView textView = root.findViewById(R.id.text_found);
        foundViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}