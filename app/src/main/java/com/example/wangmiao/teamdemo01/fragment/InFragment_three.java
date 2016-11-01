package com.example.wangmiao.teamdemo01.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wangmiao.teamdemo01.R;
import com.example.wangmiao.teamdemo01.activity.IndexActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class InFragment_three extends Fragment {


    public InFragment_three() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_fragment_three, container, false);
        Button button = (Button) view.findViewById(R.id.button_in);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),IndexActivity.class);
                startActivity(intent);
                //关闭当前Activity
                getActivity().finish();
            }
        });
        return view;
    }

}
