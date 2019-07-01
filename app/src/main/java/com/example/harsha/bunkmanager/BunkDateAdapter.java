package com.example.harsha.bunkmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private JSONArray d;
    public View layout;
    private JSONManip JSONManipObj;
    private int objPos;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            layout=itemView;
            date=(TextView) itemView.findViewById(R.id.date);

        }
    }

    public BunkDateAdapter(JSONObject subject_detail,int objPosition){
        obj=subject_detail;
        JSONManipObj=JSONManip.getInstance();
        objPos=objPosition;
    }

    @Override
    public BunkDateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.subject_layout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    public void remove(final int position,final Context context,final JSONArray d) {
        final boolean[] flag = new boolean[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            JSONManipObj.removeBunkDate(context,position,objPos);
                            d.remove(position);
                            obj.put("date",d);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,getItemCount());
                            MyAdapter.getInstance().update();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){

        try {
            //final int pos=position;
            d=obj.getJSONArray("date");
            //Toast.makeText(layout.getContext(),d.getString(position),Toast.LENGTH_SHORT).show();
            holder.date.setText(d.getString(position));
            holder.date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    remove(position,v.getContext(),d);
                    /*if(remove(position,v.getContext(),d))
                    {
                        notifyItemRemoved(position);
                        notifyItemRangeRemoved(position,d.length());
                    }*/
                }

            });



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
