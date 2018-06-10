package com.example.harsha.bunkmanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BunkDateAdapter extends RecyclerView.Adapter<BunkDateAdapter.ViewHolder> {

    private JSONObject obj;
    public View layout;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            layout=itemView;
            date=(TextView) itemView.findViewById(R.id.date);
        }
    }

    public BunkDateAdapter(JSONObject subject_detail){
        obj=subject_detail;
    }

    @Override
    public BunkDateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.subject_layout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position){

        try {

            JSONArray d=obj.getJSONArray("date");
            Toast.makeText(layout.getContext(),d.getString(position),Toast.LENGTH_SHORT).show();
            holder.date.setText(d.getString(position));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount(){
        int count=0;
        try {
            count= obj.getJSONArray("date").length();       //return size
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }
}
