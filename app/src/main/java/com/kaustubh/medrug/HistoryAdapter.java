package com.kaustubh.medrug;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<historyItem> exampleList;




    static class HistoryViewHolder extends  RecyclerView.ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        Button btn;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.hist_doc);
            textView2=itemView.findViewById(R.id.hist_dat);
            textView3=itemView.findViewById(R.id.hist_time);
            textView4=itemView.findViewById(R.id.hist_stat);
            btn=itemView.findViewById(R.id.cancel);
        }
    }

    public HistoryAdapter(List<historyItem> examplelist){
        this.exampleList=examplelist;
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hist_card,parent, false);
        return new HistoryAdapter.HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        historyItem current=exampleList.get(position);
        holder.textView1.setText(current.getDoctor());
        holder.textView2.setText(current.getDate());
        holder.textView3.setText(current.getTime());
        holder.textView4.setText(current.getStatus());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(holder.btn.getContext(), R.style.MyDialogTheme)
                .setTitle("Confirmation")
                .setMessage("Are you sure?")


                // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                exampleList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();




            }
        });

    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }


}
