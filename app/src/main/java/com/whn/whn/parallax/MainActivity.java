package com.whn.whn.parallax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ParallaxListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ParallaxListView) findViewById(R.id.parallaxlistview);
        lv.setOverScrollMode(ParallaxListView.OVER_SCROLL_NEVER);

        //获取头布局
        View headerView = View.inflate(getApplicationContext(), R.layout.layout_header, null);
        //将iv传递过去
        ImageView iv = (ImageView) headerView.findViewById(R.id.imageView);
        lv.setImageView(iv);
        //添加头布局
        lv.addHeaderView(headerView);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constant.NAMES));
    }
}
