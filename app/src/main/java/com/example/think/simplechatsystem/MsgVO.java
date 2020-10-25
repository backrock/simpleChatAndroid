package com.example.think.simplechatsystem;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/11.
 */

public class MsgVO implements Serializable {

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getThumbImgUrl() {
        return thumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl) {
        this.thumbImgUrl = thumbImgUrl;
    }

    public String getOriginalImgUrl() {
        return originalImgUrl;
    }

    public void setOriginalImgUrl(String originalImgUrl) {
        this.originalImgUrl = originalImgUrl;
    }

    public long getMsgTimestamp() {
        return msgTimestamp;
    }

    public void setMsgTimestamp(long msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }

    public CustomerVO getSender() {
        return sender;
    }

    public void setSender(CustomerVO sender) {
        this.sender = sender;
    }

    public CustomerVO getReceiver() {
        return receiver;
    }

    public void setReceiver(CustomerVO receiver) {
        this.receiver = receiver;
    }
//    private int id;
//    private int msgId;
//    private int role;   //0 sender 1 receiver 2 system note

    private int status; //发送中，发送失败，发送成功

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String textContent;
//    private String imgurl;


    private String thumbImgUrl;
    private String originalImgUrl;
    private long msgTimestamp;
    private CustomerVO sender;
    private CustomerVO receiver;
}
