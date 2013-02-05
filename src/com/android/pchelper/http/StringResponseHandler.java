package com.android.pchelper.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;

public abstract class StringResponseHandler extends ResponseHandler
{	
	public StringResponseHandler(boolean asyncFlag)
	{
		super(asyncFlag);
	}
	
	@Override
	protected void onResponse(HttpResponse response)
	{
		StatusLine status = response.getStatusLine();
		int statusCode = status.getStatusCode();
        String responseBody = null;
        try {
            HttpEntity entity = null;
            HttpEntity temp = response.getEntity();
            if(temp != null)
            {
                entity = new BufferedHttpEntity(temp);
                responseBody = EntityUtils.toString(entity, "UTF-8");
            }
        } 
        catch(IOException e)
        {
            sendErrorMessage(AsyncHttpClient.HTTP_RESPONSE_IOERROR, e);
        }

        if(statusCode >= 300)
        {
            sendErrorMessage(statusCode, new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()));
        }
        else
        {
            sendSuccessMessage(responseBody);
        }
	}


}
