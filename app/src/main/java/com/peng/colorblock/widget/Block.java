package com.peng.colorblock.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.peng.colorblock.R;
import com.peng.colorblock.util.BezierEvaluator;
import com.peng.colorblock.util.ScreenUtil;

/**
 * Created by Hanrong on 2016/10/30.
 */
public class Block extends View {
    private Color color;
    private Status status;
    private Position position;
    private Paint paint;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        paint.setColor(color.colorId);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public Block(Context context) {
        super(context);
        init(context);
    }

    public Block(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Block(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setClickable(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setMaskFilter(new BlurMaskFilter(RADIUS_BLURMASKFILTER, BlurMaskFilter.Blur.NORMAL));
        int screenWidth = ScreenUtil.getWidth(context);
        int screenHeight = ScreenUtil.getHeight(context);
        float ratio = (screenWidth > screenHeight ? screenWidth : screenHeight) / 1280f;
        start = new PointF(0, 0);
        end = new PointF(50 * ratio, 50 * ratio);
        point1 = new PointF(20 * ratio, 30 * ratio);
        point2 = new PointF(40 * ratio, 60 * ratio);

        final BezierEvaluator evaluator = new BezierEvaluator(point1, point2);

        final Animator animator = AnimatorInflater.loadAnimator(context, R.animator.anim_block_rotate);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (((Block) view).getStatus() == Status.on) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofObject(view, "x", evaluator, start, end);
                    AnimatorSet set = new AnimatorSet();
                    set.play(animator).with(objectAnimator);
                    set.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            view.setVisibility(View.INVISIBLE);
                            super.onAnimationEnd(animation);
                        }
                    });
                }
            }
        });
    }

    private int measuredSize;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measuredSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureSpec = MeasureSpec.makeMeasureSpec(measuredSize, MeasureSpec.EXACTLY);
        setMeasuredDimension(measureSpec, measureSpec);
    }


    private static final int RADIUS_ROUND_RECT = 10;
    private static final float RADIUS_BLURMASKFILTER = 0.5f;

    private PointF start;
    private PointF end;
    private PointF point1;
    private PointF point2;

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(0, 0, measuredSize, measuredSize);
        canvas.drawRoundRect(rectF, RADIUS_ROUND_RECT, RADIUS_ROUND_RECT, paint);
        super.onDraw(canvas);
    }

    /**
     * 脱落后设置状态
     */
    private void off() {
        setStatus(Status.off);
    }

    public static class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    enum Status {
        /**
         * 还在的
         */
        on,
        /**
         * 脱落的
         */
        off
    }

    enum Color {
        yellow(R.color.yellow), blue(R.color.blue), red(R.color.red), purple(R.color.purple), green(R.color.green),;
        public int colorId;

        Color(int colorId) {
            this.colorId = colorId;
        }
    }
}
