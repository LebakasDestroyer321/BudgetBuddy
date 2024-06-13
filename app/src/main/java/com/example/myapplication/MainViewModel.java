package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<String> title = new MutableLiveData<>();
    private MutableLiveData<String> explanation = new MutableLiveData<>();
    private MutableLiveData<String> imageUrl = new MutableLiveData<>();

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getExplanation() {
        return explanation;
    }

    public LiveData<String> getImageUrl() {
        return imageUrl;
    }

    public void setData(String title, String explanation, String imageUrl) {
        this.title.setValue(title);
        this.explanation.setValue(explanation);
        this.imageUrl.setValue(imageUrl);
    }

}
