package com.peng.colorblock.util;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by Hanrong on 2016/11/2.
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF pointF;
    /**
     * 中间两个点
     */
    private PointF point1;
    private PointF point2;

    public BezierEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public PointF evaluate(float time, PointF start, PointF end) {
        float timeLeft = 1.0f - time;
        pointF = new PointF();//结果

        PointF point0 = start;//起点

        PointF point3 = end;//终点
        pointF.x = timeLeft * timeLeft * timeLeft * (point0.x)
                + 3 * timeLeft * timeLeft * time * (point1.x)
                + 3 * timeLeft * time * time * (point2.x)
                + time * time * time * (point3.x);

        pointF.y = timeLeft * timeLeft * timeLeft * (point0.y)
                + 3 * timeLeft * timeLeft * time * (point1.y)
                + 3 * timeLeft * time * time * (point2.y)
                + time * time * time * (point3.y);
        return pointF;
    }
}
