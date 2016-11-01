package com.example.wangmiao.teamdemo01.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangmiao.teamdemo01.R;
import com.example.wangmiao.teamdemo01.Utils.NetUtils;

import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swapRefreshLayout;
    private RecyclerView recyclerView;
    private String path="http://www.1zhebaoyou.com/apptools/productlist.aspx?act=getproductlist&pages=1&bc=0&sc=0&sorts=&channel=0&ckey=&daynews=&lprice=0&hprice=0&tbclass=0&actid=0&brandid=0&predate=2016-10-31+17%3A07%3A05&index=1&v=35";
    private MyAdapter myAdapter;


    public Fragment_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        this.swapRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swapRefreshLayout);
        this.recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        swapRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        this.myAdapter=new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onRefresh() {
        getDataFromNet(path);
    }

    private void getDataFromNet(String path) {
        InputStream inputStreamFromNet = NetUtils.getInputStreamFromNet(path);
        String data = NetUtils.inputStreamToString(inputStreamFromNet);



    }
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        private int TYPE_HEAD=1;
        private int TYPE_ITEM=0;
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            int type=TYPE_ITEM;
            if(position==0){
                type=TYPE_HEAD;

            }else {
                type=TYPE_ITEM;
            }
            return type;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
