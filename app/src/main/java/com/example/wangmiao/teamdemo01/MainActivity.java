package com.example.wangmiao.teamdemo01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "梅儿子吃屎", Toast.LENGTH_SHORT).show();
        System.out.println("第一次提交的代码!!!");
        System.out.println("第二次提交的代码！");
        System.out.println("第三次提交!!!");
        System.out.println("kzj提交");
        System.out.println("柯志杰的梅霸霸提交");
    }
}
