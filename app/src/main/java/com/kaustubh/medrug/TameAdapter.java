package com.kaustubh.medrug;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


class TameAdapter extends RecyclerView.Adapter<TameAdapter.MyViewHolder> {
    private List<String> mDataset;


    private int checked_pos,prev=-1;




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
//            docclick.act(getAdapterPosition());
            System.out.println(mDataset.get(getAdapterPosition()));
            if(checked_pos==-1) {
                checked_pos = getAdapterPosition();
                notifyItemChanged(checked_pos);
                Bookapt.details[2]=(mDataset.get(getAdapterPosition()));
            }
            else
            {
                prev=checked_pos;
                checked_pos=getAdapterPosition();
                notifyItemChanged(checked_pos);
                notifyItemChanged(prev);
                Bookapt.details[2]=(mDataset.get(getAdapterPosition()));
            }
        }
    }
    public TameAdapter(ArrayList<String> myDataset, Tame docs) {
        mDataset = myDataset;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.generic, parent, false);
        return new TameAdapter.MyViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(position==prev)
        {

            holder.lol.setBackgroundResource(R.drawable.button);
        }
        if(position==checked_pos)
        {
            holder.lol.setBackgroundResource(R.drawable.avlbg);
        }
        String currentItem = mDataset.get(position);
        holder.textView.setText(currentItem);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}