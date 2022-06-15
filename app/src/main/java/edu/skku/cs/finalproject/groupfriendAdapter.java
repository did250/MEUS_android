package edu.skku.cs.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;


public class groupfriendAdapter extends BaseAdapter {
    private ArrayList<String> items;
    private Context mContext;
    private String id;
    private int flag;

    groupfriendAdapter(ArrayList<String> items, Context mContext, String id, int flag){
        this.items = items;
        this.mContext = mContext;
        this.id = id;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.groupfriend, viewGroup, false);
        }
        TextView txt = (TextView) view.findViewById(R.id.groupfriend);
        txt.setText(items.get(i));

        return view;
    }
}
