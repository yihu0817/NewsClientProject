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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

public class HttpConnectionUtil {
	private final static String TAG = "HttpConnectionUtil";
	public static enum HttpMethod {
		GET, POST
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
		void execute(String response);
	}
	/**
	 * 异步连接
	 * 
	 * @param url
	 *            网址
	 * @param method
	 *            Http方法,POST跟GET
	 * @param callback
	 *            回调方法,返回给页面或其他的数据
	 */
	public void asyncConnect(final String url, final HttpMethod method,
			final HttpConnectionCallback callback) {
		asyncConnect(url, null, method, callback);
	}

	/**
	 * 同步方法
	 * 
	 * @param url
	 *            网址
	 * @param method
	 *            Http方法,POST跟GET
	 * @param callback
	 *            回调方法,返回给页面或其他的数据
	 */
	public void syncConnect(final String url, final HttpMethod method,
			final HttpConnectionCallback callback) {
		syncConnect(url, null, method, callback);
	}

	/**
	 * 异步带参数方法
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            POST或GET要传递的参数
	 * @param method
	 *            方法,POST或GET
	 * @param callback
	 *            回调方法
	 */
	public void asyncConnect(final String url,
			final Map<String, String> params, final HttpMethod method,
			final HttpConnectionCallback callback) {

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... param) {
				return asyncConnects(url, params, method, callback);
			}

			@Override
			protected void onPostExecute(String result) {
				callback.execute(result);
				super.onPostExecute(result);
			}

		}.execute();

	}

	/**
	 * 同步带参数方法
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            POST或GET要传递的参数
	 * @param method
	 *            方法,POST或GET
	 * @param callback
	 *            回调方法
	 */
	public void syncConnect(final String url, final Map<String, String> params,
			final HttpMethod method, final HttpConnectionCallback callback) {
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				syncConnects(url, params, method, callback);
			}
		});

	}

	/**
	 * 同步带参数方法
	 * 
	 * @param url
	 *            网址
	 * @param params
	 *            POST或GET要传递的参数
	 * @param method
	 *            方法,POST或GET
	 * @param callback
	 *            回调方法
	 */
	public void syncConnects(final String url,
			final Map<String, String> params, final HttpMethod method,
			final HttpConnectionCallback callback) {
		String json = null;
		BufferedReader reader = null;
		InputStream is = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpUriRequest request = getRequest(url, params, method);
			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				is = response.getEntity().getContent();
				reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				for (String s = reader.readLine(); s != null; s = reader
						.readLine()) {
					sb.append(s);
				}

				json = sb.toString();
			}
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
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
		callback.execute(json);
	}

	public String asyncConnects(final String url,
			final Map<String, String> params, final HttpMethod method,
			final HttpConnectionCallback callback) {
		String json = null;
		BufferedReader reader = null;
		InputStream is = null;
		try {
			HttpClient client = new DefaultHttpClient();
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
			}
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
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
