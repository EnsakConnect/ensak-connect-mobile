package com.ensak.connect.reponse.feed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedResponse {
    @SerializedName("content")
    @Expose
    public ArrayList<FeedContentResponse> content;

    @SerializedName("totalPages")
    @Expose
    private int totalPages;

    @SerializedName("number")
    @Expose
    private int pageNumber;

    public FeedResponse() {
        content = new ArrayList<>();
        totalPages = 1;
        pageNumber = 0;
    }

    public ArrayList<FeedContentResponse> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setContent(ArrayList<FeedContentResponse> content) {
        this.content.clear();
        this.content.addAll(content);
    }

    public void addContent(ArrayList<FeedContentResponse> content) {
        this.content.addAll(content);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
