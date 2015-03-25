package com.warmtel.android.main.useful.jsonobj;

public class VideoModelObj {
	private String cover;//画面截图
	private String title;//标题
	private int replyCount;//播放次数
	private String mp4_url;//视频播放地址,普清的
	private int length;//视频的秒数
	private String mp4Hd_url;//高清视频地址
	private int playersize;
	private String m3u8Hd_url;//m3u8格式高清地址
	private String ptime;//编辑时间
	private String m3u8_url;
	private String vid;
	public VideoModelObj() {
		
	}
	public VideoModelObj(String cover, String title, int replyCount,
			String mp4_url, int length, String mp4Hd_url, int playersize,
			String m3u8Hd_url, String ptime, String m3u8_url, String vid) {
		this.cover = cover;
		this.title = title;
		this.replyCount = replyCount;
		this.mp4_url = mp4_url;
		this.length = length;
		this.mp4Hd_url = mp4Hd_url;
		this.playersize = playersize;
		this.m3u8Hd_url = m3u8Hd_url;
		this.ptime = ptime;
		this.m3u8_url = m3u8_url;
		this.vid = vid;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public String getMp4_url() {
		return mp4_url;
	}
	public void setMp4_url(String mp4_url) {
		this.mp4_url = mp4_url;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getMp4Hd_url() {
		return mp4Hd_url;
	}
	public void setMp4Hd_url(String mp4Hd_url) {
		this.mp4Hd_url = mp4Hd_url;
	}
	public int getPlayersize() {
		return playersize;
	}
	public void setPlayersize(int playersize) {
		this.playersize = playersize;
	}
	public String getM3u8Hd_url() {
		return m3u8Hd_url;
	}
	public void setM3u8Hd_url(String m3u8Hd_url) {
		this.m3u8Hd_url = m3u8Hd_url;
	}
	public String getPtime() {
		return ptime;
	}
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}
	public String getM3u8_url() {
		return m3u8_url;
	}
	public void setM3u8_url(String m3u8_url) {
		this.m3u8_url = m3u8_url;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	
	

}
