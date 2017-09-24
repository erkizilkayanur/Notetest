package com.example.nurerkizilkaya.notedeneme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.nurerkizilkaya.notedeneme.adapter.NotesAdapter;
import com.example.nurerkizilkaya.notedeneme.model.Notes;
import com.example.nurerkizilkaya.notedeneme.webservices.NotesMethods;
import com.example.nurerkizilkaya.notedeneme.webservices.NotesRetroClient;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteActivity extends AppCompatActivity {

    @Bind(R.id.listRcyViewId)
    RecyclerView listRcyViewId;
    public Notes[] noteArray;

    public NotesAdapter adapter;
    public ProgressDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayoutManager.VERTICAL);
        listRcyViewId.setLayoutManager(ll);

        listRcyViewId.addOnItemTouchListener(
                new RecyclerItemClickListener(this, listRcyViewId, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                      if(noteArray!=null&&noteArray.length>0)
                      {
                          Notes selectednote=noteArray[position];

                          Intent intent=new Intent(NoteActivity.this,NoteDetailActivity.class);
                          intent.putExtra("postId",selectednote.getId().toString());
                          NoteActivity.this.startActivity(intent);
                      }

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );



        final NotesMethods noteApi= NotesRetroClient.getClient().create(NotesMethods.class);
        final Call<Notes[]> call=noteApi.getItemNotes();
        dialog = new ProgressDialog(NoteActivity.this);
        dialog.setMessage("Yükleniyor...");
        dialog.show();

        call.enqueue(new Callback<Notes[]>() {
            @Override
            public void onResponse(Call<Notes[]> call, Response<Notes[]> response) {
                noteArray = response.body();
                adapter = new NotesAdapter(Arrays.asList(noteArray));
                listRcyViewId.setAdapter(adapter);

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Notes[]> call, Throwable t) {
                Toast.makeText(getApplication(),"Sorry unsuccess:(",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
