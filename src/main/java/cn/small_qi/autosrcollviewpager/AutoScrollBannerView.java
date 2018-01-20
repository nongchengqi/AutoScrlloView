package cn.small_qi.autosrcollviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by chengqi_nong on 2018/1/12.
 */

public class AutoScrollBannerView extends FrameLayout {
    private AutoScrollViewPager mScrollViewPager;
    private AutoScrollViewIndicator mScrollViewIndicator;
    public AutoScrollViewPager getScrollViewPager() {
        return mScrollViewPager;
    }

    public AutoScrollBannerView(@NonNull Context context) {
        this(context, null);
    }

    public AutoScrollBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public AutoScrollBannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScrollViewPager = new AutoScrollViewPager(getContext());
        mScrollViewIndicator  =new AutoScrollViewIndicator(getContext());
        addView(mScrollViewPager);
        addView(mScrollViewIndicator);

        mScrollViewPager.setLoadFinishListener(new AutoScrollViewPager.OnDateLoadFinish() {
            @Override
            public void loadFinish(int num) {
                mScrollViewIndicator.setCircleNum(num);
            }
        });
        mScrollViewPager.setOnPageChangeListener(new AutoScrollViewPager.OnPageChangeListenerForAutoScrollViewPager() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mScrollViewIndicator.setSelect(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }






}
