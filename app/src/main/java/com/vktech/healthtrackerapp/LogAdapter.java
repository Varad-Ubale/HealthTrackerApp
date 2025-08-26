package com.vktech.healthtrackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Locale;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    private final Context context;
    private final List<LogEntry> logList;
    private final OnLogActionListener listener;

    public LogAdapter(Context context, List<LogEntry> logList, OnLogActionListener listener) {
        this.context = context;
        this.logList = logList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_log_entry, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        LogEntry entry = logList.get(position);

        holder.tvDate.setText(entry.getDate());

        String metricsText = String.format(Locale.getDefault(),
                "Steps: %d | Water: %d ml | Sleep: %.1f h",
                entry.getSteps(), entry.getWater(), entry.getSleep());

        holder.tvMetrics.setText(metricsText);
        holder.tvNotes.setText(entry.getNotes());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(entry);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(entry);
            }
        });
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvMetrics, tvNotes;
        Button btnEdit, btnDelete;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvMetrics = itemView.findViewById(R.id.tvMetrics);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public interface OnLogActionListener {
        void onEdit(LogEntry entry);
        void onDelete(LogEntry entry);
    }
}