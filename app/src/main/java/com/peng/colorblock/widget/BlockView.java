package com.peng.colorblock.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.peng.colorblock.util.ScreenUtil;

/**
 * Created by Hanrong on 2016/10/29.
 */
public class BlockView extends LinearLayout {
    private Context context;
    public BlockView(Context context) {
        super(context);
        init(context);
    }

    public BlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private static final int rowNum = 10;
    /**
     * 列数
     */
    private static final int colNum = 10;

    private int blockSize;
    private float wRatio;
    private float hRatio;
    private void init(Context context) {

        this.context = context;
        wRatio = ScreenUtil.getWidth(context) / 720f;
        hRatio = ScreenUtil.getWidth(context) / 1280f;
        blockSize = (int)( ScreenUtil.getWidth(context)/(float)colNum +0.5f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT );
        lp.gravity = Gravity.CENTER;

        LinearLayout label = new LinearLayout(context);
        label.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams rowLp;
        for (int i = 0; i < rowNum; i++) {
            LinearLayout row = new LinearLayout(context);
            row.setBackgroundColor(Color.WHITE);
            row.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams btnLp;
            for (int j = 0; j < colNum; j++) {
                int btnPadding = (int) (10*wRatio);
                Button btn = new Button(context);
//                btn.setPadding(btnPadding, btnPadding, btnPadding, btnPadding);
                btn.setBackgroundColor(Color.YELLOW);
                btnLp = new LinearLayout.LayoutParams(blockSize, blockSize);
                btnLp.setMargins(btnPadding, btnPadding, btnPadding, btnPadding);
                row.addView(btn, btnLp);

            }
            rowLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, blockSize);
            label.addView(row,rowLp );
        }
        this.removeAllViews();
        addView(label, lp);
        postInvalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureSpec =  MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);setMeasuredDimension(measureSpec, measureSpec);
        for (int i = 0; i < getChildCount(); i ++) {
            measureChild(getChildAt(i), measureSpec, measureSpec);
        }
//init(context);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for(int i = 0; i < childCount; i ++) {
            View item = getChildAt(i);
            item.layout(l, t, r, b);
        }
//        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//init(context);
        super.onDraw(canvas);
    }
}
