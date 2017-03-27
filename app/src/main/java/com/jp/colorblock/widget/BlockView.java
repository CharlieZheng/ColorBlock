package com.jp.colorblock.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.jp.colorblock.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class BlockView extends LinearLayout {

    private static final int rowNum = 10;
    /**
     * 列数
     */
    private static final int colNum = 10;
    private Block[][] blocks;

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

    private void init(Context context) {
        blocks = new Block[rowNum][colNum];
        float wRatio = ScreenUtil.getWidth(context) / 720f;
        int btnPadding = (int) (2 * wRatio);
        int blockSize = (int) ((ScreenUtil.getWidth(context) - 20 * btnPadding) / (float) colNum + 0.5f);
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
            Block.Color[] temp = Block.Color.values();
            for (int j = 0; j < colNum; j++) {
                blocks[i][j] = new Block(context);
                blocks[i][j].setColor(temp[j % temp.length]);
                blocks[i][j].setPosition(new Block.Position(i, j));
                blocks[i][j].setStatus(Block.Status.on);
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
     * @param block 被点击的砖块
     * @return 即将被消除的砖块
     */
    private List<Block> findOffingBlock(Block block) {
        Block.Position position = block.getPosition();
        int x = position.x;
        int y = position.y;
        List<Block> colOnBlkList = new ArrayList<>(); // 列
        List<Block> rowOnBlkList = new ArrayList<>(); // 行
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

        int colZMin = Integer.MAX_VALUE, rowZMin = Integer.MAX_VALUE;
        int colFMin = Integer.MIN_VALUE, rowFMin = Integer.MIN_VALUE;
        for (int i = 0; i < rowOnBlkList.size(); i++) {
            int item = rowOnBlkList.get(i).getPosition().y;
            if (item - y < 0) {
                if (item > rowFMin) {
                    rowFMin = item;
                }
            } else {
                if (item < rowZMin) {
                    rowZMin = item;
                }
            }
        }
        for (int i = 0; i < rowOnBlkList.size(); i++) {
            int item = colOnBlkList.get(i).getPosition().x;
            if (item - x < 0) {
                if (item > colFMin) {
                    colFMin = item;
                }
            } else {
                if (item < colZMin) {
                    colZMin = item;
                }
            }
        }
        Block colZMinBl, rowZMinBl;
        Block colFMinBl, rowFMinBl;
        List<Block> nearlyList = new ArrayList<>();
        colZMinBl = blocks[colZMin][y];
        rowZMinBl = blocks[x][rowZMin];
        colFMinBl = blocks[colFMin][y];
        rowFMinBl = blocks[x][rowFMin];
        nearlyList.add(rowZMinBl);
        nearlyList.add(colZMinBl);
        nearlyList.add(rowFMinBl);
        nearlyList.add(colFMinBl);
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
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View item = getChildAt(i);
            item.layout(l, t, r, b);
        }
        super.onLayout(changed, l, t, r, b);
    }
}
