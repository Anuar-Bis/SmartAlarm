package com.example.tardinesstracker.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatbotFragment extends Fragment {

    private RecyclerView recyclerView;
    private EditText etMessageInput;
    private ImageButton btnSend;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messages = new ArrayList<>();
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        // Инициализация UI
        recyclerView = view.findViewById(R.id.rv_chat_messages);
        etMessageInput = view.findViewById(R.id.et_message_input);
        btnSend = view.findViewById(R.id.btn_send);

        // Настройка RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatAdapter = new ChatAdapter(messages);
        recyclerView.setAdapter(chatAdapter);

        // Отправка сообщения
        btnSend.setOnClickListener(v -> sendMessage());

        // Пример начального сообщения от бота
        addBotMessage("Hello! How can I assist you today?");

        return view;
    }

    private void sendMessage() {
        String inputText = etMessageInput.getText().toString().trim();
        if (!inputText.isEmpty()) {
            // Сообщение от пользователя
            ChatMessage userMessage = new ChatMessage(null, null, inputText, new Date(), false);
            chatAdapter.addMessage(userMessage);
            etMessageInput.setText("");

            // Симуляция ответа бота (в реальном приложении это будет API-запрос)
            simulateBotResponse(inputText);
            recyclerView.smoothScrollToPosition(messages.size() - 1);
        }
    }

    private void simulateBotResponse(String userInput) {
        String botResponse = "You said: " + userInput; // Замени на реальную логику бота
        addBotMessage(botResponse);
    }

    private void addBotMessage(String text) {
        ChatMessage botMessage = new ChatMessage(null, null, text, new Date(), true);
        chatAdapter.addMessage(botMessage);
    }

    // Внутренний адаптер для чата
    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
        private final List<ChatMessage> messages;

        public ChatAdapter(List<ChatMessage> messages) {
            this.messages = messages;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatbot_message, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ChatMessage message = messages.get(position);
            holder.tvMessageText.setText(message.getText());
            holder.tvMessageTime.setText(timeFormat.format(message.getTimestamp()));
            with(holder.cardMessage) {
                if (message.isFromBot()) {
                    setCardBackgroundColor(Color.parseColor("#E0E0E0")); // Серый для бота
                    holder.tvMessageText.setTextColor(Color.parseColor("#212121"));
                    layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                        endToEnd = ConstraintLayout.LayoutParams.UNSET;
                    }
                } else {
                    setCardBackgroundColor(Color.parseColor("#3F51B5")); // Синий для пользователя
                    holder.tvMessageText.setTextColor(Color.parseColor("#FFFFFF"));
                    layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                        startToStart = ConstraintLayout.LayoutParams.UNSET;
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        public void addMessage(ChatMessage message) {
            messages.add(message);
            notifyItemInserted(messages.size() - 1);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvMessageText;
            TextView tvMessageTime;
            com.google.android.material.card.MaterialCardView cardMessage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMessageText = itemView.findViewById(R.id.tv_message_text);
                tvMessageTime = itemView.findViewById(R.id.tv_message_time);
                cardMessage = itemView.findViewById(R.id.card_message);
            }
        }
    }
}

// Модель данных для сообщений (дополни, если нужно)
data class ChatMessage(
    val id: String?,
    val senderId: String?,
    val text: String,
    val timestamp: Date,
    val isFromBot: Boolean
)
    
