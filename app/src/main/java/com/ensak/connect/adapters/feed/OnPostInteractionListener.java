package com.ensak.connect.adapters.feed;

import com.ensak.connect.repository.feed.model.FeedContentResponse;

public interface OnPostInteractionListener {
    void onJobApply(int position);

    void likeDislikeQuestionPost(FeedContentResponse post, int position);
    void likeDislikeBlogPost(FeedContentResponse post, int position);
}
