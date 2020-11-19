package com.example.lostfoundpets.data;

import com.example.lostfoundpets.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of signup status and user credentials information.
 */
public class SignupRepository {

    private static volatile SignupRepository instance;

    private SignupDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private SignupRepository(SignupDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static SignupRepository getInstance(SignupDataSource dataSource) {
        if (instance == null) {
            instance = new SignupRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> signup(String username, String password) {
        // handle signup
        Result<LoggedInUser> result = dataSource.signup(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}