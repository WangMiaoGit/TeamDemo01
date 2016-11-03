package com.example.wangmiao.teamdemo01.fragment;


import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangmiao.teamdemo01.R;
import com.example.wangmiao.teamdemo01.Utils.NetUtils;
import com.example.wangmiao.teamdemo01.domain.Home_Clothes;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewPager.OnPageChangeListener {
    private SwipeRefreshLayout swapRefreshLayout;
    private RecyclerView recyclerView;
    private final String RECYCLE_PATH = "http://www.1zhebaoyou.com/apptools/productlist.aspx?act=getproductlist&pages=1&bc=0&sc=0&sorts=&channel=0&ckey=&daynews=&lprice=0&hprice=0&tbclass=0&actid=0&brandid=0&predate=2016-10-31+17%3A07%3A05&index=1&v=35";
    private final String VIEWPAGER_PATH="http://www.1zhebaoyou.com/apptools/indexad.aspx?v=35";
    private final String IMAGEVIEW_PATH="http://www.1zhebaoyou.com/apptools/app.aspx";
    private MyAdapter myAdapter;
    private List<Home_Clothes.RowsEntity> clothes;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private List<ImageView> imageViews_three=new ArrayList<>();
    private List<ImageView> imageViews_icon=new ArrayList<>();
    private LinearLayout linearLayout_icons;
    private int prePosition;
    private ImageView imageView_super;
    private ImageView imageView_food;
    private ImageView imageView_round;
    private ImageView[] imageViews=new ImageView[5];
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ImageView imageView= (ImageView) msg.obj;
            String imageUrl = msg.getData().getString("url");
            if(imageView!=null){
                Picasso.with(getActivity()).load(imageUrl).placeholder(R.mipmap.ic_launcher)
                        .into(imageView);
            }
            viewPagerAdapter.notifyDataSetChanged();
            Picasso.with(getActivity()).load(msg.getData().getString("url2")).into(imageViews_three.get(msg.arg1));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        this.swapRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swapRefreshLayout);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        clothes = new ArrayList<>();
        swapRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.home_headview, null);
        this.myAdapter = new MyAdapter(headerView);
        recyclerView.setAdapter(myAdapter);
        /*getDataFromNet(RECYCLE_PATH);
        myAdapter.notifyDataSetChanged();*/
        this.viewPager= (ViewPager) headerView.findViewById(R.id.viewPager);
        this.linearLayout_icons= (LinearLayout) headerView.findViewById(R.id.linearLayout_icons);
        this.imageView_food= (ImageView) headerView.findViewById(R.id.imageView_food);
        this.imageView_round= (ImageView) headerView.findViewById(R.id.imageView_round);
        this.imageView_super= (ImageView) headerView.findViewById(R.id.imageView_super);
        imageViews_three.add(imageView_super);
        imageViews_three.add(imageView_food);
        imageViews_three.add(imageView_round);
        setImageView();
        this.viewPagerAdapter=new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        for(int i=0;i<linearLayout_icons.getChildCount();i++){
            ImageView imageView= (ImageView) linearLayout_icons.getChildAt(i);
            imageViews_icon.add(imageView);
            imageView.setId(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(v.getId());
                }
            });
        }


    }

    private void setImageView() {
        getDataFromNet(IMAGEVIEW_PATH);
    }

    @Override
    public void onRefresh() {
        getDataFromNet(RECYCLE_PATH);
        getDataFromNet(VIEWPAGER_PATH);
        getDataFromNet(IMAGEVIEW_PATH);
        myAdapter.notifyDataSetChanged();
        viewPagerAdapter.notifyDataSetChanged();
        swapRefreshLayout.setRefreshing(false);
    }

    private void getDataFromNet(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStreamFromNet=null;
                String data=null;
                switch (path){
                   case IMAGEVIEW_PATH:
                       inputStreamFromNet = NetUtils.getInputStreamFromNet(path);
                       if(inputStreamFromNet!=null){
                           data= NetUtils.inputStreamToString(inputStreamFromNet);
                           JSONArray jsonArray = null;
                           try {
                               jsonArray = new JSONArray(data);
                               System.out.println("size="+jsonArray.length());
                               for(int i=0;i<jsonArray.length();i++){
                                   JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                                   String imgUrl = jsonObject.getString("imgUrl");
                                   Message message = Message.obtain(handler);
                                   Bundle bundle = new Bundle();
                                   bundle.putString("url2",imgUrl);
                                   message.arg1=i;
                                   message.setData(bundle);
                                   message.sendToTarget();

                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                       }else {
                           Looper.prepare();
                           Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                           Looper.loop();
                       }
                       break;
                   case VIEWPAGER_PATH:
                       inputStreamFromNet = NetUtils.getInputStreamFromNet(path);
                       if(inputStreamFromNet!=null){
                           data= NetUtils.inputStreamToString(inputStreamFromNet);
                           try {
                               JSONObject jsonObject = new JSONObject(data);
                               JSONArray jsonArray = jsonObject.getJSONArray("adList");
                               //System.out.println("jsonArray.size="+jsonArray.length());
                               for (int i=0;i<jsonArray.length();i++){
                                   JSONObject jsonObject1= (JSONObject) jsonArray.get(i);
                                   String imgUrl = jsonObject1.getString("imgUrl");
                                   ImageView imageView = new ImageView(getActivity());
                                   imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                   imageViews[i]=imageView;
                                   Message obtain = Message.obtain(handler);
                                   Bundle bundle = new Bundle();
                                   bundle.putString("url",imgUrl);
                                   obtain.setData(bundle);
                                   obtain.obj=imageView;
                                   obtain.sendToTarget();
                                   //System.out.println("imageview="+imageView);
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }else {
                           Looper.prepare();
                           Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                           Looper.loop();
                       }
                       break;
                   case RECYCLE_PATH:
                       inputStreamFromNet = NetUtils.getInputStreamFromNet(path);
                       if (inputStreamFromNet != null) {
                           data = NetUtils.inputStreamToString(inputStreamFromNet);
                           //System.out.println("data="+data);
                           Gson gson = new Gson();
                           Home_Clothes home_clothes = gson.fromJson(data, Home_Clothes.class);
                           clothes = home_clothes.getRows();
                           //System.out.println("clothes="+clothes);
                       } else {
                           Looper.prepare();
                           Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                           Looper.loop();
                       }
                       break;
               }
            }
        }).start();

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        imageViews_icon.get(position).setBackgroundResource(R.drawable.dot);
        imageViews_icon.get(prePosition).setImageResource(R.drawable.dot_1);
        prePosition=position;


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ViewPagerAdapter extends PagerAdapter{
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position]);
            return imageViews[position];
        }

        @Override
        public int getCount() {
            System.out.println("size="+imageViews.length);
            return imageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private final int TYPE_HEAD = 1;
        private final int TYPE_ITEM = 0;
        private android.view.View headerView;

        public MyAdapter(android.view.View headerView) {
            this.headerView = headerView;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case TYPE_HEAD:
                    view = headerView;
                    break;
                case TYPE_ITEM:
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.home_item, null);
            }
            MyViewHolder myViewHolder = new MyViewHolder(view, viewType);
            System.out.println("myViewHolder=" + myViewHolder);
            return myViewHolder;
        }

        @Override
        public int getItemViewType(int position) {
            int type = TYPE_ITEM;
            System.out.println("position=" + position);
            if (position == 0) {
                type = TYPE_HEAD;
            } else {
                type = TYPE_ITEM;
            }
            return type;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            int itemViewType = getItemViewType(position);
            switch (itemViewType) {
                case TYPE_HEAD:
                    /*viewPagerAdapter=new ViewPagerAdapter();
                    holder.viewPager.setAdapter(viewPagerAdapter);*/
                    break;
                case TYPE_ITEM:
                    Home_Clothes.RowsEntity rowsEntity = clothes.get(position);
                    System.out.println("position=" + position);
                    //System.out.println("rowsEntity="+rowsEntity);
                    if (rowsEntity != null) {
                        //System.out.println("name=" + rowsEntity.getName());
                        //System.out.println("textView_title=" + holder.textView_title);
                        DecimalFormat decimalFormat = new DecimalFormat("#.##");
                        holder.textView_title.setText(rowsEntity.getName());
                        holder.textView_saleTotal.setText("已售" + rowsEntity.getSaleTotal() + "件");
                        holder.textView_oldPrice.setText("原价¥" + decimalFormat.format(rowsEntity.getOldPrice()));
                        holder.textView_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.textView_newPrice.setText("¥" + (int) rowsEntity.getNewPrice());
                        holder.textView_discount.setText(decimalFormat.format(rowsEntity.getDiscount()) + "折");
                        int isBaoYou = rowsEntity.getIsBaoYou();
                        if (isBaoYou == 1) {
                            holder.textView_isBaoyou.setText("包邮");
                        } else {
                            holder.textView_isBaoyou.setText("不包邮");
                        }
                        Picasso.with(getActivity())
                                .load(rowsEntity.getProductImg())
                                .resize(120, 100)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(holder.imageView_home);
                    } else {
                        Toast.makeText(getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


        }

        @Override
        public int getItemCount() {
            return clothes.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView_home;
            private TextView textView_title;
            private TextView textView_discount;
            private TextView textView_isBaoyou;
            private TextView textView_newPrice;
            private TextView textView_oldPrice;
            private TextView textView_saleTotal;
            private ViewPager viewPager;


            public MyViewHolder(View itemView, int viewType) {
                super(itemView);
                switch (viewType) {
                    case TYPE_HEAD:
                        this.viewPager= (ViewPager) itemView.findViewById(R.id.viewPager);
                        break;
                    case TYPE_ITEM:
                        //System.out.println("view=" + itemView);
                        this.imageView_home = (ImageView) itemView.findViewById(R.id.imageView_home);
                        this.textView_title = (TextView) itemView.findViewById(R.id.textView_title);
                        this.textView_discount = (TextView) itemView.findViewById(R.id.textView_discount);
                        this.textView_isBaoyou = (TextView) itemView.findViewById(R.id.textView_isBaoyou);
                        this.textView_newPrice = (TextView) itemView.findViewById(R.id.textView_newPrice);
                        this.textView_oldPrice = (TextView) itemView.findViewById(R.id.textView_oldPrice);
                        this.textView_saleTotal = (TextView) itemView.findViewById(R.id.textView_saleTotal);
                        break;
                }

            }
        }
    }
}
