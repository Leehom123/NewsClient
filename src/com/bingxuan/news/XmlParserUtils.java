package com.bingxuan.news;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XmlParserUtils {

	public  static  List<News> parserXml(InputStream in) throws Exception{
		
			//��ȡxml������
			List<News> newLists=null;
			News news=null;
			XmlPullParser parser = Xml.newPullParser();
			//���ý�����Ҫ����������
			parser.setInput(in,"utf-8");
			//��ȡ�������¼����͡�
			int type = parser.getEventType();
			//��ͣ�����½���
			while(type!=XmlPullParser.END_DOCUMENT){
				//�����ж����ǿ�ʼ�ڵ㻹�ǽ����ڵ�
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
