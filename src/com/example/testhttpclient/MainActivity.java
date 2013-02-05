package com.example.testhttpclient;

import com.android.pchelper.http.AsyncHttpClient;
import com.android.pchelper.http.FileResponseHandler;
import com.android.pchelper.http.RequestParams;
import com.android.pchelper.http.StringResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		testAsyncHttpClient();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	
	void testAsyncHttpClient()
	{
		AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://www.google.com", null, null, new StringResponseHandler(true) {

			@Override
			protected void onStart()
			{
				System.out.println("start");
				super.onStart();
			}

			@Override
			protected void onFinish()
			{
				 System.out.println("finish");
				super.onFinish();
			}

			@Override
			protected void onError(int errorCode)
			{
				 System.out.println("error = " + errorCode);
				super.onError(errorCode);
			}

			@Override
			public void onSuccess(String result)
			{
				// TODO Auto-generated method stub
				 System.out.println(result);
			}

        });
        
        client.get("http://www.baidu.com", null, null, new StringResponseHandler(true) {


			@Override
			public void onSuccess(String result)
			{
				// TODO Auto-generated method stub
				 System.out.println(result);
			}

        });
        
        
        client.get("http://itstarting.iteye.com/blog/1231195", null, null, new StringResponseHandler(true) {

			@Override
			public void onSuccess(String result)
			{
				// TODO Auto-generated method stub
				 System.out.println(result);
			}

        });
        
        
        
        AsyncHttpClient AsyncClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        //params.put("range", "byte=10000");
        params.put("HTTP_RANGE", String.valueOf(20000));
        String url = "http://sj.zol.com.cn/down.php?softid=19899&subcateid=74&site=11&server=111&w=0&m=0";
        AsyncClient.post(url, null, params, new FileResponseHandler(true, "/mnt/sdcard/test.apk", 0){

			@Override
			public void onSuccess(String fileName)
			{

				System.out.println(fileName);
			}

			@Override
			public void onProgress(int progress)
			{

			}
        	
        });
       
	}

}
