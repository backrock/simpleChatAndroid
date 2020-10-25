package com.example.think.simplechatsystem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MessageActivity extends BaseActivity implements View.OnClickListener{
    final int MsgPageCount = 10;
    LinkedList<UIMsgVO> uiData;
    List<CustomerVO> customerVOs;
    ListView mlist;
    MessageAdapter adapter;
    EditText etContent;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        handler = new Handler();
        uiData = new LinkedList<>();
        setTitle("客服");
        findView(R.id.btn_img).setOnClickListener(this);
        findView(R.id.btn_send).setOnClickListener(this);
        etContent = findView(R.id.et_msg);
        mlist = findView(R.id.list_msg);
        adapter = new MessageAdapter(this,uiData,listener);
        mlist.setAdapter(adapter);
//        startMsgTickService();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
            finish();
                //showFinishDialog();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void uploadFile(String path){

        UIMsgVO uvo = new UIMsgVO();
        uvo.setMsgTimestamp(System.currentTimeMillis());
        uvo.setRole(0);
        uvo.setStatus(0);
        uvo.setImgurl(path);
        uiData.addLast(uvo);
        while (uiData.size()>MsgPageCount) uiData.removeFirst();
        adapter.setData(uiData);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData(){}
    @Override
    protected void initView(){}

Uri photoUri;
    private View.OnClickListener clickListener = new View.OnClickListener(){

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_pop_camera:
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //path为保存图片的路径，执行完拍照以后能保存到指定的路径下
                    //设置日期的转换格式
                    SimpleDateFormat timeStampFormat =new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    //设置文件名
                    String filename =timeStampFormat.format(new Date());
                    //使用ContentValues保存文件名
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, filename);
                    //使用内容提供者，定义照片保存的Uri
                    photoUri =getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(intent2, REQUEST_CODE_CAMERA);
                    break;
                case R.id.btn_pop_storage:
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");//选择图片
                    //intent.setType(“audio/*”); //选择音频
                    //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                    //intent.setType(“video/*;image/*”);//同时选择视频和图片
                    // intent.setType("*/*");//无类型限制
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, REQUEST_CODE_STORAGE);
                    break;
                default:
                    break;
            }
        }
    };
    final int REQUEST_CODE_STORAGE = 111;
    final int REQUEST_CODE_CAMERA = 112;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        String path;
        if (requestCode == REQUEST_CODE_STORAGE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null){
                path = new OpenStorage(this).uriToPath(uri);
                String sfp = ImgFileUtil.getFileType(path);
                if (path !=null &&  sfp!=null && !sfp.isEmpty()) uploadFile(path);
            }
        }else if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK){
            Uri uri;
            if (data != null &&data.getData() != null) {
                uri = data.getData();
            }else uri = photoUri;
            if (uri != null){
                path = new OpenStorage(this).uriToPath(uri);
                String sfp = ImgFileUtil.getFileType(path);
                if (path !=null &&  sfp!=null && !sfp.isEmpty()) uploadFile(path);
            }
        }
    }
    PopBottomMenu popMenu;
    @Override
    public void onClick(View v) {
        int vgid = v.getId();
        if (vgid==R.id.btn_img){
            if(popMenu==null)popMenu = new PopBottomMenu(this, clickListener);
            popMenu.show();
        }else if (vgid == R.id.btn_send){
          String txt =  etContent.getText().toString().trim();
            if (!txt.isEmpty()){
                UIMsgVO uvo = new UIMsgVO();
                uvo.setMsgTimestamp(System.currentTimeMillis());
                uvo.setContent(txt);
                uvo.setRole(0);
                uvo.setStatus(0);
                uiData.addLast(uvo);
                UIMsgVO uvo2 = new UIMsgVO();
                if (true){
//                    uvo2.setUserId(customerVOs.get(0).getUserId());
                    uvo2.setStatus(0);
                    uvo2.setContent("您好，请问有什么要为您服务的吗？");
                }else {
                    uvo2.setStatus(1);
                    uvo2.setContent("您好，系统繁忙，可能不能及时回复。");
                }
                uvo2.setMsgTimestamp(System.currentTimeMillis());
                uvo2.setRole(1);
                uiData.addLast(uvo2);
               while (uiData.size()>MsgPageCount) uiData.removeFirst();
                adapter.setData(uiData);
                adapter.notifyDataSetChanged();
                etContent.setText("");
            }
            else toast("empty");
        }
    }

    MessageItemlayout.OnImgClickListener listener=new MessageItemlayout.OnImgClickListener() {
        @Override
        public void onClick(View v, UIMsgVO value, int position) {
            String url = value.getImgOriginUrl();
            if (url!=null && !url.isEmpty()){
                if (url.contains("http")) WebviewActivity.startActivity(MessageActivity.this,url);
                else WebviewActivity.startActivity(MessageActivity.this,"file://"+url);
            }//if
            else{
                url = value.getImgurl();
                if (url!=null && !url.isEmpty())WebviewActivity.startActivity(MessageActivity.this,"file://"+url);
            }
        }// onclick
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
