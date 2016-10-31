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

import java.util.ArrayList;
import java.util.List;

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
    private Block[][] blocks;

    private void init(Context context) {

        this.context = context;
        blocks = new Block[rowNum][colNum];
        wRatio = ScreenUtil.getWidth(context) / 720f;
        hRatio = ScreenUtil.getWidth(context) / 1280f;
        int btnPadding = (int) (2 * wRatio);
        blockSize = (int) ((ScreenUtil.getWidth(context) - 20 * btnPadding) / (float) colNum + 0.5f);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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

                blocks[i][j] = new Block(context);
//                btn.setPadding(btnPadding, btnPadding, btnPadding, btnPadding);
                blocks[i][j].setBackgroundColor(Color.YELLOW);
                blocks[i][j].setPosition(new Block.Position(i, j));
                btnLp = new LinearLayout.LayoutParams(blockSize, blockSize);
                btnLp.setMargins(btnPadding, btnPadding, btnPadding, btnPadding);
                row.addView(blocks[i][j], btnLp);

            }
            rowLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, blockSize + 2 * btnPadding);
            label.addView(row, rowLp);
        }
        this.removeAllViews();
        addView(label, lp);
        postInvalidate();
    }

    /**
     * 找到将要脱落的Block
     *
     * @param block
     * @return
     */
    private List<Block> findOffingBlock(Block block) {
        Block.Position position = block.getPosition();
        int x = position.x;
        int y = position.y;
        List<Block> colOnBlkList = new ArrayList<Block>(); // 列
        List<Block> rowOnBlkList = new ArrayList<Block>(); // 行
        for (int i = 0; i < rowNum; i++) {
            if (blocks[i][y].getStatus() == Block.Status.on) {
                colOnBlkList.add(blocks[i][y]);
            }
        }
        for (int i = 0; i < colNum; i++) {
            if (blocks[x][i].getStatus() == Block.Status.on) {
                rowOnBlkList.add(blocks[x][i]);
            }
        }

        int colZmin = Integer.MAX_VALUE, rowZmin = Integer.MAX_VALUE;
        int colFmin = Integer.MIN_VALUE, rowFmin = Integer.MIN_VALUE;
        for (int i = 0; i < rowOnBlkList.size(); i++) {
            int item = rowOnBlkList.get(i).getPosition().y;
            if (item - y < 0) {
                if (item > rowFmin) {
                    rowFmin = item;
                }
            } else {
                if (item < rowZmin) {
                    rowZmin = item;
                }
            }
        }
        for (int i = 0; i < rowOnBlkList.size(); i++) {
            int item = colOnBlkList.get(i).getPosition().x;
            if (item - x < 0) {
                if (item > colFmin) {
                    colFmin = item;
                }
            } else {
                if (item < colZmin) {
                    colZmin = item;
                }
            }
        }
        Block colZminBl, rowZminBl;
        Block colFminBl, rowFminBl;
        List<Block> nearlyList = new ArrayList<Block>();
        colZminBl = blocks[colZmin][y];
        rowZminBl = blocks[x][rowZmin];
        colFminBl = blocks[colFmin][y];
        rowFminBl = blocks[x][rowFmin];
        nearlyList.add(rowZminBl);
        nearlyList.add(colZminBl);
        nearlyList.add(rowFminBl);
        nearlyList.add(colFminBl);

        return nearlyList;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);
        setMeasuredDimension(measureSpec, measureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), measureSpec, measureSpec);
        }
//init(context);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
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
