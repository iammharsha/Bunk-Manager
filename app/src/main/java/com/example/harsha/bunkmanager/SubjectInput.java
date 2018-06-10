package com.example.harsha.bunkmanager;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SubjectInput extends Activity {

    private Button submit;
    private EditText name,percent,total_bunk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_input);

        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                name=(EditText) findViewById(R.id.text_name);
                total_bunk=(EditText) findViewById(R.id.text_total);
                percent=(EditText) findViewById(R.id.text_percent);

                String n=name.getText().toString();
                int tb=Integer.parseInt(total_bunk.getText().toString());
                int p=Integer.parseInt(percent.getText().toString());


            }

        });
    }
}
