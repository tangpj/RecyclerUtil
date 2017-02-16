package com.tangpj.recyclerviewutils;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MultiAdapter
 * @author create by Tang
 * @date date 17/2/16 上午10:25
 * @Description: 能够快速实现多布局的Adapter
 */

public class MultiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MultiItem> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (MultiItem item : items){
            if (item.getViewType() == viewType){
                return item.onCreateViewHolder(parent);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        for (MultiItem item : items){
            if(item.getViewType() == getItemViewType(position)){
                item.onBindViewHolder(holder,getMultiItemIndex(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (MultiItem item : items){
            count += item.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int count = -1;
        MultiItem item = null;
        for (int i = 0 ; i < items.size() ; i++){
            count += items.get(i).size();
            if (position <= count){
                item = items.get(i);
                break;
            }
        }
        return item == null ? 0 : item.getViewType();
    }

    /**
     * @Method: getRealItemIndex
     * @author create by Tang
     * @date date 17/2/16 上午11:31
     * @Description: 获取对应的Item中数据的真实索引
     */
    private int getMultiItemIndex(int position){
        int count = - 1;
        for (int i = 0 ; i < items.size() ; i++){
            count += items.get(i).size();
            if (position <= count){
                break;
            }
        }
        return position - count - 1;
    }
}
