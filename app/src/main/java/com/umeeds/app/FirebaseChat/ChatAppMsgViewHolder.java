package com.umeeds.app.FirebaseChat;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.umeeds.app.R;


public class ChatAppMsgViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout leftMsgLayout;

    RelativeLayout rightMsgLayout;

    TextView leftMsgTextView;
    TextView timeStamp, timeStamp2;
    TextView rightMsgTextView;

    public ChatAppMsgViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            leftMsgLayout = (RelativeLayout) itemView.findViewById(R.id.chat_left_msg_layout);
            rightMsgLayout = (RelativeLayout) itemView.findViewById(R.id.chat_right_msg_layout);
            leftMsgTextView = (TextView) itemView.findViewById(R.id.text_message_body);
            rightMsgTextView = (TextView) itemView.findViewById(R.id.text_message_body_sent);
            timeStamp = (TextView) itemView.findViewById(R.id.text_message_time_sent);
            timeStamp2 = (TextView) itemView.findViewById(R.id.text_message_reciever_time);


        }
    }
}
