package com.tangpj.recyclerutils;

import android.support.v7.widget.RecyclerView;
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
    int setFooterView();

    /**
     * @Method: onCreateFooterView
     * @author create by Tang
     * @date date 17/1/22 下午4:37
     * @Description: 如果setFooterView返回不为0
     * 父类中则调用该方法创建FooterView
     */
    RecyclerView.ViewHolder onCreateFooterView(ViewGroup parent);

    void onBindFooterView(RecyclerView.ViewHolder normalHolder, int position);
}
