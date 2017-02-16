package com.tangpj.recyclerviewutils.internal;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import java.util.List;

/**
 * @ClassName: IMultiItem
 * @author create by Tang
 * @date date 17/2/16 上午10:30
 * @Description: IMultiItem接口文件
 * 定义MultiItem的行为
 */

public interface IMultiItem<E,Holder> {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(Holder holder,E e);

    void onBindViewHolder(RecyclerView.ViewHolder holder,int position);

    int size();

    int getViewType();

    void setData(List<E> data);

    List<E> getData();

}
