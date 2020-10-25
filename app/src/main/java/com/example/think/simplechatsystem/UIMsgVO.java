package com.example.think.simplechatsystem;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/11.
 */

public class UIMsgVO implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    private long msgTimestamp;
    private String userId;

    public long getMsgTimestamp() {
        return msgTimestamp;
    }

    public void setMsgTimestamp(long msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private int id;
    private int role;   //发送方，接收方
    private String content;
    private int status; //0发送中，1发送失败，2发送成功  (接收方，0在线，1离线)

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    private boolean read;   //已读，未读

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    private String imgurl;
    private String imgOriginUrl;

    public String getImgOriginUrl() {
        return imgOriginUrl;
    }

    public void setImgOriginUrl(String imgOriginUrl) {
        this.imgOriginUrl = imgOriginUrl;
    }
}
