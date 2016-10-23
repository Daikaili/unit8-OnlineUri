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
					//ʹ��TextView��ʾ���
					
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
					//���ӳ�ʱ
					conn.setRequestMethod("GET");
					//��get��ʽ��������GETһ��Ҫ��д
					if(conn.getResponseCode()!=200)
						throw new RuntimeException("����URLʧ��");
					InputStream iStream=conn.getInputStream();
					//�õ����緵�ص�������
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
			//��ȡ������Դ
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();	
		byte[] buffer=new byte[1024];
		int len=-1;
		while((len=inSream.read(buffer))!=-1){
			outStream.write(buffer,0,len);
		}
		byte[] data=outStream.toByteArray();
		//���ֽ������תΪ�ֽ�����
		outStream.close();
		//�ر��ֽ������
		inSream.close();
		//�ر��ֽ�������
		return new String(data,charsetName);
		//���ػ�ȡ�����ݣ���ҳԴ����
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
					//��ȡ�ٶ���ҳ��Ƭ
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
