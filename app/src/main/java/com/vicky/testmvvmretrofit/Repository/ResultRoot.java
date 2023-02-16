package com.vicky.testmvvmretrofit.Repository;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultRoot {
    @SerializedName("Search")
    public ArrayList<ResultModel> search;
    public String totalResults;
    @SerializedName("Response")
    public String response;

    public ArrayList<ResultModel> getSearch() {
        return search;
    }

    public void setSearch(ArrayList<ResultModel> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
