package com.example.wangmiao.teamdemo01.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wangmiao.teamdemo01.R;
import com.example.wangmiao.teamdemo01.fragment.InFragment_one;
import com.example.wangmiao.teamdemo01.fragment.InFragment_three;
import com.example.wangmiao.teamdemo01.fragment.InFragment_two;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager_in;
    private ImageView imageView_in;
    private LinearLayout linerLayout_icon;

    /**
     * 声明存储图标的ImageView数组
     */
    private ImageView[] imageView_icons;

    private List<Fragment> fragments;

    private int prePosition;

    private Button button_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    private void initData() {

        this.fragments = new ArrayList<>();
        InFragment_one inFragment_one = new InFragment_one();
        InFragment_two inFragment_two = new InFragment_two();
        InFragment_three inFragment_three = new InFragment_three();

        fragments.add(inFragment_one);
        fragments.add(inFragment_two);
        fragments.add(inFragment_three);

        this.viewPager_in.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        //linearLayout_icons.getChildCount():获取线性布局中的子元素个数i
        int count=this.linerLayout_icon.getChildCount();
        this.imageView_icons=new ImageView[count];
        for(int i=0;i<count;i++){
            ImageView imageView_icon=(ImageView) this.linerLayout_icon.getChildAt(i);
            //给ImageView设置一个标志值
            //imageView_icon.setTag(i);

            imageView_icon.setId(i);
            imageView_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // int currentItem=Integer.parseInt(v.getTag().toString());
                    //设置ViewPager切换到指定页面
                    //viewPager.setCurrentItem(currentItem);

                    viewPager_in.setCurrentItem(v.getId());
                }
            });

            this.imageView_icons[i]=imageView_icon ;
        }

    }

    /**
     * 自定义适配器对象
     *
     * @author dell
     *
     */
    private final class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return fragments.size();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView_in.setVisibility(ImageView.GONE);
                    }
                });
            }
        }).start();
    }

    private void initView() {
        this.viewPager_in = (ViewPager) this.findViewById(R.id.viewPager_in);
        this.imageView_in = (ImageView) this.findViewById(R.id.imageView_in);
        this.linerLayout_icon = (LinearLayout) this.findViewById(R.id.linerLayout_icon);
        //给ViewPager注册滑动监听器
        this.viewPager_in.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当ViewPager滚动过程中自动调用的方法
             * @param position 当前页面的索引位
             * @param positionOffset 滚动的偏移量,[0-1) 一旦这个值大于0.5后才可能滑动目标页面,否则会回退到之前的页面
             * @param positionOffsetPixels 以像素为单位指定滚动的距离
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                System.out.println("===onPageScrolled(int position="+position+", float positionOffset="+positionOffset+", int positionOffsetPixels="+positionOffsetPixels+")===");
            }

            /**
             * 当滚动到目标页面后自动调用的方法
             * @param position 新页面的索引位
             */
            @Override
            public void onPageSelected(int position) {
                //将目标页面的图片设置为彩色图标
                imageView_icons[position].setImageResource(R.drawable.radio_on);
                //将之前页面的图标还原成灰色图标
                imageView_icons[prePosition].setImageResource(R.drawable.radio_off);
                //将当前位置作为之前页面的位置
                prePosition=position;
                System.out.println("===onPageSelected(int position="+position+")==");
            }

            /**
             * 当页面的滚动状态发生变化时自动调用的方法
             * @param state 滚动状态
             *               ViewPager.SCROLL_STATE_IDLE=0:当前ViewPager处于空闲状态
             *              ViewPager.SCROLL_STATE_DRAGGING=1:当前ViewPager正在被用户拖拽
             *              ViewPager.SCROLL_STATE_SETTLING=2:表示ViewPager 准备滑动到最终位置的过程中
             *
             */
            @Override
            public void onPageScrollStateChanged(int state) {

                System.out.println("===onPageScrollStateChanged(int state="+state+")==");
            }
        });
    }
}
