package com.tangpj.recyclerutils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * @ClassName: RecyclerViewDivider
 * @author create by Tang
 * @date date 17/1/23 下午3:53
 * @Description: RecyclerView分割线
 */

public class RecyclerViewDivider extends RecyclerView.ItemDecoration{

    private static final String TAG = "RecyclerViewDivider";


    //间隔
    private int interval;
    //纵向分割线的补偿量
    private int recoupInterval;
    //分割线补偿余数，用于最后一列
    private int recoupResidue;
    //用来计算item间分割线的偏移系数
    private int intervalOffset;
    //

    private static DividerStyle style;
    private Drawable mDivider;
    private static final int[] ATTRS = new int[] {android.R.attr.listDivider};


    /**
     * @ClassName: DividerType
     * @author create by Tang
     * @date date 17/1/23 下午4:15
     * @Description: 分割线的样式
     */
    public enum DividerStyle{
        //线条
        LINES,
        //透明
        TRANSPARENT
    }

    private RecyclerViewDivider(Context context, float interval){
        this.interval = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,interval,context.getResources().getDisplayMetrics());
        if (style == DividerStyle.LINES){
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

    }



    /**
     * @Method: newTransparentDivider
     * @author create by Tang
     * @date date 17/1/23 下午4:21
     * @Description: 创建透明分割线
     * 默认宽度为16dp
     */
    public static RecyclerViewDivider newTransparentDivider(Context context){
        return newTransparentDivider(context,16);
    }

    /**
     * @Method: newTransparentDivider
     * @author create by Tang
     * @date date 17/1/23 下午4:21
     * @Description: 创建透明分割线
     * @param interval 分割线宽度
     *                 单位为dp
     */
    public static RecyclerViewDivider newTransparentDivider(Context context, float interval){
        style = DividerStyle.TRANSPARENT;
        return new RecyclerViewDivider(context,interval);
    }

    /**
     * @Method: newLinesDivider
     * @author create by Tang
     * @date date 17/1/23 下午5:19
     * @Description: 创建不透明分割线
     * 默认宽度为1dp
     */
    public static RecyclerViewDivider newLinesDivider(Context context){
        return newLinesDivider(context,1);
    }

    /**
     * @Method: newLinesDivider
     * @author create by Tang
     * @date date 17/1/23 下午5:19
     * @Description: 创建不透明分割线
     * @param interval 分割线宽度
     *                 单位为dp
     *
     */
    public static RecyclerViewDivider newLinesDivider(Context context,int interval){
        style = DividerStyle.LINES;
        return new RecyclerViewDivider(context,interval);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (style == DividerStyle.LINES){
            RecyclerView.LayoutManager lm = parent.getLayoutManager();
            if (lm instanceof GridLayoutManager){
                drawVertical(c,parent);
            }
            if (lm instanceof LinearLayoutManager){
                if (((LinearLayoutManager) lm).getOrientation() == LinearLayoutManager.VERTICAL){
                    drawHorizontal(c,parent);
                    return;
                }
                drawVertical(c,parent);
                return;
            }
            drawHorizontal(c,parent);
            drawVertical(c,parent);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int span;
        int position;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (layoutManager.getItemViewType(view) == SimpleAdapter.TYPE_HEADER){
            outRect.set(0, 0, 0, interval);
            return;
        }
        if (layoutManager.getItemViewType(view) == SimpleAdapter.TYPE_FOOTER){
            return;
        }
        if (adapter.getItemViewType(0) != 0){
            position = layoutManager.getPosition(view) - 1;
        }else {
            position = layoutManager.getPosition(view);
        }


        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        if (layoutManager instanceof GridLayoutManager){
            span = ((GridLayoutManager)layoutManager).getSpanCount() - 1;
            if (recoupInterval == 0 ) {
                recoupInterval = this.interval / (span + 1);
                recoupResidue = this.interval % (span + 1);
            }
            outGridVerticalRect(outRect,parent,position,spanCount,childCount);
            return;
        }else if (layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
            int orientation = lm.getOrientation();
            if ( orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, interval);
            } else {
                outRect.set(0, 0, interval, 0);
            }
            return;
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager sl = (StaggeredGridLayoutManager)layoutManager;
            span = sl.getSpanCount() - 1;
            if (sl.getOrientation() == StaggeredGridLayoutManager.VERTICAL){
                if (recoupInterval == 0){
                    recoupInterval = this.interval / (span + 1);
                    recoupResidue = this.interval % (span + 1);
                }
                outGridVerticalRect(outRect,parent,position,spanCount,childCount);
                return;
            }

            outGridHorizontalRect(outRect,parent,position,spanCount,childCount);

        }




    }

    private void outGridVerticalRect(Rect outRect,RecyclerView parent,int position,int spanCount,int childCount){
        if (isFirstColumn(parent,position,spanCount,childCount)){
            outRect.set(0, 0, interval - recoupInterval, interval);
            intervalOffset = 1;

        }else if (isLastColumn(parent, position, spanCount, childCount)) {
            outRect.set(interval - (recoupInterval + recoupResidue),
                    0,
                    0,
                    interval);
        }else if (isLastRaw(parent, position, spanCount, childCount)) {
            outRect.set(recoupInterval * (intervalOffset - 1)
                    , 0
                    , interval - recoupInterval * intervalOffset
                    , interval);
        } else {
            outRect.set(recoupInterval * (intervalOffset - 1)
                    , 0
                    , interval - recoupInterval * intervalOffset
                    , interval);
        }
        intervalOffset++;
    }

    private void outGridHorizontalRect(Rect outRect,RecyclerView parent,int position,int spanCount,int childCount){
        if (isFirstColumn(parent,position,spanCount,childCount)){
            outRect.set(0, 0, interval, interval);
            intervalOffset = 1;

        }else if (isLastColumn(parent, position, spanCount, childCount)) {
            outRect.set(0,
                    0,
                    0,
                    interval);
        }else if (isLastRaw(parent, position, spanCount, childCount)) {
            outRect.set(0, 0, interval, interval);
        } else {
            outRect.set(0, 0, interval, interval);
        }

    }

    private void drawHorizontal(Canvas canvas,RecyclerView parent){
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams lp
                    = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - lp.leftMargin;
            final int right = child.getRight() + lp.rightMargin + interval;
            final int top = child.getTop() - interval;
            final int bottom = child.getTop() + lp.topMargin;
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(canvas);
        }
    }

    private void drawVertical(Canvas canvas,RecyclerView parent){
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams lp
                    = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + lp.rightMargin;
            final int right = left + interval;
            final int top = child.getTop() - lp.topMargin;
            final int bottom = child.getBottom() + lp.bottomMargin;
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(canvas);
        }
    }

    private int getSpanCount(RecyclerView parent){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int span = 1;
        if (layoutManager instanceof GridLayoutManager){
            span = ((GridLayoutManager)layoutManager).getSpanCount();
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            span = ((StaggeredGridLayoutManager)layoutManager).getSpanCount();
        }
        return span;
    }


    /**
     * @Method: isFirstColumn
     * @author create by Tang
     * @date date 17/2/4 上午11:06
     * @Description: 判断是否是第一列
     */
    private boolean isFirstColumn(RecyclerView parent,int position,int span, int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (position == 0){
            return true;
        }
        if (layoutManager instanceof GridLayoutManager){
            if (((position + 1) % span) == 1){
                return true;
            }
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager)layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                if (((position + 1) % span) == 1){
                    return true;
                }
            }else {
                if (position < span){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @Method: isLastColumn
     * @author create by Tang
     * @date date 17/1/24 下午2:13
     * @Description: 判断是否是最后一列
     */
    private boolean isLastColumn(RecyclerView parent,int position,int span,int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager ){

            if ((position + 1) % span == 0){
                return true;
            }
        }else if (layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                if ((position + 1) % span == 0){
                    return true;
                }
            }else {
                //大于该值的item位于最后一列中
                int lastColumn = childCount - childCount % span;
                if (position >= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }




    /**
     * @Method: isLastRaw
     * @author create by Tang
     * @date date 17/1/24 下午2:14
     * @Description: 判断是否是最后一行
     */
    private boolean isLastRaw(RecyclerView parent,int position,int span,int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int lastColumn = childCount - (childCount % span == 0 ? span : childCount % span);
            if (position >= lastColumn){
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                int lastColumn = childCount - (childCount % span == 0 ? span : childCount % span);
                if (position >= lastColumn){
                    return true;
                }
            } else  {
                if ((position + 1) % span == 0) {
                    return true;
                }
            }
        }
        return false;
    }




}
