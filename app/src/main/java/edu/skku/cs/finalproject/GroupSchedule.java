package edu.skku.cs.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GroupSchedule extends AppCompatActivity {

    TextView txtname;
    TextView txtid;
    TextView txtmember;
    Button fix;
    Button add;
    EditText mm;
    EditText dd;
    private int[] p;
    private ListView listView;
    private fAdapter adapter;
    private ArrayList<Integer> finals;
    private ArrayList<Integer> timesfix;
    int count = 0;
    private Group group;
    private String[] members;
    private Person person;
    private ArrayList<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_schedule);
        listView = findViewById(R.id.listkk);
        finals = new ArrayList<Integer>();
        timesfix = new ArrayList<Integer>();
        mm = findViewById(R.id.editmonth);
        add = findViewById(R.id.addmem);
        dd = findViewById(R.id.editday);
        fix = findViewById(R.id.fix);
        txtname = findViewById(R.id.groupname);
        txtid = findViewById(R.id.groupid);
        txtmember = findViewById(R.id.groupmember);
        arr = new ArrayList<String>();
        Intent intent = getIntent();
        String groupid = intent.getSerializableExtra("group").toString();

        fix.setOnClickListener(view -> {
            if (count == 0){

                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder builder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/getgroup").newBuilder();
                builder.addQueryParameter("groupid", groupid);
                String url = builder.build().toString();
                Request request = new Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String myResponse = response.body().string();

                        Gson gson = new GsonBuilder().create();
                        group = gson.fromJson(myResponse, Group.class);
                        members = group.getMembers();
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                txtname.setText(group.getGroupname());
                                txtid.setText(group.getGroupid());
                                txtmember.setText(group.getMembers()[0]);
                                for ( int k = 1; k<group.getMembers().length;k++){
                                    txtmember.setText(txtmember.getText()+" "+group.getMembers()[k]);
                                }


                            }
                        }, 0);


                    }
                });


                count++;
            }

            else {
                System.out.println("safassadfasdadsfasdfsadf;laskdjf;lksadjfl;akdsfjl;aksdfjl;adskja;lkdjal;kds " + count);
                for ( int k = 0; k<members.length;k++){
                    OkHttpClient client2 = new OkHttpClient();
                    String id = members[k];
                    HttpUrl.Builder builder2 = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/getuser").newBuilder();
                    builder2.addQueryParameter("id", id);
                    String url2 = builder2.build().toString();
                    Request request2 = new Request.Builder().url(url2).build();
                    client2.newCall(request2).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                        }
                        String temp[] = new String[3];
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            String myResponse = response.body().string();
                            Gson gson = new GsonBuilder().create();
                            person = gson.fromJson(myResponse, Person.class);
                            ArrayList<String> test = new ArrayList<>();
                            p = new int[24];
                            finals.clear();
                            if (person.getSchedules().length == 0){
                                for ( int a = 0; a<24;a++){
                                    finals.add(0);
                                }
                            }
                            else {
                                for (int s = 0; s < person.getSchedules().length; s++) {
                                    String a = String.valueOf(person.getSchedules()[s][1]);
                                    String b = String.valueOf(person.getSchedules()[s][2]);
                                    String c = String.valueOf(person.getSchedules()[s][3]);
                                    arr.add(a);
                                    arr.add(b);
                                    arr.add(c);

                                    String am = mm.getText().toString();
                                    String bm = dd.getText().toString();
                                    for (int k = 0; k < arr.size() - 2; k++) {

                                        if (k % 3 == 0 && arr.get(k).equals(am) && arr.get(k + 1).equals(bm)) {
                                            System.out.println("123123");
                                            timesfix.add(Integer.valueOf(arr.get(k + 2)));
                                            System.out.println(Integer.parseInt(arr.get(k + 2)));

                                        }
                                    }
                                    for (int k = 0; k < timesfix.size(); k++) {
                                        System.out.println(timesfix.get(k));
                                    }

                                    for (int z = 0; z < timesfix.size(); z++) {
                                        p[timesfix.get(z)] = 1;
                                    }


                                    for (int z = 0; z < 24; z++) {
                                        System.out.println(p[z]);
                                        finals.add(p[z]);
                                    }


                                }
                            }
                            Handler mHandler = new Handler(Looper.getMainLooper());
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new fAdapter(finals, getApplicationContext());
                                    listView.setAdapter(adapter);;
                                }
                            }, 0    );

                        }

                    });
                }

                count++;

            }
        });





    }
}