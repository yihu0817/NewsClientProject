package com.warmtel.android.main.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class AsyncImageGet {
	private onBitMapListener onBitmapListen;
	private BitmapGetAsync mBitmapGetAsync;
	
	public AsyncImageGet(String url)
	{
		mBitmapGetAsync=new BitmapGetAsync();
		mBitmapGetAsync.execute(url);
	}
	public void setOnBitmapListen(onBitMapListener onBitmapListen) {
		this.onBitmapListen = onBitmapListen;
	}
	protected Bitmap loadImageBitmapFromUrl(String imageUrl) {
	HttpURLConnection conn;
		try {
			URL url = new URL(imageUrl);
			conn=(HttpURLConnection)url.openConnection();
			InputStream is=conn.getInputStream();
//			InputStream is = url.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public class BitmapGetAsync extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bit = loadImageBitmapFromUrl(params[0]);
			return bit;
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			onBitmapListen.onBitmapCallBack(result);
		}
	}
	public interface onBitMapListener {
		public void onBitmapCallBack(Bitmap result);
	}

}
