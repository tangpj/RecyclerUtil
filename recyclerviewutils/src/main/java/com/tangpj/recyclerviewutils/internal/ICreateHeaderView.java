package com.tangpj.recyclerviewutils.internal;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
    @LayoutRes
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

    /**
     * @Method: onBindHeaderView
     * @author create by Tang
     * @date date 17/1/23 上午10:26
     * @Description: 如果子类需要对header作任何处理
     * 需要覆盖该方法
     * @param header headerHolder中的itemView
     *               通过<header/>可以处理<header/>
     *               中的控件
     */
    void onBindHeaderView(View header);
}
