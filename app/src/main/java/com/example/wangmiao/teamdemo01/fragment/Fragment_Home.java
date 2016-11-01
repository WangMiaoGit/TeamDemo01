package com.example.wangmiao.teamdemo01.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangmiao.teamdemo01.R;
import com.example.wangmiao.teamdemo01.Utils.NetUtils;
import com.example.wangmiao.teamdemo01.domain.Home_Clothes;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swapRefreshLayout;
    private RecyclerView recyclerView;
    private String path="http://www.1zhebaoyou.com/apptools/productlist.aspx?act=getproductlist&pages=1&bc=0&sc=0&sorts=&channel=0&ckey=&daynews=&lprice=0&hprice=0&tbclass=0&actid=0&brandid=0&predate=2016-10-31+17%3A07%3A05&index=1&v=35";
    private MyAdapter myAdapter;
    private List<Home_Clothes.RowsEntity> clothes;


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
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.home_headview, null);
        this.myAdapter=new MyAdapter(headerView);
        recyclerView.setAdapter(myAdapter);


    }

    @Override
    public void onRefresh() {
        getDataFromNet(path);
    }

    private void getDataFromNet(String path) {
        InputStream inputStreamFromNet = NetUtils.getInputStreamFromNet(path);
        String data = NetUtils.inputStreamToString(inputStreamFromNet);
        Gson gson = new Gson();
        Home_Clothes home_clothes = gson.fromJson(data, Home_Clothes.class);
        clothes = home_clothes.getRows();

    }
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        private final int TYPE_HEAD=1;
        private final int TYPE_ITEM=0;
        private View headerView;

        public MyAdapter(View headerView) {
            this.headerView=headerView;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType){
                case TYPE_HEAD:
                    view=headerView;
                    break;
                case TYPE_ITEM:
                    view=LayoutInflater.from(getActivity()).inflate(R.layout.home_item,null);
            }
            MyViewHolder myViewHolder = new MyViewHolder(view,viewType);
            return myViewHolder;
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
            Home_Clothes.RowsEntity rowsEntity = clothes.get(position);
            holder.textview_title.setText(rowsEntity.getName());
            holder.textView_saleTotal.setText("已售"+rowsEntity.getSaleTotal()+"件");
            holder.textView_oldPrice.setText("原价¥"+rowsEntity.getOldPrice());
            holder.textView_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textView_newPrice.setText("¥"+(int) rowsEntity.getNewPrice());
            holder.textView_discount.setText(rowsEntity.getDiscount()+"折");
            int isBaoYou = rowsEntity.getIsBaoYou();
            if(isBaoYou==1){
                holder.textView_isBaoyou.setText("包邮");
            }else {
                holder.textView_isBaoyou.setText("不包邮");
            }
            Picasso.with(getActivity())
                    .load(rowsEntity.getProductImg())
                    .resize(120,100)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imageView_home);

        }

        @Override
        public int getItemCount() {
            return clothes.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageView_home;
            private TextView  textview_title;
            private TextView  textView_discount;
            private TextView  textView_isBaoyou;
            private TextView  textView_newPrice;
            private TextView  textView_oldPrice;
            private TextView  textView_saleTotal;

            public MyViewHolder(View itemView, int viewType) {
                super(itemView);
                switch (viewType){
                    case TYPE_HEAD:
                        break;
                    case TYPE_ITEM:
                        this.imageView_home= (ImageView) itemView.findViewById(R.id.imageView_home);
                        this.textview_title= (TextView) itemView.findViewById(R.id.textview_title);
                        this.textView_discount= (TextView) itemView.findViewById(R.id.textView_discount);
                        this.textView_isBaoyou= (TextView) itemView.findViewById(R.id.textView_isBaoyou);
                        this.textView_newPrice= (TextView) itemView.findViewById(R.id.textView_newPrice);
                        this.textView_oldPrice= (TextView) itemView.findViewById(R.id.textView_oldPrice);
                        this.textView_saleTotal= (TextView) itemView.findViewById(R.id.textView_saleTotal);
                        break;
                }

            }
        }
    }
}
