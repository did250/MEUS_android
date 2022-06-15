package edu.skku.cs.finalproject;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;



public class fAdapter extends BaseAdapter {
    private ArrayList<Integer> items;
    private Context mContext;
    int i = 0;
    fAdapter(ArrayList<Integer> items, Context mContext){
        this.items = items;
        this.mContext = mContext;

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
        TextView txt1 = (TextView) view.findViewById(R.id.groupfriend);

        int s = i+1;
        if (items.get(i) == 1){
            txt1.setBackgroundColor(Color.parseColor("#000000"));
        }
        txt1.setText(i + " : 00 ~ " + s + " : 00");
        i++;



        return view;
    }
}
