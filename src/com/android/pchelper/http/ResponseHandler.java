package com.android.pchelper.http;

import org.apache.http.HttpResponse;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class ResponseHandler
{
    protected static final int SUCCESS_MESSAGE = 0;
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int START_MESSAGE = 2;
    protected static final int FINISH_MESSAGE = 3;

    private boolean mAsyncFlag = false;
    private Handler mHandler = null;
    
    /**
     * async = true 使用handler发送消息
     * @param asyncFlag
     */
    public ResponseHandler(boolean asyncFlag)
    {
    	mAsyncFlag = asyncFlag;
    	if(mAsyncFlag && (Looper.myLooper() != null) )
    	{
    		mHandler = new Handler()
    		{
                public void handleMessage(Message msg)
                {
                	ResponseHandler.this.handleMessage(msg);
                }
            };
    	}
    }
    
    protected void sendMessage(int what, Object obj)
    {
    	if(mHandler != null && mAsyncFlag)
    	{
    		mHandler.sendMessage(mHandler.obtainMessage(what, obj));
    	}
    	else
    	{
    		Message msg = new Message();
    		msg.what = what;
    		msg.obj = obj;
    		handleMessage(msg);
    	}
    }
	
	protected void sendStartMessage()
	{
		sendMessage(START_MESSAGE, null);
	}
	
	protected void sendFinishMessage()
	{
		sendMessage(FINISH_MESSAGE, null);
	}
	
	protected void sendErrorMessage(int errorCode, Throwable e)
	{
		sendMessage(FAILURE_MESSAGE, new Object[]{errorCode, e});
	}
	
	protected void sendSuccessMessage(String result)
	{
		sendMessage(SUCCESS_MESSAGE, result);
	}
	
	protected void handleMessage(Message msg)
	{
		 switch(msg.what) 
		 {
         case FAILURE_MESSAGE:
        	 Object [] obj = (Object[]) msg.obj;
             onError((Integer)obj[0]);
             onError((Throwable) obj[1]);
             break;
         case START_MESSAGE:
             onStart();
             break;
         case FINISH_MESSAGE:
             onFinish();
             break;
         case SUCCESS_MESSAGE:
        	 onSuccess((String)msg.obj);
         default:
        	 break;
		 }
	}
	
	protected void onStart() {};
	protected void onFinish() {};
	protected void onSuccess(String result) {};
	protected void onError(Throwable e) {};
	protected void onError(int errorCode) {};
	
	abstract protected void onResponse(HttpResponse response);
}
