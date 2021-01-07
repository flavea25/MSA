package com.example.lostfoundpets.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    FirebaseAuth fAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        fAuth = FirebaseAuth.getInstance();
        Button loginButton = (Button)root.findViewById(R.id.login_button);
        TextView textView = root.findViewById(R.id.notLogged);
        
        if(fAuth.getCurrentUser() != null){
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.profile_fragment, LoggedProfileFragment.class,null)
                    .commit();
            loginButton.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }

        loginButton.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.profile_fragment, LoginFragment.class,null)
                    .commit();
            loginButton.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        });

        return root;
    }
}