package com.example.udclient.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udclient.R;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {
    private List<MeetingDto> tableList;
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemListener(OnItemClickListener Listener){
        listener=Listener;
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView textView2;

        public TableViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textView =itemView.findViewById(R.id.tableName);
            textView2= itemView.findViewById(R.id.ownerName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public TableAdapter(List<MeetingDto> list){
        tableList= list;
    }


    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_item,parent,false);
        TableViewHolder tvh = new TableViewHolder(v,listener);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        MeetingDto currentTable = tableList.get(position);

        holder.textView.setText(currentTable.getName());
        holder.textView2.setText(currentTable.getCode());

    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }
}
