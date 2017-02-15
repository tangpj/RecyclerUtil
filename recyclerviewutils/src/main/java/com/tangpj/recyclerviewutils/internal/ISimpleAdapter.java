package com.tangpj.recyclerviewutils.internal;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @ClassName: ISimpleAdapter
 * @author create by Tang
 * @date date 17/1/22 下午4:22
 * @Description: SimpleAdapter接口
 */

public interface ISimpleAdapter<E> {


    /**
     * @Method: onCreateNormalView
     * @author create by Tang
     * @date date 17/1/22 下午4:38
     * @Description: 创建正常的列表项
     * 该方法需要在子类中实现
     */
    RecyclerView.ViewHolder onCreateNormalView(ViewGroup parent);

    /**
     * @Method: onBindNormalView
     * @author create by Tang
     * @date date 17/1/22 下午4:40
     * @Description: 绑定数据
     */

    void onBindNormalView (RecyclerView.ViewHolder normalHolder, int position,E value);

    void setData(List<E> data);

    void addItem(E value);

    void removeItem(E value);

    void removeItem(int position);

}
