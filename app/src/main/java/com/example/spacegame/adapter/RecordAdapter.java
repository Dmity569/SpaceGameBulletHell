package com.example.spacegame.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.spacegame.R;
import com.example.spacegame.domain.Record;

import java.util.List;

public class RecordAdapter  extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<Record> records;

    public RecordAdapter(Context context, List<Record> records) {
        this.records = records;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordAdapter.ViewHolder holder, int position) {
        Record record = records.get(position);
        holder.nameView.setText(record.getName());
        holder.scoreView.setText(String.valueOf(record.getScore()));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, scoreView;
        ViewHolder(View view){
            super(view);
            scoreView = view.findViewById(R.id.score);
            nameView = view.findViewById(R.id.name);
        }
    }
}
