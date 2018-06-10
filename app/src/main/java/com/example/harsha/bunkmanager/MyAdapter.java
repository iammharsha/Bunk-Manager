package com.example.harsha.bunkmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.support.v4.content.ContextCompat.startActivity;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private JSONArray values;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        // each data item is just a string in this case
        public RelativeLayout row;
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;
        private final Context context;

        public ViewHolder(View v) {
            super(v);
            context=v.getContext();
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            row=(RelativeLayout) v.findViewById(R.id.row_sub);

        }


    }

   /* public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }*/

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(JSONArray myDataset){//List<String> myDataset) {
        values = myDataset;
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
               JSONObject obj=values.getJSONObject(position);
               holder.txtHeader.setText(obj.getString("name"));
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.length();
    }
}
