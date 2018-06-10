package com.example.harsha.bunkmanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private JSONArray input;
    private String obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab=(FloatingActionButton) findViewById(R.id.add_subject);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,SubjectInput.class);
                startActivity(intent);

            }
        });




        //File file = new File("subject.json");
        //if(file.exists()) {



            try {
                obj="[{\"name\":\"harsha\",\"date\":[\"getter\",\"setter\"]},{\"name\":\"hello\"}]";
                input = new JSONArray(obj);
                recycle();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}


    }

    private void recycle(){
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }
}
