package com.warmtel.android.main.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.content.Context;
import android.os.AsyncTask;

import com.warmtel.android.common.configs.Logs;

public class HttpConnectionUtil {
	private final static String TAG = "HttpConnectionUtil";

	public static enum HttpMethod {
		GET, POST
	}

	private Context mContext;

	public HttpConnectionUtil(Context context) {
		mContext = context;
	}

	public HttpConnectionUtil() {

	}

	/**
	 * 回调接口
	 */
	public interface HttpConnectionCallback {
		/**
		 * Call back method will be execute after the http request return.
		 * 
		 * @param response
		 *            the response of http request. The value will be null if
		 *            any error occur.
		 */
		void onResponse(String response);

		void onErrorResponse(String errorMessage);
	}

	/**
	 * 不缓存无参数
	 * 
	 * @param url
	 * @param method
	 * @param callback
	 */
	public void asyncConnect(final String url, final HttpMethod method,
			final HttpConnectionCallback callback) {
		asyncConnect(url, null, method, null, callback);
	}

	/**
	 * 有缓存无参数
	 * 
	 * @param url
	 * @param method
	 * @param cacheUrl
	 * @param callback
	 */
	public void asyncConnect(final String url, final HttpMethod method,
			final String cacheUrl, final HttpConnectionCallback callback) {
		asyncConnect(url, null, method, cacheUrl, callback);
	}

	/**
	 * 无缓存有参数
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @param callback
	 */
	public void asyncConnect(final String url,
			final Map<String, String> params, final HttpMethod method,
			final HttpConnectionCallback callback) {
		asyncConnect(url, params, method, null, callback);
	}

	/**
	 * 有缓存有参数
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @param cacheUrlK
	 * @param callback
	 */
	public void asyncConnect(final String url,
			final Map<String, String> params, final HttpMethod method,
			final String cacheUrlK, final HttpConnectionCallback callback) {

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... param) {
				try {
					return asyncConnects(url, params, method, cacheUrlK,
							callback);
				} catch (MessagingException e) {
					Logs.e("doinbackgroud MessagingException >>>>>");
					callback.onErrorResponse(e.getMessage());
					return null;
				}
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result != null)
					callback.onResponse(result);

			}

		}.execute();
	}

	public String asyncConnects(final String url,
			final Map<String, String> params, final HttpMethod method,
			final String cacheUrlKey, final HttpConnectionCallback callback)
			throws MessagingException {
		String json = null;
		BufferedReader reader = null;
		InputStream is = null;
		try {
			HttpClient client = new DefaultHttpClient();
			// 请求超时
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			// 读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					15000);

			HttpUriRequest request = getRequest(url, params, method);

			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				is = response.getEntity().getContent();
				reader = new BufferedReader(new InputStreamReader(is));
				// 创建StringBuilder对象用于存储所有数据
				StringBuilder sb = new StringBuilder();
				// 定义String类型用于储存单行数据
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				json = sb.toString();

				if (cacheUrlKey != null && mContext != null && json!=null) {
					ApiPreference.getInstance(mContext).putCache(cacheUrlKey,json);
				}
			} else {
				throw new MessagingException(
						MessagingException.SERVICE_CONNECT_ERROR, "与服务器连接错误");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new MessagingException(MessagingException.NET_IO_ERROR,
					"与服务器连接超时, 请检查你的网络状态 .");
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

	/**
	 * POST跟GET传递参数不同,POST是隐式传参GET是显式传参
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            参数
	 * @param method
	 *            方法
	 * @return
	 */
	private HttpUriRequest getRequest(String url, Map<String, String> params,
			HttpMethod method) {
		if (method.equals(HttpMethod.POST)) {
			List<NameValuePair> listParams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String name : params.keySet()) {
					listParams.add(new BasicNameValuePair(name, params
							.get(name)));
				}
			}
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
						listParams);
				HttpPost request = new HttpPost(url);
				request.setEntity(entity);
				return request;
			} catch (UnsupportedEncodingException e) {
				// Should not come here, ignore me.
				throw new java.lang.RuntimeException(e.getMessage(), e);
			}
		} else {

			if (url.indexOf("?") < 0) {
				url += "?";
			}
			if (params != null) {
				for (String name : params.keySet()) {
					try {
						url += "&" + name + "="
								+ URLEncoder.encode(params.get(name), "UTF-8");

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			HttpGet request = new HttpGet(url);
			return request;
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param is
	 * @throws IOException
	 */
	private void downLoadFile(InputStream is) {
		File file = new File("/sdcard/music/android1.apk");
		FileOutputStream out = null;
		BufferedOutputStream outs = null;
		try {
			out = new FileOutputStream(file);
			outs = new BufferedOutputStream(out);
			byte[] buf = new byte[128];
			while (is.read(buf) != -1) {
				outs.write(buf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outs != null) {
					outs.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
