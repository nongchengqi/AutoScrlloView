package cn.small_qi.autosrcollviewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by chengqi_nong on 2018/1/20.
 */

public class AutoScrollViewIndicator extends View{
    private Paint mStaticPaint;
    private Paint mSelectPaint;
    private int unSelectColor=Color.parseColor("#dedede");
    private int SelectColor=Color.parseColor("#ff0000");
    private int circleNum;//圆数量
    private int radius=10;//圆半径
    private int select=1;//默认选中
    private int padding=5;//每个圆之间的距离
    private int top=10;//圆的位置距离底部 是高度的几分之一
    public int getCircleNum() {
        return circleNum;
    }
    public void setCircleNum(int circleNum) {
        this.circleNum = circleNum;
        invalidate();
    }
    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public int getSelect() {
        return select;
    }
    public void setSelect(int select) {
        this.select = select;
        invalidate();
    }
    public AutoScrollViewIndicator(Context context) {
        super(context);
        init();
    }
    private void init(){
        mSelectPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mStaticPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mSelectPaint.setStyle(Paint.Style.FILL);
        mStaticPaint.setStyle(Paint.Style.FILL);

        mSelectPaint.setColor(SelectColor);
        mStaticPaint.setColor(unSelectColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStaticCircle(canvas);
        drawSelectCircle(canvas,select);
    }
    private void drawSelectCircle(Canvas canvas, int position) {

        if (position==0){
            position=circleNum-1;
        }else if (position==circleNum+1){
            position=0;
        }else{
            position =position-1;
        }
        int left = getWidth() / 2 - (radius * padding * circleNum) / 2 + radius*2;
        canvas.drawCircle(left + position * padding * radius, getHeight() - getHeight()/top, radius, mSelectPaint);
    }

    private void drawStaticCircle(Canvas canvas) {
        int left = getWidth() / 2 - (radius * padding * circleNum) / 2 + radius*2;
        for (int i = 0; i < circleNum; i++) {
            canvas.drawCircle(left + i * padding * radius, getHeight() - getHeight() /top, radius, mStaticPaint);
        }
    }

}
