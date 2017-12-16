package com.example.myapplication;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by 上官轩明 on 2017/11/6.
 */

public interface APIInterface {
    @GET("{index}")
    Call<NewsContentBean> repo(@Path("index") int index );
}
