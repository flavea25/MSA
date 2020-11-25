package com.example.lostfoundpets.ui.found;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoundViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public FoundViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Found Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}