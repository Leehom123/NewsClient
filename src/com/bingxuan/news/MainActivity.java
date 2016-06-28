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
				//getConnection()�õ�HttpURLConnection���������ͻ�������ݣ�
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//������������get����
				conn.setRequestMethod("GET");//�����д���Ҳ�Ƿ���get����
				//��������ʱʱ��
				conn.setConnectTimeout(4000);
				//��ȡ��������Ӧ��
				int code = conn.getResponseCode();
				if (code==200) {
					//��ȡ���������ص����ݣ���һ������ʽ���صģ���ת�����ַ����ܳ������Գ�ȡ��һ��util��
					InputStream in = conn.getInputStream();
					newslist = XmlParserUtils.parserXml(in);

					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// ����UI
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
			//����ԭ����item
			view=View.inflate(getApplicationContext(), R.layout.item1, null);
		}else {
			//�����µ�item
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
