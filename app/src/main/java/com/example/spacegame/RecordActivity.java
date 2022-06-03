package com.example.spacegame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacegame.domain.Record;

public class RecordActivity extends AppCompatActivity {
    private EditText etLogin;
    private EditText etScore;

    private com.example.spacegame.domain.Record record;
    private com.example.spacegame.adapter.RecordAdapter adapter;

    // private final com.example.spacegame.rest.LibraryApi libraryApi = new com.example.spacegame.rest.LibraryApiImpl(this);

    @Override
    protected void onResume() {
        super.onResume();
        String BASE_URL = "http://192.168.0.207:8080";
        SharedPreferences mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);;
        BASE_URL = mSettings.getString("BASE_URL", BASE_URL);
        com.example.spacegame.rest.LibraryApi libraryApi = new com.example.spacegame.rest.LibraryApiImpl(this, BASE_URL);
        libraryApi.fillRecord();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        RecyclerView recyclerView = findViewById(R.id.list_records);
        adapter = new com.example.spacegame.adapter.RecordAdapter(this, com.example.spacegame.fakedb.LibraryFakeDb.RECORD_LIST);
        recyclerView.setAdapter(adapter);

//        Log.d("RECORD_LIST", "created");
//        AppCompatButton btn_add = findViewById(R.id.btn_add);
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String login = ((EditText)findViewById(R.id.et_login)).getText().toString();
//                int score = Integer.parseInt(((EditText)findViewById(R.id.et_score)).getText().toString());
//
//                record = new com.example.spacegame.domain.Record(
//                        0,
//                        login,
//                        score
//                );
//                new com.example.spacegame.rest.LibraryApiImpl(RecordActivity.this).newRecord(record);
//                //Toast.makeText(getApplicationContext(),login,Toast.LENGTH_LONG).show();
//            }
//        });

    }
    public void update() {
        Log.d("RECORD_LIST", "update");
        adapter.notifyDataSetChanged();
    }
//    static public void newRecord(Record record){
//        new com.example.spacegame.rest.LibraryApiImpl().newRecord(record);
//    }
}
