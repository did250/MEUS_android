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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {

    Button signup;
    Button cancel;
    EditText id;
    EditText pw1;
    EditText pw2;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup = findViewById(R.id.btnsignup);
        cancel = findViewById(R.id.btncancel);
        id = findViewById(R.id.textID);
        pw1 = findViewById(R.id.textpw1);
        pw2 = findViewById(R.id.textpw2);
        name = findViewById(R.id.textnames);
        signup.setOnClickListener(view -> {
            if (pw1.getText().toString().equals(pw2.getText().toString())){
                OkHttpClient client = new OkHttpClient();
                Person person = new Person();
                person.setId(id.getText().toString());
                person.setPw(pw1.getText().toString());
                person.setName(name.getText().toString());
                Gson gson = new Gson();
                String json = gson.toJson(person, Person.class);
                HttpUrl.Builder builder = HttpUrl.parse("https://41f4oo0drh.execute-api.ap-northeast-2.amazonaws.com/dev/adduser").newBuilder();
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

                        if (myResponse.equals("\"F\"\n")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            System.out.println("id 존재");
                        }
                    }
                });


            }
            else {
                System.out.println("비밀번호와 비밀번호 확인 일치하지 않음.");
            }

        });

        cancel.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}