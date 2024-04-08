package com.example.projectprm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectprm.Models.Message;
import com.example.projectprm.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;
    private String currentUserEmail;

    public MessageAdapter(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }

    // dùng để cập nhật dữ liệu danh sách messageList và thông báo cho Adapter cần phải cập nhật giao diện
    public void setData(List<Message> list){
        this.messageList = list;
        notifyDataSetChanged();
    }

    // quản lý các thành phần giao diện cho mỗi mục trong RecyclerView
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    // gắn dữ liệu từ danh sách messageList vào ViewHolder và hiển thị nó trong giao diện
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message message = messageList.get(position);
        if (holder == null || message == null || holder.tvMessage1 == null || holder.tvMessage2 == null) {
            return;
        }
        if(message != null){
            if (message.getSenderId().equals(currentUserEmail)) {
                holder.tvMessage1.setVisibility(View.VISIBLE);
                holder.tvMessage2.setVisibility(View.GONE);
                holder.tvMessage1.setText(message.getMessage());
            } else {
                holder.tvMessage1.setVisibility(View.GONE);
                holder.tvMessage2.setVisibility(View.VISIBLE);
                holder.tvMessage2.setText(message.getMessage());
            }
        }
        holder.tvMessage1.setText(message.getMessage());
        holder.tvMessage2.setText(message.getMessage());
    }

    // trả về số lượng mục trong danh sách hoặc 0 nếu danh sách rỗng
    @Override
    public int getItemCount() {
        if(messageList != null){
            return messageList.size();
        }
        return 0;
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder{

        private TextView tvMessage1;
        private TextView tvMessage2;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessage1 = itemView.findViewById(R.id.tv_message1);
            tvMessage2 = itemView.findViewById(R.id.tv_message2);
        }
    }
}
