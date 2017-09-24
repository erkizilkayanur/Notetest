package com.example.nurerkizilkaya.notedeneme.webservices;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by a s u s on 24.9.2017.
 */
public class NotesRetroClient {
    public static final String BASE_URL="http://jsonplaceholder.typicode.com/";
    public static Retrofit retrofit=null;

    public static  Retrofit getClient()
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(new OkHttpClient())
                    .build();
        }

        return  retrofit;
    }

}
