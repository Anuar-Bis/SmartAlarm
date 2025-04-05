package com.example.tardinesstracker.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Tardiness;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TardinessListAdapter extends RecyclerView.Adapter<TardinessListAdapter.TardinessViewHolder> {

    private final Context context;
    private List<Tardiness> tardinessList;
    private final OnTardinessClickListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public interface OnTardinessClickListener {
        void onTardinessClick(Tardiness tardiness);
    }

    public TardinessListAdapter(Context context, List<Tardiness> tardinessList, OnTardinessClickListener listener) {
        this.context = context;
        this.tardinessList = tardinessList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TardinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tardiness, parent, false);
        return new TardinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TardinessViewHolder holder, int position) {
        Tardiness tardiness = tardinessList.get(position);
        
        // Set student information
        holder.studentNameTv.setText(tardiness.getStudentName());
        holder.studentIdTv.setText(String.format("ID: %s", tardiness.getStudentId()));
        
        // Set tardiness details
        holder.dateTv.setText(dateFormat.format(tardiness.getDateTime()));
        holder.minutesLateTv.setText(String.format("%d min late", tardiness.getMinutesLate()));
        
        // Set reason if available
        if (tardiness.getReason() != null && !tardiness.getReason().isEmpty()) {
            holder.reasonTv.setVisibility(View.VISIBLE);
            holder.reasonTv.setText(tardiness.getReason());
        } else {
            holder.reasonTv.setVisibility(View.GONE);
        }
        
        // Set color based on tardiness severity
        int color;
        if (tardiness.getMinutesLate() <= 5) {
            color = R.color.slightlyLate;
        } else if (tardiness.getMinutesLate() <= 15) {
            color = R.color.warning;
        } else {
            color = R.color.veryLate;
        }
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, color));
        
        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTardinessClick(tardiness);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tardinessList == null ? 0 : tardinessList.size();
    }

    public void updateData(List<Tardiness> newData) {
        this.tardinessList = newData;
        notifyDataSetChanged();
    }

    static class TardinessViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView studentNameTv;
        TextView studentIdTv;
        TextView dateTv;
        TextView minutesLateTv;
        TextView reasonTv;

        public TardinessViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.tardiness_card);
            studentNameTv = itemView.findViewById(R.id.student_name);
            studentIdTv = itemView.findViewById(R.id.student_id);
            dateTv = itemView.findViewById(R.id.tardiness_date);
            minutesLateTv = itemView.findViewById(R.id.minutes_late);
            reasonTv = itemView.findViewById(R.id.tardiness_reason);
        }
    }
}
