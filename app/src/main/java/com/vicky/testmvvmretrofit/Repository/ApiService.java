package com.vicky.testmvvmretrofit.Repository;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/?apikey=b9bd48a6&s=Marvel&type=movie")
    Call<ResultRoot> getRequest();
}
