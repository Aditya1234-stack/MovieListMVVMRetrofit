package com.vicky.testmvvmretrofit.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RepositoryWebservice {

    public static OkHttpClient providerOkHttpClientBuilder(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();
    }

    List<ResultModel> webserviceResponseList = new ArrayList<>();
    public LiveData<List<ResultModel>> providesWebservice(){
        final MutableLiveData<List<ResultModel>> data = new MutableLiveData<>();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUrl.Base_url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providerOkHttpClientBuilder())
                    .build();

            ApiService apiService = retrofit.create(ApiService.class);
            apiService.getRequest().enqueue(new Callback<ResultRoot>() {
                @Override
                public void onResponse(Call<ResultRoot> call, Response<ResultRoot> response) {
                    webserviceResponseList = (response.body().getSearch());
                    data.setValue(webserviceResponseList);
                }

                @Override
                public void onFailure(Call<ResultRoot> call, Throwable t) {
                    Log.d("Repository", "Falied");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private List<ResultModel> parseJson(String response) {
        List<ResultModel> apiResults = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);

                ResultModel resultModel = new ResultModel();
                resultModel.setTitle(object.getString("Title"));
                resultModel.setYear(object.getString("Year"));
                resultModel.setYear(object.getString("Year"));

                apiResults.add(resultModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return apiResults;
    }
}
