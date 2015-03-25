package com.warmtel.android.main.useful;

/**
 * 
 * @author Administrator 项目描述: 仿网易新闻，今日头条手机App客户端,实现类似界面效果和功能. 界面效果图参考新闻快讯.
 *         接口定义如下: 新闻模块: 1.头条新闻列表接口 参数定义: int pageNo = 0; //页号 ，表示第几页,第一页从0开始
 *         int pageSize = 20; //页大小，显示每页多少条数据 String news_type_id =
 *         "T1348647909107"; //新闻类型标识, 此处表示头条新闻 请地址:
 *         "http://c.m.163.com/nc/article/headline/"+ news_type_id
 *         +pageNo*pageSize+ "-" +pageSize+ ".html" 请求方式:Get 例如:
 *         http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
 *         //表示请求头条新闻第一页20条数据
 *         http://c.m.163.com/nc/article/headline/T1348647909107/20-20.html
 *         //表示请求头条新闻第二页20条数据
 *         http://c.m.163.com/nc/article/headline/T1348647909107/40-20.html
 *         //表示请求头条新闻第三页20条数据 2.头条新闻内容接口 参数定义: String docid = A90HHI6I00014SEH ;
 *         //新闻ID ,从新闻列表项目获取 请地址: "http://c.m.163.com/nc/article/"+docid
 *         +"/full.html" 请求方式:Get 例如:
 *         http://c.m.163.com/nc/article/A90HHI6I00014SEH/full.html //获取新闻ID
 *         docid为A90HHI6I00014SEH的新闻内容 3. 娱乐新闻列表接口 参数定义: int pageNo = 0; //页号
 *         ，表示第几页,第一页从0开始 int pageSize = 20; //页大小，显示每页多少条数据 String news_type_id
 *         = "T1348648517839 "; //新闻类型标识, 此处表示娱乐新闻 请地址:
 *         "http://c.m.163.com/nc/article/list/"+news_type_id +pageNo*pageSize+
 *         "-" +pageSize+ ".html" 请求方式:Get 例如:
 *         http://c.m.163.com/nc/article/list/T1348648517839/0-20.html
 *         //表示请求娱乐新闻第一页20条数据
 *         http://c.m.163.com/nc/article/list/T1348648517839/20-20.html //表示请求娱乐
 *         新闻第二页20条数据 4. 体育新闻列表接口 参数定义: int pageNo = 0; //页号 ，表示第几页,第一页从0开始 int
 *         pageSize = 20; //页大小，显示每页多少条数据 String news_type_id =
 *         "T1348649079062"; //新闻类型标识, 此处表示娱乐新闻 请地址:
 *         "http://c.m.163.com/nc/article/list/"+news_type_id +pageNo*pageSize+
 *         "-" +pageSize+ ".html" 请求方式:Get 例如:
 *         http://c.m.163.com/nc/article/list/T1348649079062/0-20.html
 *         //表示请求新闻第一页20条数据
 *         http://c.m.163.com/nc/article/list/T1348649079062/20-20.html //表示请求
 *         新闻第二页20条数据 5. 财经新闻列表接口 参数定义: int pageNo = 0; //页号 ，表示第几页,第一页从0开始 int
 *         pageSize = 20; //页大小，显示每页多少条数据 String news_type_id =
 *         "T1348648756099"; //新闻类型标识, 此处表示娱乐新闻 请地址:
 *         "http://c.m.163.com/nc/article/list/"+news_type_id +pageNo*pageSize+
 *         "-" +pageSize+ ".html" 请求方式:Get 例如:
 *         http://c.m.163.com/nc/article/list/T1348648756099/0-20.html
 *         //表示请求新闻第一页20条数据
 *         http://c.m.163.com/nc/article/list/T1348648756099/20-20.html //表示请求
 *         新闻第二页20条数据 
 *         6. 科技新闻列表接口 参数定义:
 *          int pageNo = 0; //页号 ，表示第几页,第一页从0开始
 *           int pageSize = 20; //页大小，显示每页多少条数据 
 *           String news_type_id = "T1348649580692"; //新闻类型标识, 此处表示娱乐新闻 
 *           请地址: "http://c.m.163.com/nc/article/list/"+news_type_id +pageNo*pageSize+"-" +pageSize+ ".html" 请求方式:Get 例如:
 *         http://c.m.163.com/nc/article/list/T1348649580692/0-20.html //表示请求新闻第一页20条数据
 *         http://c.m.163.com/nc/article/list/T1348649580692/20-20.html //表示请求新闻第二页20条数据
 * 
 * 图片模块
 *         图片模块: 1.精选图片列表接口 请求地址: http://api.sina.cn/sinago/list.json 请求参数:
 *         channel=hdpic_toutiao &adid=4ad30dabe134695c3b7c3a65977d7e72 &wm=b207
 *         &from=6042095012 &chwm=12050_0001 &oldchwm= &imei=867064013906290
 *         &uid=802909da86d9f5fc &p= 请求方式:Get
 * 
 * 
 *         5.图片详情接口 请求地址: http://api.sina.cn/sinago/article.json 请求参数:
 *         postt=hdpic_hdpic_toutiao_4 &wm=b207 &from=6042095012
 *         &chwm=12050_0001 &oldchwm=12050_0001 &imei=867064013906290
 *         &uid=802909da86d9f5fc &id= //对应图片列表某项ID字段 如：精选图片列表接口第一项数据 id:
 *         "2841-75964-hdpic", 请求方式:Get
 * 视频模块
 * 1.热点视频列表接口
              参数定义: 
                           int pageNo = 0; //页号 ，表示第几页,第一页从0开始
                           int pageSize = 10; //页大小，显示每页多少条数据
                           String video_type_id = "V9LG4B3A0";  //视频类型标识, 此处表示热点视频 
             请地址: "http://c.3g.163.com/nc/video/list/"+ video_type_id + "/n/" +pageNo*pageSize+ "-"  +pageSize+ ".html"
             请求方式:Get
            例如: http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/0-10.html    //表示请求热点视频 第一页10条数据
                   http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/10-10.html     //表示请求热点视频 第二页10条数据
                   http://c.3g.163.com/nc/video/list/V9LG4B3A0/n/20-10.html     //表示请求热点视频 第三页10条数据




          2.娱乐视频列表接口              参数定义: 
                           int pageNo = 0; //页号 ，表示第几页,第一页从0开始
                           int pageSize = 10; //页大小，显示每页多少条数据
                           String video_type_id = "V9LG4CHOR";  //视频类型标识
             请地址: "http://c.3g.163.com/nc/video/list/"+ video_type_id + "/n/" +pageNo*pageSize+ "-"  +pageSize+ ".html"
             请求方式:Get
            例如: http://c.3g.163.com/nc/video/list/V9LG4CHOR /n/0-10.html    //表示请求 视频 第一页10条数据
                   http://c.3g.163.com/nc/video/list/V9LG4CHOR /n/10-10.html     //表示请求 视频 第二页10条数据
                   http://c.3g.163.com/nc/video/list/V9LG4CHOR /n/20-10.html     //表示请求 视频 第三页10条数据




         3.搞笑视频列表接口              参数定义: 
                           int pageNo = 0; //页号 ，表示第几页,第一页从0开始
                           int pageSize = 10; //页大小，显示每页多少条数据
                           String video_type_id = "V9LG4E6VR";  //视频类型标识, 
             请地址: "http://c.3g.163.com/nc/video/list/"+ video_type_id + "/n/" +pageNo*pageSize+ "-"  +pageSize+ ".html"
             请求方式:Get
            例如: http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/0-10.html    //表示请求 视频 第一页10条数据
                   http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/10-10.html     //表示请求视频 第二页10条数据
                   http://c.3g.163.com/nc/video/list/V9LG4E6VR/n/20-10.html     //表示请求视频 第三页10条数据






         4.精品视频列表接口              参数定义: 
                           int pageNo = 0; //页号 ，表示第几页,第一页从0开始
                           int pageSize = 10; //页大小，显示每页多少条数据
                           String video_type_id = "00850FRB";  //视频类型标识, 此处表示精品视频
             请地址: "http://c.3g.163.com/nc/video/list/"+ video_type_id + "/n/" +pageNo*pageSize+ "-"  +pageSize+ ".html"
             请求方式:Get
            例如: http://c.3g.163.com/nc/video/list/00850FRB/n/0-10.html    //表示请求 精品视频 第一页10条数据
                   http://c.3g.163.com/nc/video/list/00850FRB/n/10-10.html     //表示请求 精品视频 第二页10条数据
                   http://c.3g.163.com/nc/video/list/00850FRB/n/20-10.html     //表示请求 精品视频 第三页10条数据
 *         
 *         
 */

public class NewsInfo {
	public static int sPageNo = 0; // 表示初始界面显示第一页
	public static int sPageSize = 10; // 页大小，显示每页多少条数据

	public static String sDocID = "docID";// 具体新闻调度（各Fragment 和Activity通讯的参数标记）

	public static final int TODAY_HEADLINE = 0;// 头条
	public static final int ENTERTAINMENT = 1;// 娱乐
	public static final int SPORTS = 2;// 体育
	public static final int ECONOMICS = 3;// 财经
	public static final int SCIENCE = 4;// 科技

	// 封装成数组
	public static final int[] READ_STYLE = { TODAY_HEADLINE, ENTERTAINMENT,
			SPORTS, ECONOMICS, SCIENCE };

	// 新闻接口
	public static final String HEADLINE_PORT = "T1348647909107";// 新闻类型标识,
																// 此处表示头条新闻
	public static final String ENTERTAIN_PORT = "T1348648517839";// 娱乐新闻
	public static final String SPORTS_PORT = "T1348649079062";// 体育新闻
	public static final String ECONOMIC_PORT = "T1348648756099";// 财经新闻
	public static final String SCIENCE_PORT = "T1348649580692";// 科技新闻

	public static final String[] HTTP_PORT = { HEADLINE_PORT, ENTERTAIN_PORT,
			SPORTS_PORT, ECONOMIC_PORT, SCIENCE_PORT };
	/**
	 * ReadNewsTitle
	 */
	public static final String READNEWS_HEADLINE_TITLE = "头条";
	public static final String READNEWS_ENTERTAIN_TITLE = "娱乐";
	public static final String READNEWS_SPORTS_TITLE = "体育";
	public static final String READNEWS_ECONMIC_TITLE = "财经";
	public static final String READNEWS_SCIENCE_TITLE = "科技";

	public static final String[] TITLE_S = { READNEWS_HEADLINE_TITLE,
			READNEWS_ENTERTAIN_TITLE, READNEWS_SPORTS_TITLE,
			READNEWS_ECONMIC_TITLE, READNEWS_SCIENCE_TITLE };
	public enum ReadNewsStyle {
		TODAY_HEADLINE, ENTERTAINMENT, SPORTS, ECONOMICS, SCIENCE
	}

	// 请求地址
	public static final String HEADLINE_URL = "http://c.m.163.com/nc/article/headline/";// 头条URL
	public static final String OTHERS_URL = "http://c.m.163.com/nc/article/list/";// 其他URL
	/**
	 * 构造网址
	 */
	// public static String
	// HEADLINE_URL_CREATE=HEADLINE_URL+HEADLINE_PORT+sPageNo*sPageSize+"-"+sPageSize+".html";
	public static String HEADLINE_URL_CREATE = HEADLINE_URL + HEADLINE_PORT;
	public static String ENTERTAIN_URL_CREATE = OTHERS_URL + ENTERTAIN_PORT;
	public static String SPORTS_URL_CREATE = OTHERS_URL + SPORTS_PORT;
	public static String ECONOMIC_URL_CREATE = OTHERS_URL + ECONOMIC_PORT;
	public static String SCIENCE_URL_CREATE = OTHERS_URL + SCIENCE_PORT;
	/**
	 * 后缀封装
	 */
	public static String sTail = "/" + sPageNo * sPageSize + "-" + sPageSize+ ".html";
	/**
	 * 新闻具体信息网址构建 String docid = A90HHI6I00014SEH ; //新闻ID ,从新闻列表项目获取 请地址:
	 * "http://c.m.163.com/nc/article/"+docid +"/full.html"
	 */
	public static String sNewsInfoHead = "http://c.m.163.com/nc/article/";
	public static String sNewsInfoTail = "/full.html";

	//=========================图片详情=====================================
	
	/**
	 * PicNewsTitle
	 */
	public static String PicNews_handpickTitle = "精选";
	public static String PicNews_funTitle = "趣图";
	public static String PicNews_beautyTitle = "美图";
	public static String PicNews_storyTitle = "故事";
	public static final String[] PIC_TITLE_S = { PicNews_handpickTitle,
		PicNews_funTitle,PicNews_beautyTitle ,PicNews_storyTitle};
	/**
	 * 图片接口信息归总
	 */
	// 精选图片 get方式
	// 请求地址
	public static final String REQUSET_HEADURL = "http://api.sina.cn/sinago/list.json";
	// 请求参数
	public static final String CHANNEL_JINXUAN = "?channel=hdpic_toutiao";
	public static final String ADID = "&adid=4ad30dabe134695c3b7c3a65977d7e72";
	public static final String WM = "&wm=b207";
	public static final String FROM = "&from=6042095012";
	public static final String CHWM = "&chwm=12050_0001";
	public static final String OLDCHWM = "&oldchwm=";
	public static final String IMEI = "&imei=867064013906290";
	public static final String UID = "&uid=802909da86d9f5fc";
	public static final String PORT = "&p=";
	public static final String REQUSET_TAILURL = "&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=&imei=867064013906290&uid=802909da86d9f5fc&p=";
	// 趣图
	public static final String CHANNEL_FUNNY = "?channel=hdpic_funny";
	// 美图
	public static final String CHANNEL_PRETTY = "?channel=hdpic_pretty";
	// 故事
	public static final String CHANNEL_STORY = "?channel=hdpic_story";
	public static String getRequestUrl(String channel)
	{
		return REQUSET_HEADURL+channel+REQUSET_TAILURL;
	}
	public static final  String[] CHANNEL_S={
		CHANNEL_JINXUAN,
		CHANNEL_FUNNY,
		CHANNEL_PRETTY,
		CHANNEL_STORY	
	};
	/**
	 * 图片详情请求
	 */
	public static final String POSTT="?postt=hdpic_hdpic_toutiao_4";
	public static final String DETAIL_OLDCHWM="&oldchwm=12050_0001";
	public static final String REQUSET_DETAIL_MIDDLEURL=POSTT+WM+FROM+CHWM+DETAIL_OLDCHWM+IMEI+UID+"&id=";
	public static final String REQUSET_DETAIL_HEADURL = "http://api.sina.cn/sinago/article.json";
	public static String  getPicDetailUrl(String id)
	{
		return REQUSET_DETAIL_HEADURL+REQUSET_DETAIL_MIDDLEURL+id;
	}
	//=================================Video详情==================================
	
	/**
	 * VideoNewsTitle
	 */
	public static String VideoNews_hotspotTitle = "热点";
	public static String VideoNews_yuleTitle = "娱乐";
	public static String VideoNews_funnyTitle = "搞笑";
	public static String VideoNews_boutiqueTitle = "精品";
	public static final String[] VIDEO_TITLE_S = { VideoNews_hotspotTitle,
		VideoNews_yuleTitle,VideoNews_funnyTitle ,VideoNews_boutiqueTitle};
	
	//"http://c.3g.163.com/nc/video/list/"
	public static final String VIDEO_HEADURL="http://c.3g.163.com/nc/video/list/";
	public static final String HOT_VIDEO_PORT="V9LG4B3A0"; //热点视频
	public static final String YULE_VIDEO_PORT="V9LG4CHOR";//娱乐视频 
	public static final String FUN_VIDEO_PORT="V9LG4E6VR"; //搞笑视频
	public static final String BOUTIQUE_VIDEO_PORT="00850FRB"; //精品视频
//"/n/" +pageNo*pageSize+ "-"  +pageSize+ ".html"
	public static final String VIDEO_TAILURL = "/n/" + sPageNo * sPageSize + "-" + sPageSize+ ".html";
	public static String getVideoUrl(String port,int CurrentPage)
	{
		return VIDEO_HEADURL+port+ "/n/" + CurrentPage * sPageSize + "-" + sPageSize+ ".html";
	}
	
	public static final  String[] VIDEO_PORTS={
		HOT_VIDEO_PORT,
		YULE_VIDEO_PORT,
		FUN_VIDEO_PORT,
		BOUTIQUE_VIDEO_PORT	
	};
	//http://api.36wu.com/Weather/GetWeather?district=北京
	public static final String WEATHER_PORT="http://api.36wu.com/Weather/GetWeather?district=";
	
	
}
