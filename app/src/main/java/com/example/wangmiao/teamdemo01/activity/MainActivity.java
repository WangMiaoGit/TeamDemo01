package com.example.wangmiao.teamdemo01.activity;

import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wangmiao.teamdemo01.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager_in;
    private ImageView imageView_in;
    private LinearLayout linerLayout_icon;

    /**
     * 声明存储图标的ImageView数组
     */
    private ImageView[] imageView_icons;

    private int prePosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    private void initData() {

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
