package com.example.cwnu_diner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
////////////////// StoreLists Activity의 리스트뷰를 구성하는 함수 ListviewAdapter////////////////////
public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // 총 몇개
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // i에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //final int pos = position;

        // listview Layout을 inflate하여 convertView 참조 획득.
        Context c=viewGroup.getContext();
        if(view==null){
            LayoutInflater li=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.listview, viewGroup,false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iv = view.findViewById(R.id.imageView1);
        TextView tv1= view.findViewById(R.id.textView1);
        TextView tv2= view.findViewById(R.id.textView2);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(i);

        // 아이템 내 각 위젯에 데이터 반영
        iv.setImageDrawable(listViewItem.getIcon());
        tv1.setText(listViewItem.getName());
        tv2.setText(listViewItem.getText());
        return view;
    }

    // 인덱스리턴
    @Override
    public long getItemId(int i) {
        return i ;
    }

    // i위치의 데이터 리턴
    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i) ;
    }

    // 아이템 데이터 추가
    public void addItem(Drawable icon, String name, String text) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setName(name);
        item.setText(text);

        listViewItemList.add(item);
    }
}