package edu.skku.cs.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScheduleAdapter extends BaseAdapter {
    private ArrayList<String[]> items;
    private Context mContext;
    private String id;
    private Me fragment;
    ScheduleAdapter(ArrayList<String[]> items, Context mContext, String id, Me fragment){
        this.items = items;
        this.mContext = mContext;
        this.id = id;
        this.fragment=fragment;
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
            view = inflater.inflate(R.layout.schedule_list, viewGroup, false);
        }
        TextView txt1 = (TextView) view.findViewById(R.id.N1);
        TextView txt2 = (TextView) view.findViewById(R.id.N2);
        ImageButton delete = (ImageButton) view.findViewById(R.id.btndelete);
        txt1.setText(items.get(i)[0]);
        txt1.setBackgroundColor(Color.parseColor("#FED9CD"));
        int time = Integer.parseInt(items.get(i)[3]);
        txt2.setText(String.valueOf(time));
        if (i %2 == 0) {
            txt1.setBackgroundColor(Color.parseColor("#FED9CD"));
            txt2.setBackgroundColor(Color.parseColor("#FED9CD"));
        }
        else{
            txt1.setBackgroundColor(Color.parseColor("#D9D9D9"));
            txt2.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        delete.setOnClickListener(view1 -> {
            OkHttpClient client = new OkHttpClient();
            fordelete fordelete = new fordelete();
            fordelete.setId(id);
            fordelete.setShc_name(items.get(i)[0]);

            fordelete.setTime(Integer.valueOf(items.get(i)[3]));
            fordelete.setMonth(Integer.valueOf(items.get(i)[1]));
            fordelete.setDay(Integer.valueOf(items.get(i)[2]));
            Gson gson = new Gson();
            String json = gson.toJson(fordelete, fordelete.class);
            HttpUrl.Builder builder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/deleteschedule").newBuilder();
            String url = builder.build().toString();
            Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"),json)).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();
                    System.out.println(myResponse);
                    fragment.update();
                }
            });
        });

        return view;
    }
}
