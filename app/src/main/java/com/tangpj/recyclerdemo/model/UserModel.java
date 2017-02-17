package com.tangpj.recyclerdemo.model;

import com.tangpj.recyclerdemo.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserModel
 * @author create by Tang
 * @date date 17/1/23 上午11:47
 * @Description: TODO
 */

public class UserModel {
    private List<UserBean> list ;

    public UserModel(){
        list = new ArrayList<>();
        init();
    }


    private  void init(){
        list.add(new UserBean("USER1",16));
        list.add(new UserBean("USER2",16));
        list.add(new UserBean("USER3",16));
        list.add(new UserBean("USER4",16));
        list.add(new UserBean("USER5",16));
        list.add(new UserBean("USER6",16));
        list.add(new UserBean("USER7",16));
        list.add(new UserBean("USER8",16));
        list.add(new UserBean("USER9",16));
        list.add(new UserBean("USER10",16));
        list.add(new UserBean("USER11",16));
        list.add(new UserBean("USER12",16));
        list.add(new UserBean("USER13",16));
        list.add(new UserBean("USER14",16));
        list.add(new UserBean("USER15",17));
        list.add(new UserBean("USER16",17));
        list.add(new UserBean("USER17",17));
        list.add(new UserBean("USER18",17));
        list.add(new UserBean("USER19",17));
        list.add(new UserBean("USER20",17));
    }

    public List<UserBean> getData(){
        return list;
    }
}
