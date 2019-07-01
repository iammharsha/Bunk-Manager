package com.example.harsha.bunkmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JSONManip {


    private JSONArray obj;
    private static JSONManip JSONManipObj;

    static{
        JSONManipObj = new JSONManip();
    }

    public static JSONManip getInstance(){
        return JSONManipObj;
    }

    /*public JSONManip(Context c){



    }*/

    public JSONArray addJSON(String name,String total_bunk,String percent,View v) throws JSONException {

        JSONObject newObj=new JSONObject();
        newObj.put("name",name);
        newObj.put("total_bunk",total_bunk);
        newObj.put("percent",percent);
        if(obj==null) {
            obj = new JSONArray();
        }
        obj.put(newObj);
        //Toast.makeText(v.getContext(),obj.toString(), Toast.LENGTH_SHORT).show();

        writeFile(v.getContext());
        return obj;

    }

    public void removeJSON(Context c,int position){

        obj.remove(position);
        writeFile(c);

    }

    public void removeBunkDate(Context c,int position,int objPos) throws JSONException {

        JSONObject jObj=obj.getJSONObject(objPos);
        JSONArray date=jObj.getJSONArray("date");
        if(jObj.getInt("total_bunk")>=date.length()) {
            date.remove(position);
            //Toast.makeText(c,obj.toString(\),Toast.LENGTH_SHORT).show();
        /*jObj.put("date",date);
        obj.put(position,jObj);*/
            writeFile(c);
        }
        else{
            Toast.makeText(c,"Total Bunk can't be greater than Total Hours",Toast.LENGTH_SHORT).show();
        }
    }

    public void editJSON(JSONArray JSONArr,int pos,int cse,String value,Context c) throws JSONException {

        JSONObject obj1=JSONArr.getJSONObject(pos);
        JSONArray date;
        if(obj1.has("date")) {
            date = obj1.getJSONArray("date");
        }
        else{
            date=new JSONArray();
            obj1.put("date",date);
        }
        switch (cse){
            case 0:
                if(obj1.getInt("total_bunk")>=date.length()) {
                    obj1.put("total_bunk",value);
                }
                else{
                    Toast.makeText(c,"Total Bunk can't be greater than Total Hours",Toast.LENGTH_SHORT).show();
                }

                break;

            case 1:
                obj1.put("name",value);
                break;

            case 2:
                obj1.put("percent",value);
                break;

            case 3:
                if(obj1.getInt("total_bunk")>=date.length()) {
                    date.remove(Integer.parseInt(value));
                    obj1.put("date", date);
                }
                else {
                    Toast.makeText(c,"Total Bunk can't be greater than Total Hours",Toast.LENGTH_SHORT).show();
                }
                break;

            case 4:
                if(obj1.getInt("total_bunk")>=date.length()) {
                    date.put(value);
                    obj1.put("date", date);
                }
                else {
                    Toast.makeText(c,"Total Bunk can't be greater than Total Hours",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        obj.put(pos,obj1);
        writeFile(c);
    }

    private void writeFile(Context c){


            FileOutputStream outputStream;
            try {
                outputStream = c.openFileOutput("subject.json",Context.MODE_PRIVATE);
                outputStream.write(obj.toString().getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


    }

    public JSONArray readFile(Context c) throws IOException {

        FileInputStream fin = c.openFileInput("subject.json");
        int ch;
        String temp = "";
        while ((ch = fin.read()) != -1) {
            temp = temp + Character.toString((char) ch);
        }
        //Toast.makeText(c,temp,Toast.LENGTH_SHORT).show();
        fin.close();

        try {
            obj = new JSONArray(temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }


}

