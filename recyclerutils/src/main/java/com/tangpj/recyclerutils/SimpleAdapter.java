package com.tangpj.recyclerutils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @ClassName: SimpleAdapter
 * @author create by Tang
 * @date date 17/1/22 下午4:12
 * @Description: 简单的RecyclerView Adapter
 * 可以简便的设置RecyclerView的header和footer
 * @param <E> 内部数据项的类型（实体类）
 */

public abstract class SimpleAdapter<E> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ISimpleAdapter<E>,ICreateHeaderView,ICreateFooterView{

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_NORMAL = 3;

    private List<E> data;

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
            onBindNormalView(holder,position);
        }
        if (getItemViewType(position) == TYPE_HEADER){
            onBindHeaderView(holder,position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeader(ViewGroup parent) {
        return new Holder(parent);
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterView(ViewGroup parent) {
        return new Holder(parent);
    }

    @Override
    public void onBindFooterView(RecyclerView.ViewHolder normalHolder, int position) {
        // TODO: 17/1/22 如果子类需要对Footer监听控件或者进行数据绑定，则需要覆盖该方法
    }

    @Override
    public void onBindHeaderView(RecyclerView.ViewHolder normalHolder, int position) {
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

    /**
     * @Method: Holder
     * @author create by Tang
     * @date date 17/1/22 下午5:55
     * @Description: 当不需要对footer和header做任何处理时
     * 使用该使用该Holder创建footer和header
     */
    private class Holder extends RecyclerView.ViewHolder{
        public Holder(View itemView) {
            super(itemView);
        }
    }

}
