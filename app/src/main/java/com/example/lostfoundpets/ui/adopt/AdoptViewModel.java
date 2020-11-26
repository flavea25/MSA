package com.example.lostfoundpets.ui.adopt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdoptViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AdoptViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Adopt Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}