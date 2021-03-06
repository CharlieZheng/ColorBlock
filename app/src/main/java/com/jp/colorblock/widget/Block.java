package com.jp.colorblock.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.jp.colorblock.R;
import com.jp.colorblock.util.BezierEvaluator;
import com.jp.colorblock.util.ScreenUtil;

public class Block extends View {
    private static final int RADIUS_ROUND_RECT = 10;
    private static final float RADIUS_BLUR_MASK_FILTER = 0.5f;
    private RectF rectF = new RectF();
    private Context context;
    private Status status;
    private Position position;
    private Paint paint;
    private PointF measuredSize = new PointF();
    private PointF start;
    private PointF end;


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

    public PointF getMeasuredSize() {
        return measuredSize;
    }

    public void setMeasuredSize(PointF measuredSize) {
        this.measuredSize = measuredSize;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public void setColor(Color color) {
        paint.setColor(ContextCompat.getColor(context, color.colorId));
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

    private void init(Context context) {
        this.context = context;
        setClickable(true);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setMaskFilter(new BlurMaskFilter(RADIUS_BLUR_MASK_FILTER, BlurMaskFilter.Blur.NORMAL));
        int screenWidth = ScreenUtil.getWidth(context);
        int screenHeight = ScreenUtil.getHeight(context);
        float ratio = (screenWidth > screenHeight ? screenWidth : screenHeight) / 1280f;
        start = new PointF(0, 0);
        end = new PointF(50 * ratio, 50 * ratio);
        PointF point1 = new PointF(20 * ratio, 30 * ratio);
        PointF point2 = new PointF(40 * ratio, 60 * ratio);
        final BezierEvaluator evaluator = new BezierEvaluator(point1, point2);
//        AnimatorSet set = new AnimatorSet();
        final ObjectAnimator objectAnimatorByXml = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.anim_block_rotate);
//        objectAnimatorByXml.setEvaluator(new ArgbEvaluator());
        final ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this, "measuredSize", evaluator, start, end);
        objectAnimator.setDuration(2000L);
//        set.play(objectAnimatorByXml).with(objectAnimator);
        objectAnimator.setTarget(this);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                postInvalidate();
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Block.this.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
            }
        });
        // crux
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (((Block) view).getStatus() == Status.on) {
                    objectAnimator.start();
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measuredSize.x = MeasureSpec.getSize(widthMeasureSpec);
        int measureSpec = MeasureSpec.makeMeasureSpec(Math.round(measuredSize.x), MeasureSpec.EXACTLY);
        setMeasuredDimension(measureSpec, measureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rectF.set(0, 0, measuredSize.x, measuredSize.x);
        canvas.drawRoundRect(rectF, RADIUS_ROUND_RECT, RADIUS_ROUND_RECT, paint);
        super.onDraw(canvas);
    }

    /**
     * 脱落后设置状态
     */
    private void off() {
        setStatus(Status.off);
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
        public final int colorId;

        Color(int colorId) {
            this.colorId = colorId;
        }
    }

    static class Position {
        final int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }
}
