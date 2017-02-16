package com.tangpj.recyclerviewutils;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.tangpj.recyclerviewutils.internal.IMultiItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MultiItem
 * @author create by Tang
 * @date date 17/2/16 上午10:30
 * @Description: TODO
 */

public abstract class MultiItem<E,Holder extends RecyclerView.ViewHolder> implements IMultiItem<E,Holder> {

    private List<E> data = new ArrayList<>();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((Holder) holder,data.get(position));
    }


    @Override
    public int size() {
        return data.size();
    }



    @Override
    public void setData(List<E> data) {
        data.clear();
        data.addAll(data);
    }

    @Override
    public List<E> getData() {
        return data;
    }
}
