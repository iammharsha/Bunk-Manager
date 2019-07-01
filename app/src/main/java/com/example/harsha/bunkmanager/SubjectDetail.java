package com.example.harsha.bunkmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
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
    private TextView subName;
    private TextView minPercent;
    private JSONObject subjects;
    private JSONArray in;
    private String m_Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_detail);


        Intent intent=getIntent();
        final int position=intent.getIntExtra("pos",0);
        final String input=intent.getStringExtra("value");

        try {
            in=new JSONArray(input);
            subjects= in.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this,Integer.toString(position)+" "+input,Toast.LENGTH_SHORT).show();



        minPercent=(TextView) findViewById(R.id.min_percent);
        subName=(TextView) findViewById(R.id.subject);
        add=(ImageView) findViewById(R.id.add);
        remove=(ImageView) findViewById(R.id.remove);
        bunk=(TextView) findViewById(R.id.bunk);
        try {
            subName.setText(subjects.getString("name"));
            bunk.setText(subjects.getString("total_bunk"));
            minPercent.setText(subjects.getString("percent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        minPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPercent(position,v);
            }
        });

        subName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogName(position,v);
            }
        });

        bunk.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                dialogTotalBunk(position,v);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int b=Integer.parseInt(bunk.getText().toString());
                try {
                    if(!subjects.has("date")||subjects.getJSONArray("date").length()<=b) {
                        b++;
                        bunk.setText(Integer.toString(b));
                        editTotalBunk(position, Integer.toString(b), v, 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyAdapter.getInstance().update();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int b=Integer.parseInt(bunk.getText().toString());
                try {
                    if(!subjects.has("date")||subjects.getJSONArray("date").length()<=b) {
                        if (b == 0) {
                            Toast.makeText(v.getContext(), "Can't Deduce", Toast.LENGTH_SHORT).show();
                        } else
                            b--;
                        bunk.setText(Integer.toString(b));
                        editTotalBunk(position, Integer.toString(b), v, 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MyAdapter.getInstance().update();
            }
        });

        DateAdapter(position);


    }



    public void DateAdapter(int position){

        recyclerView = (RecyclerView) findViewById(R.id.bunk_dates);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter=new BunkDateAdapter(subjects,position);
        recyclerView.setAdapter(mAdapter);
    }

    private void editTotalBunk(int position,String b,View v,int type)
    {
        try {
            final JSONManip JSONManipObj=JSONManip.getInstance();
            JSONManipObj.editJSON(in,position,type,b,v.getContext());
            MyAdapter.getInstance().update();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void dialogTotalBunk(final int position,final View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Total Hours");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if(validate(m_Text)&&(Integer.parseInt(m_Text)>=0)) {
                    bunk.setText(m_Text);
                    editTotalBunk(position, m_Text, v, 0);
                    MyAdapter.getInstance().update();
                }
                else{
                    Toast.makeText(v.getContext(),"Enter valid Input",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    public void dialogName(final int position,final View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Subject Name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setText(subName.getText());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if(validate(m_Text)) {
                    subName.setText(m_Text);
                    editTotalBunk(position, m_Text, v, 1);
                    MyAdapter.getInstance().update();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void dialogPercent(final int position, final View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Minimum Percent");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                if(validate(m_Text)&&Integer.parseInt(m_Text)>=0) {
                    minPercent.setText(m_Text);
                    editTotalBunk(position, m_Text, v, 2);
                    MyAdapter.getInstance().update();
                }
                else {
                    Toast.makeText(v.getContext(),"Enter valid Input",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


    }

    public boolean validate(String s){
        return s != null && !s.equals("");
    }
}