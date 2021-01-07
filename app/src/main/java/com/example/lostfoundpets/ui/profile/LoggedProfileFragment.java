package com.example.lostfoundpets.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lostfoundpets.MainActivity;
import com.example.lostfoundpets.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoggedProfileFragment extends Fragment {
    FirebaseAuth fAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile_logged, container, false);
        fAuth = FirebaseAuth.getInstance();

        Button logoutButton = (Button)root.findViewById(R.id.logout_button);
        TextView textView = root.findViewById(R.id.notLogged);
        logoutButton.setOnClickListener(v -> {
            fAuth.signOut();
            startActivity(new Intent(getContext(), MainActivity.class));
//            logoutButton.setVisibility(View.GONE);
//            textView.setVisibility(View.GONE);
        });
        return root;
    }
}