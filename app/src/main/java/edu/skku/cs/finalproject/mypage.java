package edu.skku.cs.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class mypage extends Fragment {

    TextView name;
    TextView id;
    Person info;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        name = view.findViewById(R.id.textname);
        id = view.findViewById(R.id.textnum);
        info = (Person) ((SecondActivity)getContext()).info;
        name.setText(info.getName());
        id.setText("ID : " + info.getId());
        return view;
    }
}