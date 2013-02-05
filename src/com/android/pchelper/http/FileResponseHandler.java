package com.android.pchelper.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

public abstract class FileResponseHandler extends ResponseHandler
{
	private String mFileName;
	private int    mOffset = 0;
	
	public FileResponseHandler(boolean asyncFlag, String fileName, int offset)
	{
		super(asyncFlag);
		
		mFileName = fileName;
		mOffset = offset > 0 ? offset : 0;
	}

	public abstract void onProgress(int progress);
	
	@Override
	protected void onResponse(HttpResponse response)
	{
		try
		{
			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();
			boolean ret = false;
			if(statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_PARTIAL_CONTENT)
			{
				ret = writeFile(response);
				if(ret)
				{
					sendSuccessMessage(mFileName);
				}
				else
				{
					sendErrorMessage(AsyncHttpClient.HTTP_RESPONSE_UNKNOWN, null);
				}
			}
			else
			{
				sendErrorMessage(statusCode, null);
			}
		}
		catch (Exception e)
		{
			sendErrorMessage(AsyncHttpClient.HTTP_RESPONSE_UNKNOWN, e);
		}
		
	}

	boolean writeFile(HttpResponse response)
	{
		RandomAccessFile outfile = null;
		InputStream in = null;
		
		try
		{
			outfile = new RandomAccessFile(mFileName, "rws");
			outfile.seek(mOffset);
			
			HttpEntity entity = response.getEntity();
			long contentLength = entity.getContentLength();
			in = entity.getContent();
			
			long write = 0;
			int read = 0;
			byte[] buf = new byte[2048];
			while( (read = in.read(buf, 0, 2048)) != -1 )
			{
				outfile.write(buf, 0, read);
				write += read;
			}
			
			return (write == contentLength);
		}
		catch (IllegalStateException e)
		{
		} 
		catch (IOException e)
		{
			super.sendErrorMessage(AsyncHttpClient.HTTP_RESPONSE_IOERROR, e);
		}
		finally
		{
			try
			{
				if(in != null) in.close();
				if(outfile != null) outfile.close();
			} 
			catch (IOException e) 
			{
			}
		}
		
		return false;
		
	}

}
