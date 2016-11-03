package com.example.wangmiao.teamdemo01.adapter;

import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangmiao.teamdemo01.R;
import com.example.wangmiao.teamdemo01.domain.Home_Clothes;
import com.example.wangmiao.teamdemo01.fragment.Fragment_Home;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kzj on 2016/11/3.
 */

    public  class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        private final int TYPE_HEAD = 1;
        private final int TYPE_ITEM = 0;
        //private final int TYPE_GRID=3;
        private android.view.View headerView;
    private Fragment_Home fragment_home;
    private List<Home_Clothes.RowsEntity> clothes;

    public HomeAdapter(Fragment_Home fragment_home, List<Home_Clothes.RowsEntity> clothes) {
        this.fragment_home=fragment_home;
        this.clothes=clothes;
    }

    public interface OnItemClickListener{

        }


        public HomeAdapter(android.view.View headerView) {
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
                    view = LayoutInflater.from(fragment_home.getActivity()).inflate(R.layout.home_item, null);
            }

            MyViewHolder myViewHolder = new MyViewHolder(view, viewType);
            //System.out.println("myViewHolder=" + myViewHolder);
            return myViewHolder;
        }

        @Override
        public int getItemViewType(int position) {
            int type = TYPE_ITEM;
            //System.out.println("position=" + position);
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
                    //System.out.println("position=" + position);
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
                        Picasso.with(fragment_home.getActivity())
                                .load(rowsEntity.getProductImg())
                                .resize(120, 100)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(holder.imageView_home);
                    } else {
                        Toast.makeText(fragment_home.getActivity(), "网络加载失败", Toast.LENGTH_SHORT).show();
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


