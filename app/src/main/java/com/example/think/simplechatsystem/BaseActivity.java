package com.example.think.simplechatsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//import com.umeng.message.PushAgent;


public abstract class BaseActivity extends AppCompatActivity {
	private List<OnActivityResultListener> onActivityResultListeners;

	private ProgressDialog progressDialog2;
	private Dialog indeterminateDialog; // 单个旋转图片对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getSupportActionBar() != null)
			getSupportActionBar().hide();
		if(savedInstanceState != null){
			initParams(savedInstanceState);
		}else if(getIntent() != null && getIntent().getExtras() != null){
			initParams(getIntent().getExtras());
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findView(int id) {
		return (T) findViewById(id);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		onActivityResultListeners = null;
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		//super.onConfigurationChanged(newConfig);
	}
	protected void initParams(Bundle bundle){
		
	}
	protected abstract void initView();
	protected abstract void initData();


	private boolean canMakeSmores() {
		return(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP_MR1);
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftKeyboard()
	{
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();
		if (isOpen)
		{
			View view = getCurrentFocus();
			if (view != null)
				imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public void toast(String text){
		Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show();
	}

	public void toast(int strRes){
		Toast.makeText(this, getString(strRes), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		isResume = true;
		super.onResume();
	}
private boolean isResume;
	public boolean isResume(){ return isResume; }
	@Override
	protected void onPause() {
		isResume = false;
		super.onPause();
	}


	public BaseActivity self(){
		return this;
	}

	public void addActivityResultListener(OnActivityResultListener onActivityResultListener){
		if(onActivityResultListeners == null){
			onActivityResultListeners = new ArrayList<>();
		}
		onActivityResultListeners.add(onActivityResultListener);
	}
	
	public void removeActivityResultListener(OnActivityResultListener onActivityResultListener){
		if(onActivityResultListeners != null){
			onActivityResultListeners.remove(onActivityResultListener);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(onActivityResultListeners != null){
			for(OnActivityResultListener onActivityResultListener : onActivityResultListeners){
				onActivityResultListener.onActivityResult(requestCode, resultCode, data);
			}
		}
	}

	
	public interface OnActivityResultListener{
		void onActivityResult(int requestCode, int resultCode, Intent data);
	}

	/**
	 * 显示单个旋转图片对话框
	 */
	public void showIndeterminateDialog() {
		if (indeterminateDialog == null) {
			indeterminateDialog = new Dialog(this, R.style.new_circle_progress);
			indeterminateDialog.setCancelable(true);
			indeterminateDialog.setCanceledOnTouchOutside(false);
			View v = LayoutInflater.from(this).inflate(R.layout.view_indeterminate_progress, null);
			indeterminateDialog.setContentView(v);
		}
		indeterminateDialog.show();
	}

	public void dismissIndeterminateDialog() {
		if (indeterminateDialog != null && indeterminateDialog.isShowing()) {
			indeterminateDialog.dismiss();
		}
	}

	public boolean isShowIndeterminateDialog(){
		return indeterminateDialog.isShowing();
	}
}
