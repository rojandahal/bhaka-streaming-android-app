package com.example.bhakamusic.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class SearchRequest {

    @SerializedName("searchQuery")
    private String searchQuery;

    public SearchRequest(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
