package com.example.harsha.bunkmanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class SubjectInput extends Activity {

    private Button submit;
    private String name,percent,total_bunk;
    private JSONArray input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_input);

        String obj="";

        final Intent intent=getIntent();

        if(intent.getBooleanExtra("avail",false)) {
            obj = intent.getStringExtra("json");
        }

        try {
            if(!obj.equals(""))
                input=new JSONArray(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(validate()) {

                    JSONManip jsonManip=JSONManip.getInstance();
                    try {
                        input=jsonManip.addJSON(name,total_bunk,percent,v);
                        Log.v("length",Integer.toString(input.length()));
                        Intent intent1=new Intent(SubjectInput.this,MainActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

        });
    }

    public boolean validate(){

            EditText nme=(EditText) findViewById(R.id.text_name);
            name=nme.getText().toString();
            if(name.matches(""))
            {
                nme.setError("Please Enter Name");
                return false;
            }

        EditText tb=(EditText) findViewById(R.id.text_total);
        total_bunk=tb.getText().toString();
        if(total_bunk.matches(""))
        {
            tb.setError("Please Enter Total Bunk");
            return false;
        }

        EditText per=(EditText) findViewById(R.id.text_percent);
        percent=per.getText().toString();
        if(percent.matches(""))
        {
            per.setError("Please Enter Maximum Percentage");
            return false;
        }

        return true;
        //int tb = Integer.parseInt(total_bunk.getText().toString());
        //int p = Integer.parseInt(percent.getText().toString());
    }
}
