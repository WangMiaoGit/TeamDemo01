package com.example.wangmiao.teamdemo01.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.wangmiao.teamdemo01.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeItemActivity extends AppCompatActivity {


    @BindView(R.id.imageView_back)
    ImageView imageView_back;
    @BindView(R.id.imageView_share)
    ImageView imageView_share;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_item);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String url = getIntent().getStringExtra("url");
        if (getSupportActionBar().isShowing()) {
            getSupportActionBar().hide();
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }

    @OnClick({R.id.imageView_back, R.id.imageView_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_back:
                this.finish();
                break;
            case R.id.imageView_share:
                break;
        }
    }
}
