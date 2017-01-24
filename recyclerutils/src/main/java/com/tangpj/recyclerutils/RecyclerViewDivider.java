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

    private Context context;

    //间隔
    private int interval = 0;

    private static DividerStyle style;
    private Drawable mDivider;
    private static final int[] ATTRS = new int[] {android.R.attr.listDivider};

    /**
     * 用来记录特殊view的数量
     * 在这个库中主要作用时又来记录headerView和footerView
     * 在RecyclerView的数量（0、1、2)
     */
    private int specialView = 0;

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
        this.context = context;
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
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager.getItemViewType(view) != 0){
            specialView ++;
            return;
        }
        int position = ((RecyclerView.LayoutParams)
                view.getLayoutParams()).getViewLayoutPosition() - specialView;
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount() - specialView;
        Log.d(TAG, "getItemOffsets: childCount = " + childCount);
        if (layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
            int orientation = lm.getOrientation();
            if ( orientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, 0, 0, interval);
            } else {
                outRect.set(0, 0, interval, 0);
            }
            return;
        }

        // 如果是最后一行，则不需要绘制底部
        if (isLastRaw(parent, position, spanCount, childCount)) {
            outRect.set(0, 0, interval, interval);
            // 如果是最后一列，则不需要绘制右边
        } else if (isLastColumn(parent, position, spanCount, childCount)) {
            outRect.set(0, 0, 0, interval);
        } else {
            outRect.set(0, 0, interval, interval);
        }
//        if (isFirstRaw(parent, position, spanCount, childCount)) {
//            if (!isLastColumn(parent, position, spanCount, childCount)){
//                outRect.set(interval, interval, 0, interval);
//            } else{
//                outRect.set(interval, interval, interval, interval);
//            }
//        }else if (isLastRaw(parent,position,spanCount,childCount)){
//            if (!isLastColumn(parent,position,spanCount,childCount)){
//                outRect.set(interval,0,0,0);
//            }else {
//                outRect.set(interval,interval,interval,0);
//            }
//        }
//        else if (isLastColumn(parent, position, spanCount, childCount)) {
//            outRect.set(interval, 0, interval, interval);
//        } else {
//            outRect.set(interval, 0, 0, interval);
//        }

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
     * @Method: isLastColumn
     * @author create by Tang
     * @date date 17/1/24 下午2:13
     * @Description: 判断是否是最后一列
     */
    private boolean isLastColumn(RecyclerView parent,int position,int span,int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (position == childCount - 1){
            return true;
        }
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
                if (position > lastColumn){
                    return true;
                }
            }
        }
        return false;
    }


    private boolean isFirstRaw(RecyclerView parent, int pos, int spanCount
            , int childCount){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            if (((GridLayoutManager)layoutManager).getSpanSizeLookup().getSpanSize(pos) == spanCount){
                return false;
            }
            if (pos < spanCount){
                return true;
            }
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL){
                if (pos < spanCount){
                    return true;
                }
            }else if(spanCount == 1){
                return true;
            } else {
                if ((pos + 1) % spanCount == 1){
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
            if (((GridLayoutManager)layoutManager).getSpanSizeLookup().getSpanSize(position) == span){
                return true;
            }
            int lastColumn = childCount - childCount % span;
            if (position >= lastColumn)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                int lastColumn = childCount - childCount % span;
                if (position >= lastColumn)
                    return true;
            } else  {
                if ((position + 1) % span == 0) {
                    return true;
                }
            }
        }
        return false;
    }




}
