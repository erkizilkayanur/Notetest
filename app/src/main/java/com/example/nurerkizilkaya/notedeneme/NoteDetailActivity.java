package com.example.nurerkizilkaya.notedeneme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nurerkizilkaya.notedeneme.model.Notes;
import com.example.nurerkizilkaya.notedeneme.webservices.NotesMethods;
import com.example.nurerkizilkaya.notedeneme.webservices.NotesRetroClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteDetailActivity extends AppCompatActivity {
    @Bind(R.id.postDetailId)
    TextView postDetailId;
    @Bind(R.id.userDetailId)
    EditText userDetailId;
    @Bind(R.id.titleDetailId)
    EditText titleDetailId;
    @Bind(R.id.descriptionDetailId)
    EditText descriptionDetailId;


    @Bind(R.id.updateBtnId)
    Button updateBtnId;
    @OnClick(R.id.updateBtnId) void updateNote()
    {
        Notes notes =new Notes();
        notes.setId(Integer.valueOf(postDetailId.getText().toString()));
        notes.setUserId(Integer.valueOf(userDetailId.getText().toString()));
        notes.setTitle(titleDetailId.getText().toString());
        notes.setBody(descriptionDetailId.getText().toString());


        final NotesMethods noteApi=NotesRetroClient.getClient().create(NotesMethods.class);
        final Call<Notes[]> call=noteApi.updateNoteDetail(postDetailId.getText().toString(),notes);
        call.enqueue(new Callback<Notes[]>() {
            @Override
            public void onResponse(Call<Notes[]> call, Response<Notes[]> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<Notes[]> call, Throwable t) {

            }
        });


    }


    @Bind(R.id.deleteBtnId)
    Button deleteBtnId;
    @OnClick(R.id.deleteBtnId) void deleteNote()
    {
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Do you really want to whatever?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        final NotesMethods noteApi = NotesRetroClient.getClient().create(NotesMethods.class);
                        final Call<Notes> call=noteApi.deleteNoteDetail(postDetailId.getText().toString());
                        call.enqueue(new Callback<Notes>() {
                            @Override
                            public void onResponse(Call<Notes> call, Response<Notes> response) {
                                Notes notes =response.body();
                                if(notes.getBody()==null)
                                {
                                    Toast.makeText(getApplication(),"Veri silinmişir.",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Notes> call, Throwable t) {

                            }
                        });
                    }})
                .setNegativeButton(android.R.string.no, null).show();





    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        String postId=bundle.getString("postId");
        if(postId!=null)
        {
            final NotesMethods noteApi= NotesRetroClient.getClient().create(NotesMethods.class);
            final Call<Notes> call=noteApi.getItemDetailNote(postId);
            call.enqueue(new Callback<Notes>() {
                @Override
                public void onResponse(Call<Notes> call, Response<Notes> response) {
                    Notes note=response.body();
                    postDetailId.setText(note.getId().toString());
                    userDetailId.setText(note.getUserId().toString());
                    titleDetailId.setText(note.getTitle().toString());
                    descriptionDetailId.setText(note.getBody().toString());
                }

                @Override
                public void onFailure(Call<Notes> call, Throwable t) {

                }
            });

        }
    }
}
