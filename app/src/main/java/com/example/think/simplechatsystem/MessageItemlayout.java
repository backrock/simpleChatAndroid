package com.example.think.simplechatsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/15.
 */

public class MessageItemlayout extends LinearLayout {
    private Context context;
    LayoutInflater mInflater;
    View child;
    int positon = -1;
    UIMsgVO mVo;
    private OnImgClickListener listener;

    TextView tv_name, tv_content, tv_time;
    ImageView iv_ltouxiang,iv_img,iv_rgif;

    public MessageItemlayout(Context context){
        super(context);
        this.context = context;
        mInflater=LayoutInflater.from(getContext());
        setPadding(0,0,0,0);
        setOrientation(VERTICAL);
    }
    public MessageItemlayout(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public MessageItemlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
    }

//    public MessageItemlayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context,attrs,defStyleAttr,defStyleRes);
//    }
    public void setListener(OnImgClickListener l){listener = l;}
    public void addContentView(UIMsgVO vo,int p,long lastTime){
        mVo = vo;
        positon = p;
//            if(child==null) {
                if (mInflater == null) mInflater=LayoutInflater.from(getContext());
                if (vo.getRole()==1){//left
                child = mInflater.inflate(R.layout.chat_left_item, null);
                tv_name = (TextView) child.findViewById(R.id.tv_name);
                tv_content = (TextView) child.findViewById(R.id.tv_content);
                tv_time = (TextView) child.findViewById(R.id.tv_time);
                iv_img = child.findViewById(R.id.iv_img);
                iv_ltouxiang = child.findViewById(R.id.iv_touxiang);
                iv_img.setOnClickListener(new ClickListener(p));
            }else{
                    child = mInflater.inflate(R.layout.chat_right_item, null);
                    tv_name = (TextView) child.findViewById(R.id.tv_name);
                    tv_content = (TextView) child.findViewById(R.id.tv_content);
                    tv_time = (TextView) child.findViewById(R.id.tv_time);
                    iv_img = child.findViewById(R.id.iv_img);
                    iv_ltouxiang = child.findViewById(R.id.iv_touxiang);
                    iv_rgif = child.findViewById(R.id.gif_giv);
                    iv_img.setOnClickListener(new ClickListener(p));
                }
                addView(child);
//        }
        if (vo.getRole()==1) {
            if (vo.getStatus() == 0) iv_ltouxiang.setImageResource(R.mipmap.chat_server);
            else iv_ltouxiang.setImageResource(R.mipmap.chat_server_gray);

            if (vo.getImgurl() == null || vo.getImgurl().isEmpty()) {
                iv_img.setVisibility(View.GONE);
                tv_content.setVisibility(View.VISIBLE);
            } else {
                iv_img.setVisibility(View.VISIBLE);
                tv_content.setVisibility(View.GONE);
                Bitmap bmp = JsonFileUtils.getURLimage(vo.getImgurl());
                if (bmp != null) iv_img.setImageBitmap(bmp);
                else iv_img.setImageResource(R.mipmap.sd_imageloaderror);
            }
        }else{
            if (vo.getImgurl()==null || vo.getImgurl().isEmpty()){
                iv_img.setVisibility(View.GONE);
                tv_content.setVisibility(View.VISIBLE);
            }
            else{
                iv_img.setVisibility(View.VISIBLE);
                tv_content.setVisibility(View.GONE);
                iv_img.setImageBitmap(ImgFileUtil.getImageThumbnail(vo.getImgurl(),100,100));
            }
            if (iv_rgif!=null){
                if (vo.getStatus() == 0){
                    iv_rgif.setVisibility(View.VISIBLE);
                    iv_rgif.setImageResource(R.drawable.jiazai1);
                }
                else if (vo.getStatus() == 1){
                    iv_rgif.setVisibility(View.VISIBLE);
                    iv_rgif.setImageResource(R.mipmap.chat_error);
                }
                else iv_rgif.setVisibility(View.GONE);
            }
        }
        if (vo.getContent()!=null)tv_content.setText(vo.getContent());
        else tv_content.setText("");
        tv_time.setText(DateUtil.timespecToMsgDate(vo.getMsgTimestamp()));
        if (p>0){
            if (vo.getMsgTimestamp() - lastTime < 2*60*1000) // 小于两分钟
                tv_time.setVisibility(View.GONE);
            else tv_time.setVisibility(View.VISIBLE);
        }
    }
    public void removeAllViews() {
        child = null;
        tv_name = null;
        tv_content = null;
        tv_time = null;
        iv_img = null;
        iv_ltouxiang = null;
        iv_rgif = null;
        super.removeAllViews();
    }

    private class ClickListener implements OnClickListener {

        private int position;

        public ClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,mVo,position);
        }
    }
    public interface OnImgClickListener {
        void onClick(View v, UIMsgVO value, int position);
    }
}
