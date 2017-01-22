package com.tangpj.recyclerutils;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * @ClassName: ICreateHeaderView
 * @author create by Tang
 * @date date 17/1/22 下午4:42
 * @Description: 如果需要支持添加HeaderView功能
 * 需要在Adapter中实现该接口
 */

public interface ICreateHeaderView {
    /**
     * @Method: setHeaderView
     * @author create by Tang
     * @date date 17/1/22 下午4:25
     * @return 头部布局id
     * @Description: 设置header
     * 只能在adapter中初始化view
     */
    int setHeaderView();

    /**
     * @Method: onCreateHeader
     * @author create by Tang
     * @date date 17/1/22 下午4:36
     * @Description: 如果setHeader返回不为0
     * 在父类中则调用该方法创建HeaderView
     *
     */
    RecyclerView.ViewHolder onCreateHeader(ViewGroup parent);

    void onBindHeaderView(RecyclerView.ViewHolder normalHolder, int position);
}
