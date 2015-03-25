package com.warmtel.android.main.useful.jsonobj;

import android.os.Parcel;
import android.os.Parcelable;

public class ReadNewsImagSrcObj  implements Parcelable{
	private String ref;
	private String pixel;
	private String alt;
	private String src;//图片网址,我们需要,其他还没得研究//10.27已经搞定
	
	public ReadNewsImagSrcObj()
	{
		
	}
	public ReadNewsImagSrcObj(Parcel in)
	{
		ref=in.readString();
		pixel=in.readString();
		alt=in.readString();
		src=in.readString();
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getPixel() {
		return pixel;
	}
	public void setPixel(String pixel) {
		this.pixel = pixel;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(alt);
		dest.writeString(pixel);
		dest.writeString(ref);
		dest.writeString(src);
	}
	public static final Parcelable.Creator<ReadNewsImagSrcObj> CREATOR=new Creator<ReadNewsImagSrcObj>() {

		@Override
		public ReadNewsImagSrcObj createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ReadNewsImagSrcObj(source);
		}

		@Override
		public ReadNewsImagSrcObj[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ReadNewsImagSrcObj[size];
		}
	};
	
}
