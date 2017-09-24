package com.example.nurerkizilkaya.notedeneme.webservices;

import com.example.nurerkizilkaya.notedeneme.model.Notes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by a s u s on 24.9.2017.
 */
public interface NotesMethods {

    @GET("posts")
    Call<Notes[]> getItemNotes();

    @GET("posts/{id}")
    Call<Notes> getItemDetailNote(@Path("id") String id);

    @PUT("posts/{id}")
    Call<Notes[]> updateNoteDetail(@Path("id") String id,@Body Notes notes);

    @DELETE("posts/{id}")
    Call<Notes> deleteNoteDetail(@Path("id") String id);
}
