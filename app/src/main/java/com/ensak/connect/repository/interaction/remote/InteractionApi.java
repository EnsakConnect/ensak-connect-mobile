package com.ensak.connect.repository.interaction.remote;


import com.ensak.connect.repository.interaction.model.InteractionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InteractionApi {
    @GET("interactions/answer/{id}/up")
    Call<InteractionResponse> interactAnswerUp(@Path("id") Integer id);

    @GET("interactions/answer/{id}/down")
    Call<InteractionResponse> interactAnswerDown(@Path("id") Integer id);


}
