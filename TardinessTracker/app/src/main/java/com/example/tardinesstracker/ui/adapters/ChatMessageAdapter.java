package com.example.tardinesstracker.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {

    private static final int VIEW_TYPE_INCOMING = 1;
    private static final int VIEW_TYPE_OUTGOING = 2;
    private static final int VIEW_TYPE_SYSTEM = 3;

    private final Context context;
    private List<ChatMessage> messages;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final String currentUserId;

    public ChatMessageAdapter(Context context, List<ChatMessage> messages, String currentUserId) {
        this.context = context;
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        
        if (viewType == VIEW_TYPE_INCOMING) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);
            return new IncomingMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_OUTGOING) {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);
            return new OutgoingMessageViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat_message, parent, false);
            return new SystemMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        
        if (holder instanceof IncomingMessageViewHolder) {
            bindIncomingMessage((IncomingMessageViewHolder) holder, message);
        } else if (holder instanceof OutgoingMessageViewHolder) {
            bindOutgoingMessage((OutgoingMessageViewHolder) holder, message);
        } else if (holder instanceof SystemMessageViewHolder) {
            bindSystemMessage((SystemMessageViewHolder) holder, message);
        }
    }

    private void bindIncomingMessage(IncomingMessageViewHolder holder, ChatMessage message) {
        holder.messageTv.setText(message.getContent());
        holder.timeTv.setText(timeFormat.format(message.getTimestamp()));
        holder.senderTv.setText(message.getSenderName());
    }

    private void bindOutgoingMessage(OutgoingMessageViewHolder holder, ChatMessage message) {
        holder.messageTv.setText(message.getContent());
        holder.timeTv.setText(timeFormat.format(message.getTimestamp()));
        // No sender name for outgoing messages
    }

    private void bindSystemMessage(SystemMessageViewHolder holder, ChatMessage message) {
        holder.messageTv.setText(message.getContent());
        holder.timeTv.setText(timeFormat.format(message.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        
        if (message.getSenderId() == null) {
            return VIEW_TYPE_SYSTEM;
        } else if (message.getSenderId().equals(currentUserId)) {
            return VIEW_TYPE_OUTGOING;
        } else {
            return VIEW_TYPE_INCOMING;
        }
    }

    public void updateData(List<ChatMessage> newData) {
        this.messages = newData;
        notifyDataSetChanged();
    }

    public void addMessage(ChatMessage message) {
        this.messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    // Abstract ViewHolder class
    abstract static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTv;
        TextView timeTv;
        
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTv = itemView.findViewById(R.id.message_content);
            timeTv = itemView.findViewById(R.id.message_time);
        }
    }

    // Specific ViewHolder classes
    static class IncomingMessageViewHolder extends MessageViewHolder {
        TextView senderTv;
        ConstraintLayout messageContainer;
        
        public IncomingMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderTv = itemView.findViewById(R.id.message_sender);
            messageContainer = itemView.findViewById(R.id.message_container);
            
            // Set background color for incoming messages
            messageContainer.setBackgroundResource(R.drawable.bg_incoming_message);
        }
    }

    static class OutgoingMessageViewHolder extends MessageViewHolder {
        ConstraintLayout messageContainer;
        
        public OutgoingMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContainer = itemView.findViewById(R.id.message_container);
            
            // Set background color and alignment for outgoing messages
            messageContainer.setBackgroundResource(R.drawable.bg_outgoing_message);
            
            // Hide the sender name for outgoing messages
            itemView.findViewById(R.id.message_sender).setVisibility(View.GONE);
            
            // Align to the right
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) messageContainer.getLayoutParams();
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.startToStart = ConstraintLayout.LayoutParams.UNSET;
            messageContainer.setLayoutParams(params);
        }
    }

    static class SystemMessageViewHolder extends MessageViewHolder {
        ConstraintLayout messageContainer;
        
        public SystemMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContainer = itemView.findViewById(R.id.message_container);
            
            // Set background and style for system messages
            messageContainer.setBackgroundResource(R.drawable.bg_system_message);
            
            // Hide the sender name for system messages
            itemView.findViewById(R.id.message_sender).setVisibility(View.GONE);
            
            // Center the message
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) messageContainer.getLayoutParams();
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            params.horizontalBias = 0.5f;
            messageContainer.setLayoutParams(params);
        }
    }
}
