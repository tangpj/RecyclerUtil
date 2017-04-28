package com.tangpj.recyclerviewutils.internal;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collection;
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

    void setData(Collection<E> data);

    /**
     * @Method: setData
     * @author create by Tang
     * @date 2017/4/28 上午11:08
     * @Description: 从start位开始，替换newData长度的item
     */
    void setData(int start,Collection<E> newData);

    void addItem(E value);

    void addAllItem(Collection<E> data);

    void removeItem(E value);

    void removeItem(int position);

}
