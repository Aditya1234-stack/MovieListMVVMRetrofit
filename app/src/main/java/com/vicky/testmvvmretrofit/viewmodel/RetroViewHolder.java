package com.vicky.testmvvmretrofit.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vicky.testmvvmretrofit.Repository.RepositoryWebservice;
import com.vicky.testmvvmretrofit.Repository.ResultModel;

import java.util.List;

public class RetroViewHolder extends AndroidViewModel {
    private final LiveData<List<ResultModel>> resultModel;
    RepositoryWebservice repositoryWebservice;
    public RetroViewHolder(@NonNull Application application) {
        super(application);

        repositoryWebservice = new RepositoryWebservice();
        resultModel = repositoryWebservice.providesWebservice();
    }

    public LiveData<List<ResultModel>> getRetrofitListObservable()
    {
        return resultModel;
    }
}
