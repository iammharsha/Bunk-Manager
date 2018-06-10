package com.example.harsha.bunkmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SubjectDetail extends Activity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView add;
    private ImageView remove;
    private TextView bunk;
    private JSONObject subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_detail);
        Intent intent=getIntent();
        int position=intent.getIntExtra("pos",0);
        String input=intent.getStringExtra("value");
        try {
            JSONArray in=new JSONArray(input);
            subjects= in.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,Integer.toString(position)+" "+input,Toast.LENGTH_SHORT).show();



        add=(ImageView) findViewById(R.id.add);
        remove=(ImageView) findViewById(R.id.remove);
        bunk=(TextView) findViewById(R.id.bunk);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int b=Integer.parseInt(bunk.getText().toString());
                b++;
                bunk.setText(Integer.toString(b));
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int b=Integer.parseInt(bunk.getText().toString());
                if(b==0){
                    Toast.makeText(v.getContext(),"Can't Deduce",Toast.LENGTH_SHORT).show();
                }
                else
                    b--;
                bunk.setText(Integer.toString(b));
            }
        });

        DateAdapter();
        /*String filename = "myfile";
        String fileContents = "Hello world!";
        FileOutputStream outputStream;
        FileInputStream fin;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            fin=openFileInput(filename);
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            Toast.makeText(this,temp,Toast.LENGTH_SHORT).show();
            fin.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/



        /*List<String> input = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            input.add("Test" + i);*/
        //}// define an adapter
        //mAdapter = new MyAdapter(input);
        //recyclerView.setAdapter(mAdapter);

    }

    public void DateAdapter(){

        recyclerView = (RecyclerView) findViewById(R.id.bunk_dates);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter=new BunkDateAdapter(subjects);
        recyclerView.setAdapter(mAdapter);
    }
}
