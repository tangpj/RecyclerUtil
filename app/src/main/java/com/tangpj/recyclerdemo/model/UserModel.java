package com.tangpj.recyclerdemo.model;

import com.tangpj.recyclerdemo.bean.UserBean;

/**
 * @ClassName: UserModel
 * @author create by Tang
 * @date date 17/1/23 上午11:47
 * @Description: TODO
 */

public enum UserModel {
    USER1(new UserBean("USER1",16)),
    USER2(new UserBean("USER2",16)),
    USER3(new UserBean("USER3",16)),
    USER4(new UserBean("USER4",16)),
    USER5(new UserBean("USER5",16)),
    USER6(new UserBean("USER6",16)),
    USER7(new UserBean("USER7",16)),
    USER8(new UserBean("USER8",16)),
    USER9(new UserBean("USER9",16)),
    USER10(new UserBean("USER10",16)),
    USER11(new UserBean("USER11",16)),
    USER12(new UserBean("USER12",16)),
    USER13(new UserBean("USER13",16)),
    USER14(new UserBean("USER14",16)),
//    USER14("USER14",16),
//    USER15("USER15",16),
    ;

    public UserBean user;

    UserModel(UserBean user){
        this.user = user;
    }
}
