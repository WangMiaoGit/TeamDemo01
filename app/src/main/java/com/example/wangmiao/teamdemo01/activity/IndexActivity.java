package com.example.wangmiao.teamdemo01.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.wangmiao.teamdemo01.R;
import com.example.wangmiao.teamdemo01.fragment.Fragment_Account;
import com.example.wangmiao.teamdemo01.fragment.Fragment_Cart;
import com.example.wangmiao.teamdemo01.fragment.Fragment_Home;
import com.example.wangmiao.teamdemo01.fragment.Fragment_Sale;
import com.example.wangmiao.teamdemo01.fragment.Fragment_Search;

public class IndexActivity extends AppCompatActivity {
    private FragmentTabHost tabhost;
    private String[] tabSpecText={"首页", "品牌特卖", "值得逛", "分类搜索", "个人中心"};
    private Class[] fragmentArray={Fragment_Home.class, Fragment_Sale.class, Fragment_Cart.class, Fragment_Search.class, Fragment_Account.class};
    private int[] tabImages={R.drawable.tab_home,R.drawable.tab_sale,R.drawable.tab_cart,R.drawable.tab_search,R.drawable.tab_account};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        this.tabhost= (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        this.tabhost.setup(this,getSupportFragmentManager(),R.id.frameLayout_content);
        for(int i=0;i<fragmentArray.length;i++){
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(tabSpecText[i]).setIndicator(getTabItemView(i));
            tabhost.addTab(tabSpec,fragmentArray[i],null);

        }

    }

    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);
        TextView textview_title= (TextView) view.findViewById(R.id.textview_title);
        ImageView imageview_icon= (ImageView) view.findViewById(R.id.imageview_icon);
        //System.out.println("text="+tabSpecText[index]);
        //System.out.println("textview_title="+textview_title);
        imageview_icon.setImageResource(tabImages[index]);
        textview_title.setText(tabSpecText[index]);
        return view;
    }
}
