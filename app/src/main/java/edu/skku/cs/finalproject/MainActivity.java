package edu.skku.cs.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    Button login;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.editTextID);
        pw = findViewById(R.id.editTextPW);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);


        login.setOnClickListener(view -> {
            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder builder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/login").newBuilder();
            builder.addQueryParameter("id", id.getText().toString());
            builder.addQueryParameter("pw", pw.getText().toString());

            String url = builder.build().toString();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final String myResponse = response.body().string();

                    if (myResponse.equals("\"T\"\n")){

                        OkHttpClient client2 = new OkHttpClient();
                        HttpUrl.Builder builder2 = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/getuser").newBuilder();
                        builder2.addQueryParameter("id", id.getText().toString());

                        String url2 = builder2.build().toString();
                        Request request2 = new Request.Builder().url(url2).build();

                        client2.newCall(request2).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response2) throws IOException {
                                final String myResponse = response2.body().string();
                                Gson gson = new GsonBuilder().create();
                                final Person info = gson.fromJson(myResponse, Person.class);

                                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                                intent.putExtra("data", info);
                                startActivity(intent);
                            }
                        });

                    }
                    else{
                        System.out.println("id pw 확인하세");
                        System.out.println(myResponse);
                    }
                }
            });

        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });
    }
}