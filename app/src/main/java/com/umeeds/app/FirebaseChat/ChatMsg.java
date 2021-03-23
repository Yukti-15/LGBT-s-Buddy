package com.umeeds.app.FirebaseChat;

public class ChatMsg {

    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";

    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";


    private String msgContent;
    private String timestamp;

    private String msgType;

    public ChatMsg(String msgType, String msgContent, String timestamp) {
        this.msgType = msgType;
        this.msgContent = msgContent;
        this.timestamp = timestamp;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
