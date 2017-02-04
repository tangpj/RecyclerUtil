package com.tangpj.recyclerutils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @ClassName: SimpleAdapter
 * @author create by Tang
 * @date date 17/1/22 下午4:12
 * @Description: 简单的RecyclerView SecondaryAdapter
 * 可以简便的设置RecyclerView的header和footer
 * @param <E> 内部数据项的类型（实体类）
 */

public abstract class SimpleAdapter<E> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ISimpleAdapter<E>,ICreateHeaderView,ICreateFooterView{

    private static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;

    private List<E> data;

    private OnItemClickListener<E> clickListener;
    private OnItemFocusChangeListener<E> focusChangeListener;

    public void setOnItemClickListener(OnItemClickListener<E> clickListener){
        this.clickListener = clickListener;
    }

    public void setOnFocusChangeListener(OnItemFocusChangeListener<E> focusChangeListener){
        this.focusChangeListener = focusChangeListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER){
            return onCreateHeader(parent);
        }
        if (viewType == TYPE_FOOTER){
            return onCreateFooterView(parent);
        }

        return onCreateNormalView(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL){
            final int mPosition = getRealPosition(position);
            onBindNormalView(holder,position,data.get(mPosition));
            if (data.size() <= 0){
                return;
            }
            if (clickListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.onItemClick(mPosition,data.get(mPosition));
                    }
                });
            }
            if (focusChangeListener != null){
                holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        focusChangeListener.onFocusChange(v,hasFocus,mPosition,data.get(mPosition));
                    }
                });
            }
            return;
        }
        if (getItemViewType(position) == TYPE_HEADER){
            onBindHeaderView(holder.itemView,position);
            return;
        }
        onBindFooterView(holder.itemView,position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeader(ViewGroup parent) {
        View header = LayoutInflater.from(parent.getContext()).inflate(setHeaderView(),parent,false);
        return new Holder(header);
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterView(ViewGroup parent) {
        View footer = LayoutInflater.from(parent.getContext()).inflate(setFooterView(),parent,false);
        return new Holder(footer);
    }

    @Override
    public void onBindHeaderView(View header, int position) {
        // TODO: 17/1/22 如果子类需要对Footer监听控件或者进行数据绑定，则需要覆盖该方法
    }

    @Override
    public void onBindFooterView(View footer, int position) {
        // TODO: 17/1/22 如果子类需要对Header监听控件或者进行数据绑定，则需要覆盖该方法
    }

    @Override
    public int getItemCount() {
        if (setHeaderView() != 0){
            if (setFooterView() != 0){
                return data == null ? 0 : data.size() + 2;
            }
            return data == null ? 0 : data.size() + 1;
        }
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (setHeaderView() != 0 && position == 0){
            return TYPE_HEADER;
        }

        if (setFooterView() != 0 && position == getItemCount() - 1){
            return TYPE_FOOTER;
        }

        return TYPE_NORMAL;
    }

    /**
     * @Method: setHeaderView
     * @author create by Tang
     * @date date 17/1/22 下午4:31
     * @Description: 如果需要设置header的话需要在子类中覆盖该方法
     */
    @Override
    public int setHeaderView() {
        return 0;
    }

    /**
     * @Method: setFooterView
     * @author create by Tang
     * @date date 17/1/22 下午4:32
     * @Description: 如果需要设置footer的话需要在子类中覆盖该方法
     */
    @Override
    public int setFooterView() {
        return 0;
    }

    @Override
    public void setData(List<E> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void addItem(E value) {
        data.add(value);
        notifyItemChanged(data.size() - 1);
    }

    @Override
    public void removeItem(int position) {
        data.remove(position);
        notifyItemChanged(position);
    }

    @Override
    public void removeItem(E value) {
        int position = data.indexOf(value);
        data.remove(position);
        notifyItemChanged(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if(lm instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) lm;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == TYPE_HEADER ||
                            getItemViewType(position) == TYPE_FOOTER){
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams layoutParams
                    = (StaggeredGridLayoutManager.LayoutParams) lp;
            layoutParams.setFullSpan(getItemViewType(holder.getLayoutPosition()) == TYPE_HEADER
                    || getItemViewType(holder.getLayoutPosition()) == TYPE_FOOTER);
        }
    }

    //获取列表数据所在位置
    private int getRealPosition(int position){
        if (setHeaderView() == 0){
            return position;
        }
        return position - 1;
    }




    /**
     * @ClassName: OnItemClickListenter
     * @author create by Tang
     * @date date 17/1/23 上午11:20
     * @Description: 点击监听器
     */
    public interface OnItemClickListener<E>{
        void onItemClick(int position,E value);
    }

    /**
     * @ClassName: OnItemFocusChangeListener
     * @author create by Tang
     * @date date 17/1/23 上午11:20
     * @Description: 焦点变化监听器
     */
    public interface OnItemFocusChangeListener<E>{
        void onFocusChange(View view,boolean hasFocus,int position,E data);
    }

}
