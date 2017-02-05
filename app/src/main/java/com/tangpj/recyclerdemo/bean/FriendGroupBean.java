package com.tangpj.recyclerdemo.bean;

import java.util.List;

/**
 * @ClassName: FriendGroupBean
 * @author create by Tang
 * @date date 17/2/5 下午5:02
 * @Description: TODO
 */

public class FriendGroupBean {
    public String title;
    public List<UserBean> data;

    public FriendGroupBean(String title,List<UserBean> data){
        this.title = title;
        this.data = data;
    }
}
