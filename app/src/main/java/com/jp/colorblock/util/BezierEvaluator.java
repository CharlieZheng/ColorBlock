package com.jp.colorblock.util;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {
    /**
     * 中间两个点
     */
    private final PointF point1;
    private final PointF point2;

    public BezierEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public static void main(String[] args) {

    }

    @Override
    public PointF evaluate(float time, PointF start, PointF end) {
        float timeLeft = 1.0f - time;
        PointF pointF = new PointF();//结果


        pointF.x = timeLeft * timeLeft * timeLeft * (start.x)
                + 3 * timeLeft * timeLeft * time * (point1.x)
                + 3 * timeLeft * time * time * (point2.x)
                + time * time * time * (end.x);

        pointF.y = timeLeft * timeLeft * timeLeft * (start.y)
                + 3 * timeLeft * timeLeft * time * (point1.y)
                + 3 * timeLeft * time * time * (point2.y)
                + time * time * time * (end.y);
        return pointF;
    }
}
