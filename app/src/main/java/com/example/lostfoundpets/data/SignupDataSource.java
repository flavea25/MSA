package com.example.lostfoundpets.data;

import com.example.lostfoundpets.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ signup credentials and retrieves user information.
 */
public class SignupDataSource {

    public Result signup(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}