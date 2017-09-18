package test.bwie.com.wuzeqiang1507d20170825;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 移动1507D  武泽强
 * 2017/8/25.
 * 作用：
 */

public class Myadapter extends BaseAdapter {
    private List<Bean.DataBean> list;
    private Context context;
    public Myadapter(Context context, List<Bean.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    //加载数据和刷新数据
    public void Loadmethed(List<Bean.DataBean> datas,boolean flag){
        for(Bean.DataBean data:datas){
            if(flag){
                list.add(0,data);

            }else{
                list.add(data);
            }

        }
        //从新刷新
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(position % 2==1){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);//根据类型判断
        ViewHolder1 viewHolder1 =null;
        ViewHolder2 viewHolder2 = null;
        switch (type){
            case 0:
                if(convertView == null){
                    convertView = View.inflate(context,R.layout.item1_layout,null);
                    viewHolder1 = new ViewHolder1();
                    viewHolder1.image= (ImageView) convertView.findViewById(R.id.image);
                    viewHolder1.name= (TextView) convertView.findViewById(R.id.name);
                    viewHolder1.title = (TextView) convertView.findViewById(R.id.title);
                    convertView.setTag(viewHolder1);
                }else{
                    viewHolder1= (ViewHolder1) convertView.getTag();
                }
                break;
            case 1:
                if(convertView == null){
                    convertView = View.inflate(context,R.layout.item2_layout,null);
                    viewHolder2 = new ViewHolder2();
                    viewHolder2.image= (ImageView) convertView.findViewById(R.id.image);
                    viewHolder2.name= (TextView) convertView.findViewById(R.id.name);
                    viewHolder2.title = (TextView) convertView.findViewById(R.id.title);
                    convertView.setTag(viewHolder2);
                }else{
                    viewHolder2= (ViewHolder2) convertView.getTag();
                }
                break;
        }
        switch (type){
            case 0:
                viewHolder1.title.setText(list.get(position).getTitle());
                viewHolder1.name.setText(list.get(position).getNick());
                ImageLoader.getInstance().displayImage(list.get(position).getApp_shuffling_image(),viewHolder1.image,Myapplication.GetOptios());
                break;

            case 1:
                viewHolder2.title.setText(list.get(position).getTitle());
                viewHolder2.name.setText(list.get(position).getNick());
                ImageLoader.getInstance().displayImage(list.get(position).getApp_shuffling_image(),viewHolder2.image,Myapplication.GetOptios());
                break;
        }


        return convertView;
    }
    class ViewHolder1{
        ImageView image;
        TextView name;
        TextView title;
    }
    class ViewHolder2{
        ImageView image;
        TextView name;
        TextView title;
    }
}
