package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{

    private List<ObjectData> mListData;

    public DataAdapter(List<ObjectData> mlistData) {
        this.mListData = mlistData;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) .inflate(R.layout.itemdata, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        ObjectData objectData = mListData.get(position);

        if(objectData == null){
            return ;
        }
        holder.tvTitle.setText(objectData.getTitle());
        holder.tvBody.setText(objectData.getBody());
    }

    @Override
    public int getItemCount() {
        if(mListData != null) {
            return mListData.size();
        }
        return 0;
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvBody;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBody = itemView.findViewById(R.id.tv_body);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
