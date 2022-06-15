package edu.skku.cs.finalproject;

import android.content.Context;
import android.graphics.Color;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class grouprequestadapter extends BaseAdapter {
    private ArrayList<String> items;
    private Context mContext;
    private String id;
    private int flag;
    private Us fragment;
    grouprequestadapter(ArrayList<String> items, Context mContext, String id, int flag, Us fragment){
        this.items = items;
        this.mContext = mContext;
        this.id = id;
        this.flag = flag;
        this.fragment = fragment;
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
            view = inflater.inflate(R.layout.grouprequestlist, viewGroup, false);
        }
        TextView txt1 = (TextView) view.findViewById(R.id.txtgroupname);
        txt1.setText(items.get(i));
        Button accept = view.findViewById(R.id.btnaccept);
        Button deny = view.findViewById(R.id.btndeny);
        accept.setOnClickListener(view1 -> {
            if (flag == 1) {
                OkHttpClient client = new OkHttpClient();
                forgroupaccept forgroupaccept = new forgroupaccept();
                forgroupaccept.setId(this.id);
                forgroupaccept.setGroupid(items.get(i));
                System.out.println(items.get(i));
                Gson gson = new Gson();
                String json = gson.toJson(forgroupaccept, forgroupaccept.class);

                HttpUrl.Builder urlbuilder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/acceptgroup").newBuilder();
                String url = urlbuilder.build().toString();
                Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), json)).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        final String myResponse = response.body().string();
                        System.out.println(myResponse);
                        if (myResponse.equals("\"T\"\n")) {
                            System.out.println("success");

                        } else {
                            System.out.println("실패");
                        }
                    }
                });
            }
            else if (flag == 2) {
                OkHttpClient client = new OkHttpClient();
                forfriend forfriend = new forfriend();
                forfriend.setMyid(this.id);
                forfriend.setFriendid(items.get(i));
                Gson gson = new Gson();
                String json = gson.toJson(forfriend, forfriend.class);

                HttpUrl.Builder urlbuilder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/acceptfriend").newBuilder();
                String url = urlbuilder.build().toString();
                Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), json)).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        final String myResponse = response.body().string();
                        System.out.println(myResponse);
                        if (myResponse.equals("\"T\"\n")) {
                        } else {
                            System.out.println("실패");
                        }

                    }
                });
                this.fragment.update();
            }
        });





        return view;
    }
}
