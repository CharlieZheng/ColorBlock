package com.peng.colorblock.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Hanrong on 2016/10/30.
 */
public class Block extends Button {
    private int color;
    private Status status;
    private Position position;

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
    }

    public Block(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Block(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        yellow, blue, red, purple, green,
    }
}
