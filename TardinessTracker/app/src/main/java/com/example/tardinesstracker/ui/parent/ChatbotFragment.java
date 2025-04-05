package com.example.tardinesstracker.ui.parent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tardinesstracker.R;
import com.example.tardinesstracker.data.entities.Tardiness;
import com.example.tardinesstracker.ui.adapters.ChatMessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatbotFragment extends Fragment {

    private ChatbotViewModel viewModel;
    private RecyclerView rvChatMessages;
    private EditText etReply;
    private Button btnSend;
    private ChatMessageAdapter adapter;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chatbot, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Initialize views
        rvChatMessages = view.findViewById(R.id.rv_chat_messages);
        etReply = view.findViewById(R.id.et_reply);
        btnSend = view.findViewById(R.id.btn_send);
        
        // Setup RecyclerView
        adapter = new ChatMessageAdapter();
        rvChatMessages.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvChatMessages.setAdapter(adapter);
        
        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ChatbotViewModel.class);
        
        // Observe tardiness updates
        viewModel.getTardinessLiveData().observe(getViewLifecycleOwner(), new Observer<List<Tardiness>>() {
            @Override
            public void onChanged(List<Tardiness> tardinessList) {
                if (tardinessList != null && !tardinessList.isEmpty()) {
                    // Update chat with new tardiness data
                    adapter.updateMessages(viewModel.convertTardinessToMessages(tardinessList));
                    rvChatMessages.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        });
        
        // Setup send button
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reply = etReply.getText().toString().trim();
                if (!reply.isEmpty()) {
                    viewModel.sendReply(reply);
                    etReply.setText("");
                }
            }
        });
    }
}
