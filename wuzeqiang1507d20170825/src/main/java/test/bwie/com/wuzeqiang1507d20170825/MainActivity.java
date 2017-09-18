package test.bwie.com.wuzeqiang1507d20170825;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.limxing.xlistview.view.XListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private ViewPager vp;
    private LinearLayout linear;
    private List<String> imagelist;
    private int currentindext=0;
    private int oldindext=0;
    private boolean flag;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vp.setCurrentItem(currentindext);
        }
    };
    private Myadapter myadapter;
    private XListView xlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitData();
        //做适配
        vp.setAdapter(new myPager());
        //调用线程中做无限循环
        autoplay();
        /**
         * vViewpager 添加小圆点
         * 做监听事件
         */
        init();
      getdata();


    }

    private void getdata(){
        GetNetData("http://www.quanmin.tv/json/categories/lol/list.json");
    }

    /**
     * 做加载数据的异步任务
     */
    private void GetNetData(String urll){
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s==null){
                    return;
                }
                //进行解析
                Gson gson = new Gson();
                Bean bean = gson.fromJson(s, Bean.class);
                List<Bean.DataBean> data = bean.getData();
                if(myadapter == null){
                    myadapter = new Myadapter(MainActivity.this,data);
                    xlv.setAdapter(myadapter);
                }else{
                    myadapter.Loadmethed(data,flag);
                }

            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");     //请求的方式是GET请求
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    //等待请求码
                    int code = connection.getResponseCode();
                    if(code== 200){
                        InputStream is = connection.getInputStream();
                        return StramTools.GETRead(is);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);


            }
        }.execute(urll);

    }

    /**
     * vViewpager 添加小圆点
     * 做监听事件
     */
    private void init() {
        //做对应相同图片的小圆点
        for ( int i =0;i<imagelist.size();i++){
            //自己定义的视图空间
            View img = new View(MainActivity.this);
            //视图的长和宽
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,30);
            //左间距
            params.leftMargin=10;
            //右间距
            params.rightMargin=10;
            img.setLayoutParams(params);
            if(i==0){
                img.setBackgroundResource(R.drawable.shape_select);
            }else{
                img.setBackgroundResource(R.drawable.shape_notxml);
            }
            //填充给Linearlayout 控件中
            linear.addView(img);
        }
        //对viewpager 做滑动监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当前的界面是成功的 红色的小圆点
                linear.getChildAt(position % imagelist.size()).setBackgroundResource(R.drawable.shape_select);
                //当前界面不是的显示的图片的时候就 蓝色的小圆点
                linear.getChildAt(oldindext %imagelist.size()).setBackgroundResource(R.drawable.shape_notxml);
                oldindext=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //初始化控件的iD
    private void InitData() {
        vp = (ViewPager) findViewById(R.id.vp);
        xlv = (XListView) findViewById(R.id.xlistview);
        linear = (LinearLayout) findViewById(R.id.linear);
        imagelist = Arrays.asList("http://p2.so.qhmsg.com/bdr/_240_/t01e8378dd608d74de7.jpg",
                "http://p0.so.qhmsg.com/bdr/_240_/t0137613c27d5b716a5.jpg",
                "http://p4.so.qhimgs1.com/bdr/_240_/t01b03cca59569f456a.jpg",
                "http://p2.so.qhimgs1.com/bdr/_240_/t01ebd4aba75daf1cee.jpg"
                );

        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);
    }

    /**
     * 在线程中做无限循环
     * 通过handler 更新主线程UI
     */
    private void autoplay(){
        new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        currentindext++;
                        Thread.sleep(3000);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    public void onRefresh() {
        flag= true;

        getdata();
        Toast.makeText(MainActivity.this,"刷新完成",Toast.LENGTH_SHORT).show();
        xlv.stopRefresh(true);
    }

    @Override
    public void onLoadMore() {
        flag= false;

        getdata();
        xlv.stopLoadMore();

    }

    //viewpager做适配
    class myPager extends PagerAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(MainActivity.this);
            ImageLoader.getInstance().displayImage(imagelist.get(position %imagelist.size()),image,Myapplication.GetOptios());
            container.addView(image);
            return image;
        }
    }


}
