package com.sqh.market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sqh.market.R;
import com.sqh.market.models.CommodityModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class myAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String,Object>> mlist;

    public myAdapter(Context context, ArrayList<HashMap<String,Object>> mlist){
        this.context=context;
        this.mlist=mlist;


    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        viewholder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_commodity_for_searchview, null);
            holder = new viewholder();
            holder.commodityName = convertView.findViewById(R.id.commodityName);
            holder.commodityInfo = convertView.findViewById(R.id.commodityInfo);
            holder.commodityPrice = convertView.findViewById(R.id.commodityPrice);
            holder.img = convertView.findViewById(R.id.commodityImg);
            //把ViewHolder对象封装到View对象中
            convertView.setTag(holder);
        }
        else {
            holder = (viewholder) convertView.getTag();
        }
            return convertView;

    }
    class viewholder {
        ImageView img;
        TextView commodityName, commodityInfo, commodityPrice;
    }
}

