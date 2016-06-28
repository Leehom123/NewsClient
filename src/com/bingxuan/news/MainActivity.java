package com.bingxuan.news;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private ListView lv;
    private List<News> newslist;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        initListDate();
        
    }

	private void initListDate() {
		// TODO Auto-generated method stub
		new Thread(){

		public void run() {
			try {
				String path ="http://169.254.199.43:8080/news.xml";
				URL url=new URL(path);
				//getConnection()得到HttpURLConnection对象，来发送或接受数据，
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//给服务器发送get请求
				conn.setRequestMethod("GET");//如果不写这句也是发送get请求
				//设置请求超时时间
				conn.setConnectTimeout(4000);
				//获取服务器响应码
				int code = conn.getResponseCode();
				if (code==200) {
					//获取服务器返回的数据，是一流的形式返回的，流转换成字符串很常用所以抽取成一个util类
					InputStream in = conn.getInputStream();
					newslist = XmlParserUtils.parserXml(in);

					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// 更新UI
							lv.setAdapter(new myAdapter());	
						}
					});
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};

		}.start();
	}
private class myAdapter extends BaseAdapter{

	@Override
	public int getCount() {
		return newslist.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView==null) {
			//复用原来的item
			view=View.inflate(getApplicationContext(), R.layout.item1, null);
		}else {
			//创建新的item
			view=convertView;
		}
		
		TextView title = (TextView) view.findViewById(R.id.tv_title);
		TextView description = (TextView) view.findViewById(R.id.tv_desp);
		title.setText(newslist.get(position).getTitle());
		description.setText(newslist.get(position).getDiscription());
		return view;
	}
	
}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
