package com.kaustubh.medrug;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {
    private List<DocItem> exampleList;
    private docclick docclick;




    class DoctorViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        DoctorViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.docImage);
            textView1 = itemView.findViewById(R.id.tvw1);
            textView2 = itemView.findViewById(R.id.tvw2);
            textView3 = itemView.findViewById(R.id.tvw3);
            textView4 = itemView.findViewById(R.id.tvw4);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println("Button Pressed "+String.valueOf(getAdapterPosition()));
            docclick.ondocclick(getAdapterPosition());

        }
    }

    DoctorAdapter(List<DocItem> exampleList, docs docs) {
        this.exampleList = exampleList;
        docclick=docs;


    }

    @NonNull
    @Override
    public DoctorAdapter.DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dynamic_doctor_list,
                parent, false);
        return new DoctorAdapter.DoctorViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DoctorViewHolder holder, int position) {
        DocItem currentItem = exampleList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());
        holder.textView3.setText(currentItem.getText3());
        holder.textView4.setText(currentItem.getText4());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    public interface docclick
    {
        void ondocclick( int position);
    }


}
