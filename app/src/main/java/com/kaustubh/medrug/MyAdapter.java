package com.kaustubh.medrug;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> mDataset;
    private click docclick;



    public class MyViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        // each data item is just a string in this case
        TextView textView;
        ImageView lol;
        MyViewHolder(View v) {
            super(v);
            textView=v.findViewById(R.id.gentext);
            lol=v.findViewById(R.id.heil);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            docclick.act(getAdapterPosition());
//            System.out.println(mDataset.get(getAdapterPosition()));


        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String> myDataset, date docs) {
        mDataset = myDataset;
        docclick= docs;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic, parent, false);
        return new MyAdapter.MyViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String currentItem = mDataset.get(position);
        holder.textView.setText(currentItem);




    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

interface click{
    void act(int pos);

}

