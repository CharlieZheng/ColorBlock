package com.peng.colorblock.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.peng.colorblock.R;

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
    }public void setPosition(Position position) {
        this.position = position;
    }


    public Block(Context context) {
        super(context);
        init();
    }

    public Block(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Block(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setMaskFilter(new BlurMaskFilter(RADIUS_BLURMASKFILTER, BlurMaskFilter.Blur.NORMAL));

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (status == Status.on) {
//                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "", 0, 360 * 4);
//                    view.setVisibility(View.GONE);
                    Animation.AnimationListener listener = new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    };
                    RotateAnimation rotateAnimation = new RotateAnimation(0, 360 * 4, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                    rotateAnimation.setDuration(1000);
                    rotateAnimation.setFillAfter(false);
                    rotateAnimation.setAnimationListener(listener);
                    view.startAnimation(rotateAnimation);


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
        int x,y;

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
