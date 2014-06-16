package com.android.storemanage.net;

import java.io.ByteArrayOutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import com.android.storemanage.utils.LogUtil;


public class SyncHttpClient extends AsyncHttpClient {

	public SyncHttpClient() {
		super();
	}

	public HttpResponse get(String url) throws Exception {
		return get(url, (RequestParams) null);
	}

	public HttpResponse get(String url, RequestParams params) throws Exception {
		return get(url, null, params);
	}

	public HttpResponse get(String url, Header[] headers, RequestParams params)
			throws Exception {
		HttpUriRequest request = new HttpGet(getUrlWithQueryString(url, params));
		if (headers != null)
			request.setHeaders(headers);
		return sendRequest((DefaultHttpClient) getHttpClient(),
				getHttpContext(), request);
	}

	public HttpResponse post(String url) throws Exception {
		return post(url, (RequestParams) null);
	}

	public HttpResponse post(String url, RequestParams params) throws Exception {
		return post(url, null, params);
	}

	public HttpResponse post(String url, HttpEntity entity) throws Exception {
		return post(url, null, entity);
	}

	public HttpResponse post(String url, Header[] headers, RequestParams params)
			throws Exception {
		HttpEntityEnclosingRequestBase request = new HttpPost(url);
		if (null != params)
			request.setEntity(params.getEntity());
		if (headers != null)
			request.setHeaders(headers);
		return sendRequest((DefaultHttpClient) getHttpClient(),
				getHttpContext(), request);
	}

	public HttpResponse post(String url, Header[] headers, HttpEntity entity)
			throws Exception {
		HttpEntityEnclosingRequestBase request = new HttpPost(url);
		if (null != entity)
			request.setEntity(entity);
		if (headers != null)
			request.setHeaders(headers);
		return sendRequest((DefaultHttpClient) getHttpClient(),
				getHttpContext(), request);
	}

	protected HttpResponse sendRequest(DefaultHttpClient client,
			HttpContext httpContext, HttpUriRequest uriRequest)
			throws Exception {
		// uriRequest.addHeader("Content-Type", "");
		RequestLine line = uriRequest.getRequestLine();
		LogUtil.d(line.getMethod() + " " + line.getUri() + " "
				+ line.getProtocolVersion().toString());
		if (uriRequest instanceof HttpEntityEnclosingRequest) {
			HttpEntity entity = ((HttpEntityEnclosingRequest) uriRequest)
					.getEntity();
			if (null != entity && entity.getContentLength() < 2 * 1024) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				entity.writeTo(out);
				LogUtil.d(out.toString());
			}
		}
		return client.execute(uriRequest, httpContext);
	}

}
