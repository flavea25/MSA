package com.example.lostfoundpets.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lostfoundpets.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoggedProfileFragment extends Fragment {
    FirebaseAuth fAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile_logged, container, false);
        fAuth = FirebaseAuth.getInstance();

        Button logoutButton = (Button)root.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            fAuth.signOut();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.profile_fragment, ProfileFragment.class,null)
                    .addToBackStack(null)
                    .commit();
        });

        return root;
    }
}