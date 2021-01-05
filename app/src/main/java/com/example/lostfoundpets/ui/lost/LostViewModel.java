package com.example.lostfoundpets.ui.lost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LostViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public LostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lost Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}