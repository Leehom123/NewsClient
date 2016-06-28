package com.bingxuan.news;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XmlParserUtils {

	public  static  List<News> parserXml(InputStream in) throws Exception{
		
			//获取xml解析器
			List<News> newLists=null;
			News news=null;
			XmlPullParser parser = Xml.newPullParser();
			//设置解析器要解析的内容
			parser.setInput(in,"utf-8");
			//获取解析的事件类型。
			int type = parser.getEventType();
			//不停地向下解析
			while(type!=XmlPullParser.END_DOCUMENT){
				//具体判断下是开始节点还是结束节点
				switch (type) {
				case XmlPullParser.START_TAG:
					if ("channel".equals(parser.getName())) {
						newLists=new ArrayList<News>();
					}else if ("title".equals(parser.getName())) {
						news.setTitle(parser.nextText());
					}else if ("description".equals(parser.getName())) {
						news.setDiscription(parser.nextText());
					}else if ("type".equals(parser.getName())) {
						news.setType(parser.nextText());
					}else if ("comment".equals(parser.getName())) {
						news.setComment(parser.nextText());
					}
					
					break;
				case XmlPullParser.END_TAG:
					if ("item".equals(parser.getName())) {
						newLists.add(news);
					}
					break;
				default:
					break;
				}
				type=parser.next();
			}
			return newLists;
		
		
		
	}
}
