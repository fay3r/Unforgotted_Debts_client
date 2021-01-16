package com.example.udclient.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.udclient.R;

import java.util.List;

public class TableUserAdapter extends RecyclerView.Adapter<TableUserAdapter.TableViewHolder> {
    private List<PersonMeetingDto> personMeetingList;
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
            textView =itemView.findViewById(R.id.userNick);
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

    public TableUserAdapter(List<MeetingDto> list){
    }


    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        TableViewHolder tvh = new TableViewHolder(v,listener);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        PersonMeetingDto currentTable = personMeetingList.get(position);

        holder.textView.setText(currentTable.getNick());;

    }

    @Override
    public int getItemCount() {
        return personMeetingList.size();
    }
}
