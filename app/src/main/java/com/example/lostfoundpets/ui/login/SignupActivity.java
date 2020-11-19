package com.example.lostfoundpets.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.login.SignupViewModel;
import com.example.lostfoundpets.ui.login.SignupViewModelFactory;

public class SignupActivity extends AppCompatActivity {

    private SignupViewModel signupViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupViewModel = new ViewModelProvider(this, new SignupViewModelFactory())
                .get(SignupViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button signupButton = findViewById(R.id.signup);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        signupViewModel.getSignupFormState().observe(this, signupFormState -> {
            if (signupFormState == null) {
                return;
            }
            signupButton.setEnabled(signupFormState.isDataValid());
            if (signupFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(signupFormState.getUsernameError()));
            }
            if (signupFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(signupFormState.getPasswordError()));
            }
        });

        signupViewModel.getSignupResult().observe(this, signupResult -> {
            if (signupResult == null) {
                return;
            }
            loadingProgressBar.setVisibility(View.GONE);
            if (signupResult.getError() != null) {
                showSignupFailed(signupResult.getError());
            }
            if (signupResult.getSuccess() != null) {
                updateUiWithUser(signupResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy signup activity once successful
            finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                signupViewModel.signupDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signupViewModel.signup(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        signupButton.setOnClickListener(v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            signupViewModel.signup(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showSignupFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}