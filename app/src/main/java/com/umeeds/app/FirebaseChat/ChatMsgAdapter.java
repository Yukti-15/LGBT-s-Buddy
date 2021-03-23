package com.umeeds.app.FirebaseChat;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;

import java.util.ArrayList;
import java.util.List;


public class ChatMsgAdapter extends RecyclerView.Adapter<ChatAppMsgViewHolder> {

    TextView timeStamp;
    private List<ChatMsg> msgDtoList = null;

    public ChatMsgAdapter(List<ChatMsg> msgDtoList) {
        this.msgDtoList = msgDtoList;
    }

    @Override
    public void onBindViewHolder(ChatAppMsgViewHolder holder, int position) {
        ChatMsg msgDto = this.msgDtoList.get(position);
        Log.i("rahul...msg", msgDto.getMsgType());

        if (ChatMsg.MSG_TYPE_RECEIVED.equals(msgDto.getMsgType())) {
            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftMsgTextView.setText(msgDto.getMsgContent());
            holder.timeStamp2.setText(msgDto.getTimestamp());
            holder.rightMsgLayout.setVisibility(LinearLayout.GONE);

            Log.i("rahul", "if....");
        }
        // If the message is a sent message.
        else if (ChatMsg.MSG_TYPE_SENT.equals(msgDto.getMsgType())) {
            Log.i("rahul", "else....");

            // Show sent message in right linearlayout.
            holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.rightMsgTextView.setText(msgDto.getMsgContent());
            holder.timeStamp.setText(msgDto.getTimestamp());
            holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public ChatAppMsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_chat_item_view, parent, false);
        return new ChatAppMsgViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if (msgDtoList == null) {
            msgDtoList = new ArrayList<ChatMsg>();
        }
        return msgDtoList.size();
    }
}
