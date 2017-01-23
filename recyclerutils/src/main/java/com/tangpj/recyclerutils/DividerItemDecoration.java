package com.tangpj.recyclerutils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * @ClassName: DividerItemDecoration
 * @author create by Tang
 * @date date 17/1/23 下午3:53
 * @Description: RecyclerView分割线
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration{

    private Context context;

    //间隔
    private float interval = 0;

    private DividerStyle style;
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

    private DividerItemDecoration(Context context,float interval){
        this.context = context;
        this.interval = TypedValue.applyDimension(
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
    public DividerItemDecoration newTransparentDivider(Context context){
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
    public DividerItemDecoration newTransparentDivider(Context context,float interval){
        style = DividerStyle.TRANSPARENT;
        return new DividerItemDecoration(context,interval);
    }

    /**
     * @Method: newLinesDivider
     * @author create by Tang
     * @date date 17/1/23 下午5:19
     * @Description: 创建不透明分割线
     * 默认宽度为1dp
     */
    public DividerItemDecoration newLinesDivider(Context context){
        return newLinesDivider(context,1);
    }

    /**
     * @Method: newLinesDivider
     * @author create by Tang
     * @date date 17/1/23 下午5:20
     * @Description: 创建不透明分割线
     * @param interval 分割线宽度
     *                 单位为dp
     */
    public DividerItemDecoration newLinesDivider(Context context,int interval){
        style = DividerStyle.LINES;
        return new DividerItemDecoration(context,interval);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (style == DividerStyle.LINES){
            RecyclerView.LayoutManager lm = parent.getLayoutManager();
            drawHorizontal(c,parent);
            if (!(lm instanceof LinearLayoutManager)){
                drawVertical(c,parent);
            }

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) parent.getLayoutParams()).getViewLayoutPosition();

    }

    private void drawHorizontal(Canvas canvas,RecyclerView parent){
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams lp
                    = (RecyclerView.LayoutParams) parent.getLayoutParams();
            final int left = child.getLeft() - lp.leftMargin;
            final int right = child.getRight() + lp.rightMargin + mDivider.getIntrinsicWidth();
            final int top = child.getTop() + lp.topMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(canvas);
        }
    }

    private void drawVertical(Canvas canvas,RecyclerView parent){
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams lp
                    = (RecyclerView.LayoutParams) parent.getLayoutParams();
            final int left = child.getRight() + lp.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            final int top = child.getTop() - lp.topMargin;
            final int bottom = child.getBottom() + lp.bottomMargin;
        }
    }


}
