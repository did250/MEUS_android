package edu.skku.cs.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class Me extends Fragment {

    Person Meinfo;
    Button update;
    Button add;
    String id;

    private ListView listView;
    private ScheduleAdapter adapter;
    private ArrayList<String[]> items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        listView = (ListView) view.findViewById(R.id.schedulelist);
        items = new ArrayList<String[]>();
        update = view.findViewById(R.id.update);
        add = view.findViewById(R.id.btnadd);
        Meinfo = (Person) ((SecondActivity)getContext()).info;
        TextView textView = view.findViewById(R.id.textselect);
        CalendarView calendarView = view.findViewById(R.id.calendarView);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                textView.setText(String.format("%d년 %d월 %d일", i, i1+1, i2));

                items.clear();
                for ( int k = 0; k<Meinfo.getSchedules().length; k++){
                    int month = Integer.valueOf(Meinfo.getSchedules()[k][1]);
                    int day = Integer.valueOf(Meinfo.getSchedules()[k][2]);
                    if ( month == i1+1 && day ==i2){

                        items.add(Meinfo.getSchedules()[k]);
                    }
                }

                adapter = new ScheduleAdapter(items, getActivity().getApplicationContext(), id, Me.this);

                listView.setAdapter(adapter);

            }

        });

        id = Meinfo.getId();


        update.setOnClickListener(view1 -> {
            update();
        });

        add.setOnClickListener(view1 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater layoutInflater = getLayoutInflater();
            final View dialog = layoutInflater.inflate(R.layout.addschedule,null);
            builder.setTitle("새 스케줄");
            builder.setView(dialog);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    OkHttpClient client = new OkHttpClient();
                    EditText name = (EditText) dialog.findViewById(R.id.sch_name);
                    EditText month = (EditText) dialog.findViewById(R.id.sch_month);
                    EditText day = (EditText) dialog.findViewById(R.id.sch_day);
                    EditText time = (EditText) dialog.findViewById(R.id.sch_time);
                    forschedule forschedule = new forschedule();
                    forschedule.setId(id);
                    forschedule.setShc_name(name.getText().toString());
                    forschedule.setMonth(Integer.valueOf(month.getText().toString()));
                    forschedule.setDay(Integer.valueOf(day.getText().toString()));
                    forschedule.setTime(Integer.valueOf(time.getText().toString()));
                    Gson gson = new Gson();
                    String json = gson.toJson(forschedule, forschedule.class);

                    HttpUrl.Builder urlbuilder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/addschedule").newBuilder();
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
                                System.out.println("success");
                                update();

                            }
                            else if (myResponse.equals("\"FF\"\n")){
                                System.out.println("시간이 겹칩니다..");
                            }
                            else{
                                System.out.println("실패");
                            }
                        }

                    });

                }

            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();

        });

        return view;
    }

    public void update(){
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
                Meinfo = gson.fromJson(myResponse, Person.class);
            }
        });

    }
}