package com.xwz.mangayomu.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xwz.mangayomu.R;
import com.xwz.mangayomu.entity.BookInfo;

import java.util.List;

public class BookAdapter extends ArrayAdapter<BookInfo> {
    private final int resourceId;

    public BookAdapter(Context context, int textViewResourceId, List<BookInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookInfo bookInfo = getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {

            // 避免ListView每次滚动时都要重新加载布局
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder = new ViewHolder();
            viewHolder.coverImage = view.findViewById(R.id.cover_image);
            viewHolder.coverTitle = view.findViewById(R.id.cover_title);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.coverImage.setImageURI(bookInfo.getCoverUri());
        viewHolder.coverTitle.setText(bookInfo.getBookName());
        return view;
    }

    class ViewHolder {
        ImageView coverImage;
        TextView coverTitle;
    }
}