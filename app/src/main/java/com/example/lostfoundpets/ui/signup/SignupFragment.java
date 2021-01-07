package com.example.lostfoundpets.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lostfoundpets.MainActivity;
import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.login.LoginFragment;
import com.example.lostfoundpets.ui.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {

    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mPhoneNumber;
    Button mRegisterBtn;
    TextView mLoginBtn, title;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        super.onCreate(savedInstanceState);
        mFullName   = root.findViewById(R.id.fullname);
        mEmail      = root.findViewById(R.id.et_email);
        mPassword   = root.findViewById(R.id.et_password);
        mPhoneNumber = root.findViewById(R.id.phoneNumber);
        mRegisterBtn= root.findViewById(R.id.btn_signup);
        mLoginBtn   = root.findViewById(R.id.btn_login);
        title = root.findViewById(R.id.signuptxt);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getContext(), MainActivity.class));
        }

        mLoginBtn.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.profile_fragment, LoginFragment.class,null)
                    .commit();
        });


        mRegisterBtn.setOnClickListener(v -> {
            final String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            final String fullName = mFullName.getText().toString();
            final String phoneNumber    = mPhoneNumber.getText().toString();

            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is Required.");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required.");
                return;
            }

            if(password.length() < 6){
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }


            // register the user in firebase

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    // send verification link

                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification()
                            .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Log.d(TAG, "onFailure: Email not sent " + e.getMessage()));
                    userID = fAuth.getCurrentUser().getUid();

                    DocumentReference documentReference = fStore.collection("users").document(userID);

                    Map<String,Object> user = new HashMap<>();
                    user.put("fullName",fullName);
                    user.put("email",email);
                    user.put("phoneNumber",phoneNumber);

                    documentReference.set(user)
                            .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: user Profile is created for "+ userID))
                            .addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.toString()));

                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.profile_fragment, ProfileFragment.class,null)
                            .commit();

                }else {
                    Toast.makeText(getContext(), "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        return root;
    }
}