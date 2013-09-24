package com.geniusgithub.lookaround.test;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;

import com.geniusgithub.lookaround.R;
import com.geniusgithub.lookaround.util.CommonLog;
import com.geniusgithub.lookaround.util.LogFactory;
import com.geniusgithub.lookaround.weibo.sdk.ShareActivity;
import com.geniusgithub.lookaround.weibo.sdk.ShareItem;

public class TestWeiboActivity extends Activity implements OnClickListener{

	private static final CommonLog log = LogFactory.createLog();
	
	private static final String FILE_NAME = "/pic.png";
	public static String TEST_IMAGE;
	
	private Button mBtnSinal;
	private Button mBtnTencent;
	private Button mBtnQZone;
	private Button mBtnWChat;
	private Button mBtnWFrien;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_mainweibo_layout);
		
		initViews();
		initData();
	}


	@Override
	protected void onDestroy() {

		ShareSDK.stopSDK(this);
		
		super.onDestroy();
	}


	private void initViews(){
		mBtnSinal = (Button) findViewById(R.id.btn_sina);
		mBtnTencent = (Button) findViewById(R.id.btn_tencent);
		mBtnQZone = (Button) findViewById(R.id.btn_qzone);
		mBtnWChat = (Button) findViewById(R.id.btn_w_chat);
		mBtnWFrien = (Button) findViewById(R.id.btn_w_friend);
		
		mBtnSinal.setOnClickListener(this);
		mBtnTencent.setOnClickListener(this);
		mBtnQZone.setOnClickListener(this);
		mBtnWChat.setOnClickListener(this);
		mBtnWFrien.setOnClickListener(this);
	}
	
	private void initData(){
		ShareSDK.initSDK(this);
		
		initImagePath();
	}

	private void initImagePath() {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
					&& Environment.getExternalStorageDirectory().exists()) {
				TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_NAME;
			}
			else {
				TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + FILE_NAME;
			}
			File file = new File(TEST_IMAGE);
			if (!file.exists()) {
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
				case R.id.btn_sina:
					shareToSina();
					break;
				case R.id.btn_tencent:
					shareToTencent();
					break;
				case R.id.btn_qzone:
					shareToQZone();
					break;
				case R.id.btn_w_chat:
					shareToWChat();
					break;
				case R.id.btn_w_friend:
					shareToWFriend();
					break;
				}
	}
	
	
	
	private void shareToSina(){
	
		ShareItem.setText("test sina http://blog.csdn.net/lancees");		
		ShareItem.setImagePath(TestWeiboActivity.TEST_IMAGE);
		ShareItem.setPlatform(SinaWeibo.NAME);		
		goShareActivity();
	}
	
	private void shareToTencent(){
	
		ShareItem.setTitle("tencent title");
		ShareItem.setTitleUrl("http://blog.csdn.net/lancees");
		ShareItem.setText("tencent content https://github.com/geniusgithub");
		ShareItem.setImagePath(TestWeiboActivity.TEST_IMAGE);
		ShareItem.setPlatform(TencentWeibo.NAME);	
	
		goShareActivity();
	}
	
	private void shareToQZone(){
	
		ShareItem.setTitle("QZone Share");
		ShareItem.setTitleUrl("http://sharesdk.cn");
		ShareItem.setText("New QZXone Content");
		ShareItem.setImageUrl("http://img.appgo.cn/imgs/sharesdk/content/2013/07/25/1374723172663.jpg");
		ShareItem.setPlatform(QZone.NAME);
	
		goShareActivity();
	}
	
	private void shareToWChat(){
		
	}
	
	private void shareToWFriend(){
		
	}


	private void goShareActivity(){
		Intent intent = new Intent();
		intent.setClass(this, ShareActivity.class);
		startActivity(intent);
	}

	
}