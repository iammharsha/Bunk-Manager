package com.example.harsha.bunkmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private JSONArray values;
    private JSONManip JSONManipObj;
    private static MyAdapter MyAdapterInstance;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public LinearLayout row;
        public TextView txtHeader;
        public TextView bunk_percent;
        public View layout;
        public TextView add_bunk;
        public LinearLayout bunk_percent_holder;
        private final Context context;

        public ViewHolder(View v) {
            super(v);
            context=v.getContext();
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            add_bunk = (TextView) v.findViewById(R.id.add_bunk);
            bunk_percent=(TextView) v.findViewById(R.id.bunk_percent);
            row=(LinearLayout) v.findViewById(R.id.row_sub);
            bunk_percent_holder=(LinearLayout) v.findViewById(R.id.bunk_percent_holder);
        }


    }

   /* public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }*/

   public static MyAdapter getInstance(){
       return MyAdapterInstance;
   }

   public void update(){
       notifyDataSetChanged();
   }

    public void remove(final int position,final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        JSONManipObj.removeJSON(context,position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,values.length());
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


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(JSONArray myDataset){//List<String> myDataset) {
        values = myDataset;
        JSONManipObj=JSONManip.getInstance();
        MyAdapterInstance=this;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       /* final String name = values.get(position);
        holder.txtHeader.setText(name);
        holder.txtFooter.setText("Footer: " + name);*/


           try {
               int tb=0;
               JSONObject obj=values.getJSONObject(position);
               holder.txtHeader.setText(obj.getString("name"));
               int p=Integer.parseInt(obj.getString("percent"));
               int b=Integer.parseInt(obj.getString("total_bunk"));
               if(obj.has("date")) {
                   tb=obj.getJSONArray("date").length();
               }
               int per=100;
               if(b!=0)
                per=100-(100*tb/b);
               GradientDrawable gd=(GradientDrawable) holder.bunk_percent_holder.getBackground();
               if(per<p) {
                   gd.setColor(Color.parseColor("#B71C1C"));
               }
               else if(per>=p&&per<=(p+5)){
                   gd.setColor(Color.parseColor("#FDD835"));
               }
               else{
                   gd.setColor(Color.parseColor("#64DD17"));
               }
               holder.bunk_percent.setText(Integer.toString(per));
           } catch (JSONException e) {
               e.printStackTrace();
           }



        holder.row.setOnClickListener(new View.OnClickListener(){


                @Override
            public void onClick(View v) {
                    //String hello=txtHeader.getText();
                final Intent intent=new Intent(v.getContext(),SubjectDetail.class);
                intent.putExtra("pos",position);
                intent.putExtra("value",values.toString());
                v.getContext().startActivity(intent);
            }

        });

           holder.add_bunk.setOnClickListener(new View.OnClickListener(){

               @Override
               public void onClick(View v){
                   Date d = null;
                   try {

                       if (values.getJSONObject(position).getJSONArray("date").length() <= Integer.parseInt(values.getJSONObject(position).getString("total_bunk"))){
                           d = new Date();
                           CharSequence s = DateFormat.format("MMMM d, yyyy", d.getTime());
                           JSONManipObj.editJSON(values, position, 4, s.toString(), v.getContext());
                           notifyDataSetChanged();
                        }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

               }

           });

           holder.row.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   final Context context=v.getContext();
                   remove(position,context);
                   return false;
               }
           });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.length();
    }
}
