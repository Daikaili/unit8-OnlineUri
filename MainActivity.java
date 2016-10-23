package com.example.onlineuri;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.os.Message;

import android.os.Handler;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
   /*private ImageView imgBaidu;
   private Handler myHandler;
   private Bitmap bitmap;*/
	private TextView tvResult;
	private WebView show;
    private Handler myHandler;
	 String result;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvResult=(TextView) this.findViewById(R.id.tvResult);
		show=(WebView) this.findViewById(R.id.show);
		myHandler=new Handler(){
			public void handleMessage(Message msg){
				if(msg.what==0x1122){
					//result_show.setText(result);
					//使用TextView显示结果
					
					show.loadDataWithBaseURL(null, result, "text/html", "utf-8"
							, null);
				}
			}
			
		};
		
		new Thread(){
			public void run(){
				try{
					URL httpUrl=new URL("http://www.baidu.com/"+"img/baidu_syloo1.gif");
					HttpURLConnection conn=(HttpURLConnection)httpUrl.openConnection();
					conn.setConnectTimeout(5*300);
					//连接超时
					conn.setRequestMethod("GET");
					//以get方式发送请求，GET一定要大写
					if(conn.getResponseCode()!=200)
						throw new RuntimeException("请求URL失败");
					InputStream iStream=conn.getInputStream();
					//得到网络返回的输入流
					result=readData(iStream,"utf-8");
					conn.disconnect();
					myHandler.sendEmptyMessage(0x1122);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}

			
		}.start();
	}
		

		protected String readData(InputStream inSream, String charsetName)
		throws Exception{  
		// TODO Auto-generated method stub
			//获取网络资源
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();	
		byte[] buffer=new byte[1024];
		int len=-1;
		while((len=inSream.read(buffer))!=-1){
			outStream.write(buffer,0,len);
		}
		byte[] data=outStream.toByteArray();
		//将字节输出流转为字节数组
		outStream.close();
		//关闭字节输出流
		inSream.close();
		//关闭字节输入流
		return new String(data,charsetName);
		//返回获取的内容，网页源代码
	}
		/*
		imgBaidu=(ImageView) this.findViewById(R.id.imgBaidu);
		myHandler=new Handler(){
			public void handleMessage(Message msg){
				if(msg.what==0x1122){
					imgBaidu.setImageBitmap(bitmap);
				}
			}
		};
		new Thread(){
			public void run(){
				try{
					URL url=new URL("http://www.baidu.com/"+"img/baidu_sylogo1.gif");
					//获取百度首页照片
					InputStream is=url.openStream();
					bitmap=BitmapFactory.decodeStream(is);
					is.close();
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
				myHandler.sendEmptyMessage(0x1122);
			}
		}.start();
		
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {


		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
