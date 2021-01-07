package com.example.lostfoundpets.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lostfoundpets.MainActivity;
import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.signup.SignupFragment;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    EditText mEmail,mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginBtn = (Button)root.findViewById(R.id.btn_login);
        Button signupButton = (Button)root.findViewById(R.id.btn_signup);
        fAuth = FirebaseAuth.getInstance();

         mPassword = root.findViewById(R.id.et_password);
         mEmail = root.findViewById(R.id.et_email);

        signupButton.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.profile_fragment, SignupFragment.class,null)
                    .commit();
        });

        mLoginBtn.setOnClickListener(v -> {

            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is Required.");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required.");
                return;
            }

            // authenticate the user

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), MainActivity.class));
                }else {
                    Toast.makeText(getContext(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            });

        });

        return root;
    }
}
