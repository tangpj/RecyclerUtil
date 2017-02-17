package com.tangpj.recyclerviewutils.internal;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @ClassName: ICreateFooterView
 * @author create by Tang
 * @date date 17/1/22 下午4:43
 * @Description: 如果需要支持添加FooterView功能
 * 需要在Adapter中实现该接口
 */
public interface ICreateFooterView {

    /**
     * @Method: setFooterView
     * @author create by Tang
     * @date date 17/1/22 下午4:26
     * @return 尾部id
     * @Description: 设置footer
     * 只能在adapter中初始化view
     */
    @LayoutRes
    int setFooterView();

    /**
     * @Method: onCreateFooterView
     * @author create by Tang
     * @date date 17/1/22 下午4:37
     * @Description: 如果setFooterView返回不为0
     * 父类中则调用该方法创建FooterView
     */
    RecyclerView.ViewHolder onCreateFooterView(ViewGroup parent);


    /**
     * @Method: onBindHeaderView
     * @author create by Tang
     * @date date 17/1/23 上午10:26
     * @Description: 如果子类需要对header作任何处理
     * 需要覆盖该方法
     * @param footer footerHolder中的itemView
     *               通过<footer/>可以处理<footer/>
     *               中的控件
     */
    void onBindFooterView(View footer);
}
