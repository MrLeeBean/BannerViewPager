package com.example.zhpan.circleviewpager.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.indicator.BaseIndicatorView;
import com.zhpan.bannerview.indicator.CircleIndicatorView;
import com.zhpan.bannerview.utils.DpUtils;
import com.zhpan.idea.utils.ToastUtils;

public class PageStyleActivity extends BaseDataActivity {
    private BannerViewPager<Integer, ImageResourceViewHolder> mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_style);
        mViewPager = findViewById(R.id.banner_view);
        mViewPager
                .setPageMargin(DpUtils.dp2px(10))
                .setRevealWidth(DpUtils.dp2px(10))
                .setHolderCreator(() -> new ImageResourceViewHolder(DpUtils.dp2px(5)))
                .setIndicatorColor(Color.parseColor("#935656"), Color.parseColor("#FF4C39"))
                .setOnPageClickListener(position -> ToastUtils.show("position:" + position))
                .setInterval(3000);
        initRadioGroup();
    }

    private void setupBanner(@APageStyle int pageStyle) {
        mViewPager
                .setPageStyle(pageStyle)
                .create(mDrawableList);
    }

    private void initRadioGroup() {
        RadioGroup radioGroupStyle = findViewById(R.id.rg_indicator_style);
        radioGroupStyle.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_multi_page:
                    setupBanner(PageStyle.MULTI_PAGE);
                    break;
                case R.id.rb_multi_page_scale:
                    setupBanner(PageStyle.MULTI_PAGE_SCALE);
                    break;
                case R.id.rb_multi_page_overlap:
                    setupOverlapBanner();
                    break;
            }
        });
        RadioButton radioButton = findViewById(R.id.rb_multi_page);
        radioButton.performClick();
    }

    private void setupOverlapBanner() {
        mViewPager
                .setIndicatorVisibility(View.GONE)
                .setPageStyle(PageStyle.MULTI_PAGE_OVERLAP)
                .setIndicatorView(setupIndicatorView())
                .create(mDrawableList);
    }

    private BaseIndicatorView setupIndicatorView() {
        CircleIndicatorView indicatorView = findViewById(R.id.indicator_view);
        indicatorView.setCheckedColor(Color.parseColor("#935656"));
        indicatorView.setNormalColor(Color.parseColor("#FF4C39"));
        return indicatorView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewPager.stopLoop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager.startLoop();
    }
}