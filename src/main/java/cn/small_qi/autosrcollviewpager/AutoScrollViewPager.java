package cn.small_qi.autosrcollviewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengqi_nong on 2018/1/12.
 */
public class AutoScrollViewPager extends ViewPager {
    private List<Bitmap> imgBitmapList;
    private List<String> imgUrlList;
    private List<Drawable> imgDrawableList;
    private List<ImageView> mImageViewList;
    private boolean isTouch;
    private Runnable scrollAction;
    private boolean isScroll;
    private boolean isAutoScroll = true;

    private OnDateLoadFinish mLoadFinishListener;

    public void setLoadFinishListener(OnDateLoadFinish loadFinishListener) {
        mLoadFinishListener = loadFinishListener;
    }

    /**
     * 如果使用到自动滚动功能但是想实现{@link #addOnPageChangeListener(OnPageChangeListener)}
     * 请使用此时方法，因为{@link #addOnPageChangeListener(OnPageChangeListener)}已经用作处理其他事情。
     * 切记~
     *
     * @param onPageChangeListener
     */
    private OnPageChangeListenerForAutoScrollViewPager onPageChangeListener;

    public void setOnPageChangeListener(OnPageChangeListenerForAutoScrollViewPager onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public AutoScrollViewPager(@NonNull Context context) {
        this(context, null);
    }

    public AutoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initList();

    }

    private void initList() {
        imgBitmapList = new ArrayList<>();
        imgUrlList = new ArrayList<>();
        imgDrawableList = new ArrayList<>();
        mImageViewList = new ArrayList<>();
    }

    public void setImgBitmapList(List<Integer> buffList) {
        mImageViewList.clear();
        if (buffList.size() == 0) {
            return;
        }
        if (buffList.size() == 1) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(buffList.get(0)).into(iv);
            mImageViewList.add(iv);
            buffList.clear();
        } else {
            ImageView left = new ImageView(getContext());
            left.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(buffList.size() - 1).into(left);
            mImageViewList.add(left);

            for (int i = 0; i < buffList.size(); i++) {
                ImageView iv = new ImageView(getContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(getContext()).load(buffList.get(i)).into(iv);
                mImageViewList.add(iv);
            }

            ImageView right = new ImageView(getContext());
            right.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(buffList.get(0)).into(right);
            mImageViewList.add(right);
        }
        configPagerParams();
    }

    public void setImgUrlList(List<String> buffList) {
        mImageViewList.clear();
        if (buffList.size() == 0) {
            return;
        }
        if (buffList.size() == 1) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(buffList.get(0)).into(iv);
            mImageViewList.add(iv);
            buffList.clear();
        } else {
            ImageView left = new ImageView(getContext());
            left.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(buffList.get(buffList.size() - 1)).into(left);
            mImageViewList.add(left);

            for (int i = 0; i < buffList.size(); i++) {
                ImageView iv = new ImageView(getContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(getContext()).load(buffList.get(i)).into(iv);
                mImageViewList.add(iv);
            }

            ImageView right = new ImageView(getContext());
            right.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(buffList.get(0)).into(right);
            mImageViewList.add(right);
        }
        configPagerParams();
    }

    public void setImgDrawableList(List<Drawable> buffList) {
        mImageViewList.clear();
        if (buffList.size() == 0) {
            return;
        }
        if (buffList.size() == 1) {
            ImageView iv = new ImageView(getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageDrawable(buffList.get(0));
            mImageViewList.add(iv);
            buffList.clear();
        } else {
            ImageView left = new ImageView(getContext());
            left.setImageDrawable(buffList.get(buffList.size() - 1));
            left.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageViewList.add(left);

            for (int i = 0; i < buffList.size(); i++) {
                ImageView iv = new ImageView(getContext());
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setImageDrawable(buffList.get(i));
                mImageViewList.add(iv);
            }


            ImageView right = new ImageView(getContext());
            right.setScaleType(ImageView.ScaleType.FIT_XY);
            right.setImageDrawable(buffList.get(0));
            mImageViewList.add(right);
        }
        configPagerParams();
    }

    public List<Drawable> getImgDrawableList() {
        return imgDrawableList;
    }

    public List<Bitmap> getImgBitmapList() {
        return imgBitmapList;
    }

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    private void configPagerParams() {
        if (mLoadFinishListener != null) {
            mLoadFinishListener.loadFinish(mImageViewList.size() - 2 > 0 ? mImageViewList.size() - 2 : mImageViewList.size());
        }

        setAdapter(new AutoScrollPagerAdapter(mImageViewList));
        setCurrentItem(1, false);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                if (positionOffset == 0) {
                    isTouch = false;
                } else {
                    isTouch = true;
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
                if (isAutoScroll) {
                    if (mImageViewList.size() != 1) {
                        if (position == 0) {
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setCurrentItem(mImageViewList.size() - 2, false);
                                }
                            }, 800);
                        }
                        if (position == mImageViewList.size() - 1) {
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setCurrentItem(1, false);
                                }
                            }, 800);

                        }
                    }
                } else {
                    if (mImageViewList.size() != 1) {
                        if (position == 0) {
                            setCurrentItem(mImageViewList.size() - 2, false);
                        }
                        if (position == mImageViewList.size() - 1) {
                            setCurrentItem(1, false);

                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    public void startAutoScroll() {
        if (scrollAction == null) {
            scrollAction = new Runnable() {
                @Override
                public void run() {
                    if (!isTouch) {
                        setCurrentItem(getCurrentItem() + 1, true);
                    }
                    postDelayed(scrollAction, 3000);
                }
            };
        }
        isScroll = true;
        postDelayed(scrollAction, 3000);
    }

    public void stopAutoScroll() {
        setAutoScroll(false);
    }

    public boolean isAutoScroll() {
        return isAutoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        isAutoScroll = autoScroll;
        if (isAutoScroll && !isScroll) {
            startAutoScroll();
        }
        if (!isAutoScroll && scrollAction != null) {
            removeCallbacks(scrollAction);
            scrollAction = null;
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == GONE && isScroll) {
            removeCallbacks(scrollAction);
            isScroll = false;
        } else if (visibility == VISIBLE && !isScroll && isAutoScroll) {
            startAutoScroll();
        }

    }

    class AutoScrollPagerAdapter extends PagerAdapter {
        private List<ImageView> imageViews;

        public AutoScrollPagerAdapter(List<ImageView> list) {
            imageViews = list;
        }

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public ImageView instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(imageViews.get(position));
        }
    }

    //滑动回调接口 和官方的接口一致
    interface OnPageChangeListenerForAutoScrollViewPager {
        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position             Position index of the first page currently being displayed.
         *                             Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        void onPageSelected(int position);

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         *
         * @param state The new scroll state.
         * @see ViewPager#SCROLL_STATE_IDLE
         * @see ViewPager#SCROLL_STATE_DRAGGING
         * @see ViewPager#SCROLL_STATE_SETTLING
         */
        void onPageScrollStateChanged(int state);
    }


    public interface OnDateLoadFinish {
        void loadFinish(int num);
    }
}
