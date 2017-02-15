package com.tangpj.recyclerviewutils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Method: Holder
 * @author create by Tang
 * @date date 17/1/22 下午5:55
 * @Description: 当不需要对footer和header做任何处理时
 * 使用该使用该Holder创建footer和header
 */
class Holder extends RecyclerView.ViewHolder{
    public Holder(View itemView) {
        super(itemView);
    }
}
