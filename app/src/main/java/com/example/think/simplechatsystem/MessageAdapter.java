package com.example.think.simplechatsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 2017/5/29.
 */

public class MessageAdapter extends BaseAdapter{

    private List<UIMsgVO> data;
    private LayoutInflater mInflater;
    private Context context;
    private MessageItemlayout.OnImgClickListener listener;

    public MessageAdapter(Context context, LinkedList<UIMsgVO> cdata,MessageItemlayout.OnImgClickListener listener){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener;
        data = cdata;
    }
    public void setData(LinkedList<UIMsgVO> cdata) {
        data = cdata;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_chat_msg, null);
            viewHolder.l_content =  convertView.findViewById(R.id.l_msg_content);
            viewHolder.l_content.setListener(listener);
                convertView.setTag(viewHolder);

        }else{
             viewHolder = (ViewHolder)convertView.getTag();
        }

         setView(position,viewHolder);

        return convertView;
    }

    private void setView(int position, ViewHolder viewHolder) {
        UIMsgVO item = data.get(position);
        long tspec = 0;
        viewHolder.l_content.removeAllViews();
        if (position>0){
            tspec = data.get(position-1).getMsgTimestamp();
        }
        viewHolder.l_content.addContentView(item,position,tspec);
    }

    class ViewHolder{
        MessageItemlayout l_content;
    }

}
