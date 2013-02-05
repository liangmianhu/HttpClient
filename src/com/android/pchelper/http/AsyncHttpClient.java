/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.android.pchelper.http;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

public class AsyncHttpClient extends SyncHttpClient
{
    private ThreadPoolExecutor threadPool;
    
    
    /**
     * Creates a new AsyncHttpClient.
     */
    public AsyncHttpClient() 
    {
        threadPool = (ThreadPoolExecutor)Executors.newCachedThreadPool();
    }

    /**
     * 
     * @param client
     * @param httpContext
     * @param uriRequest
     * @param responseHandler
     */
    protected void sendRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, ResponseHandler responseHandler) 
    {
       threadPool.submit(new AsyncHttpRequest(client, httpContext, uriRequest, responseHandler));
    }
}
