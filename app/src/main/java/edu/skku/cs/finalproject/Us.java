package edu.skku.cs.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Us extends Fragment {

    Button addgroup;
    Button addfriend;
    Button requestgroup;
    Button requestfriend;
    TextView newtext;
    Person info;
    private ListView listView0;
    private ListView group;
    private ListView friend;
    private groupfriendAdapter adapterG;
    private groupfriendAdapter adapterF;
    private grouprequestadapter adapter0;
    private groupfriendAdapter adapterFF;
    private groupfriendAdapter adapterGG;
    private ArrayList<String> G;
    private ArrayList<String> F;
    private ArrayList<String> items2;
    String ididid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_us, container, false);
        info = (Person) ((SecondActivity)getContext()).info;
        group = view.findViewById(R.id.listgroup);
        friend = view.findViewById(R.id.listfriend);

        String id = info.getId();
        ididid = id;
        G = new ArrayList<String>();
        F = new ArrayList<String>();
        items2 = new ArrayList<String>();
        String[] groups = info.getGroups();
        String[] friends = info.getFriends();
        for ( int k = 0; k<groups.length;k++){
            G.add(groups[k]);
        }
        for ( int k = 0; k<friends.length;k++){
            F.add(friends[k]);
        }


        adapterG = new groupfriendAdapter(G, getActivity().getApplicationContext(), id, 1);
        adapterF = new groupfriendAdapter(F, getActivity().getApplicationContext(), id, 1);
        group.setAdapter(adapterG);
        friend.setAdapter(adapterF);
        group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getActivity().getApplicationContext(), GroupSchedule.class);
                intent.putExtra("group", G.get(i));
                startActivity(intent);
            }
        });
        addgroup = view.findViewById(R.id.group);
        addgroup.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            EditText groupinput = new EditText(getActivity());
            builder.setTitle("새 그룹 추가");
            builder.setMessage("추가할 그룹 이름을 입력하세요.");
            if (groupinput.getParent() != null){
                ((ViewGroup) groupinput.getParent()).removeView(groupinput);
            }
            builder.setView(groupinput);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int cnt = 0;
                    for ( int s = 0; s<info.getGroups().length; s++){
                        if ( info.getGroups()[s].equals(groupinput.getText().toString())){
                            System.out.println("이미 그룹 있음");
                            cnt=1;
                            break;
                        }
                    }
                    if (cnt == 0){
                        OkHttpClient client = new OkHttpClient();
                        forgroup forgroup = new forgroup();
                        forgroup.setId(id);
                        forgroup.setGroupname(groupinput.getText().toString());
                        Gson gson = new Gson();
                        String json = gson.toJson(forgroup, forgroup.class);

                        HttpUrl.Builder urlbuilder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/addgroup").newBuilder();
                        String url = urlbuilder.build().toString();
                        Request request = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"),json)).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                final String myResponse = response.body().string();
                                if (myResponse.equals("\"T\"\n")){
                                    G.add(id+groupinput.getText().toString());
                                    adapterG = new groupfriendAdapter(G,getActivity().getApplicationContext(), id, 1);
                                    Handler mHandler = new Handler(Looper.getMainLooper());
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            group.setAdapter(adapterG);
                                        }

                                    }, 0);

                                }
                                else{
                                    System.out.println("실패");
                                }
                            }
                        });
                    }

                    String arr[] = new String[info.getGroups().length+1];

                    newtext = new TextView(getContext());
                    newtext.setText(String.valueOf(groupinput.getText()));
                    newtext.setTextSize(30);

                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        });


        requestgroup = view.findViewById(R.id.request0);
        requestgroup.setOnClickListener(view1 -> {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder builder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/getuser").newBuilder();
            builder.addQueryParameter("id", id);

            String url = builder.build().toString();
            Request request = new Request.Builder().url(url).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response2) throws IOException {

                    final String myResponse = response2.body().string();
                    Gson gson = new GsonBuilder().create();
                    info = gson.fromJson(myResponse, Person.class);
                    String arr[] = info.getGrouprequest();
                    items2.clear();
                    for ( int k = 0; k<arr.length; k++){
                        items2.add(arr[k]);

                    }
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            LayoutInflater layoutInflater = getLayoutInflater();
                            final View dialog = layoutInflater.inflate(R.layout.grourequest,null);
                            builder1.setTitle("받은요청");
                            listView0 = (ListView) dialog.findViewById(R.id.listgrouprequest);
                            if (listView0.getParent() != null){
                                ((ViewGroup) listView0.getParent()).removeView(listView0);
                            }

                            adapter0 = new grouprequestadapter(items2, getActivity().getApplicationContext(), id,1, Us.this);
                            listView0.setAdapter(adapter0);

                            builder1.setView(listView0);
                            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder1.show();
                        }
                    }, 0);
                }
            });



        });

        addfriend = view.findViewById(R.id.friend);
        addfriend.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            EditText groupinput = new EditText(getActivity());
            builder.setTitle("새 친구 추가");
            builder.setMessage("추가할 친구 아이디를 입력하세요.");
            builder.setView(groupinput);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String idid = groupinput.getText().toString();
                    if (!id.equals(idid)) {
                        OkHttpClient client = new OkHttpClient();
                        forfriend forfriend = new forfriend();
                        forfriend.setMyid(id);
                        forfriend.setFriendid(idid);
                        Gson gson = new Gson();
                        String json = gson.toJson(forfriend, forfriend.class);

                        HttpUrl.Builder urlbuilder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/addfriend").newBuilder();
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
                                if (myResponse.equals("\"T\"\n")) {
                                    System.out.println("success");
                                } else {
                                    System.out.println("실패");
                                }
                            }
                        });
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        });

        requestfriend = view.findViewById(R.id.request);
        requestfriend.setOnClickListener(view1 -> {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder builder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/getuser").newBuilder();
            builder.addQueryParameter("id", id);

            String url = builder.build().toString();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response2) throws IOException {
                    final String myResponse = response2.body().string();
                    Gson gson = new GsonBuilder().create();
                    info = gson.fromJson(myResponse, Person.class);
                    String arr[] = info.getFriendrequest();
                    items2.clear();
                    for ( int k = 0; k<arr.length; k++){
                        items2.add(arr[k]);

                    }
                    Handler mHandler = new Handler(Looper.getMainLooper());
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            LayoutInflater layoutInflater = getLayoutInflater();
                            final View dialog = layoutInflater.inflate(R.layout.grourequest,null);
                            builder1.setTitle("받은요청");
                            listView0 = (ListView) dialog.findViewById(R.id.listgrouprequest);
                            if (listView0.getParent() != null){
                                ((ViewGroup) listView0.getParent()).removeView(listView0);
                            }
                            adapter0 = new grouprequestadapter(items2, getActivity().getApplicationContext(), id, 2, Us.this);
                            listView0.setAdapter(adapter0);

                            builder1.setView(listView0);
                            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder1.show();
                            System.out.println(info.getFriends().length);
                        }
                    }, 0);

                }
            });


        });
        update();


        return view;
    }
    public void update(){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder builder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/getuser").newBuilder();
        builder.addQueryParameter("id", ididid);

        String url = builder.build().toString();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response2) throws IOException {
                final String myResponse = response2.body().string();
                Gson gson = new GsonBuilder().create();
                info = gson.fromJson(myResponse, Person.class);
                Handler mHandelr = new Handler(Looper.getMainLooper());
                F.clear();
                G.clear();
                String[] groups = info.getGroups();
                String[] friends = info.getFriends();
                for ( int k = 0; k<groups.length;k++){
                    G.add(groups[k]);
                }
                for ( int k = 0; k<friends.length;k++){
                    F.add(friends[k]);
                }
                if (F.size()==0){
                    F.add("NOFRIENDS");
                }
                mHandelr.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapterFF = new groupfriendAdapter(F, getActivity().getApplicationContext(), ididid, 2);
                        friend.setAdapter(adapterFF);
                        System.out.println(F.size());
                        adapterGG = new groupfriendAdapter(G, getActivity().getApplicationContext(), ididid, 1);
                        group.setAdapter(adapterGG);
                    }
                }, 0);

            }
        });

    }
}