package com.example.tardinesstracker.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Tardiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TardinessListAdapter extends RecyclerView.Adapter<TardinessListAdapter.TardinessViewHolder> {

    private final Context context;
    private List<Tardiness> tardinessList = new ArrayList<>();
    private final OnTardinessClickListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public interface OnTardinessClickListener {
        void onTardinessClick(Tardiness tardiness);
    }

    public TardinessListAdapter(Context context, OnTardinessClickListener listener) {
        this.context = context;
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
        holder.bind(tardiness);
    }

    @Override
    public int getItemCount() {
        return tardinessList.size();
    }

    // Обновление данных с использованием DiffUtil
    public void updateData(List<Tardiness> newData) {
        if (newData == null) {
            return;
        }
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TardinessDiffCallback(tardinessList, newData));
        this.tardinessList.clear();
        this.tardinessList.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    // Добавление одного элемента
    public void addTardiness(Tardiness tardiness) {
        if (tardiness == null) {
            return;
        }
        this.tardinessList.add(tardiness);
        notifyItemInserted(tardinessList.size() - 1);
    }

    class TardinessViewHolder extends RecyclerView.ViewHolder {
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

            // Установка клика на элемент
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTardinessClick(tardinessList.get(getAdapterPosition()));
                }
            });
        }

        void bind(Tardiness tardiness) {
            // Информация о студенте
            studentNameTv.setText(tardiness.getStudentName());
            studentIdTv.setText(String.format("ID: %s", tardiness.getStudentId()));

            // Детали опоздания
            dateTv.setText(dateFormat.format(tardiness.getDateTime()));
            minutesLateTv.setText(String.format("%d min late", tardiness.getMinutesLate()));

            // Причина опоздания
            if (tardiness.getReason() != null && !tardiness.getReason().isEmpty()) {
                reasonTv.setVisibility(View.VISIBLE);
                reasonTv.setText(tardiness.getReason());
            } else {
                reasonTv.setVisibility(View.GONE);
            }

            // Цвет карточки в зависимости от степени опоздания
            int color = getSeverityColor(tardiness.getMinutesLate());
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, color));
        }

        private int getSeverityColor(int minutesLate) {
            if (minutesLate <= 5) {
                return R.color.slightlyLate;
            } else if (minutesLate <= 15) {
                return R.color.warning;
            } else {
                return R.color.veryLate;
            }
        }
    }

    // DiffUtil для оптимизации обновлений
    private static class TardinessDiffCallback extends DiffUtil.Callback {
        private final List<Tardiness> oldList;
        private final List<Tardiness> newList;

        public TardinessDiffCallback(List<Tardiness> oldList, List<Tardiness> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Tardiness oldTardiness = oldList.get(oldItemPosition);
            Tardiness newTardiness = newList.get(newItemPosition);
            return oldTardiness.getStudentId().equals(newTardiness.getStudentId())
                    && oldTardiness.getDateTime().equals(newTardiness.getDateTime())
                    && oldTardiness.getMinutesLate() == newTardiness.getMinutesLate()
                    && (oldTardiness.getReason() == null ? newTardiness.getReason() == null : oldTardiness.getReason().equals(newTardiness.getReason()));
        }
    }
}
