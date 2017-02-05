package com.tangpj.recyclerdemo.bean;

/**
 * @ClassName: UserBean
 * @author create by Tang
 * @date date 17/2/5 下午4:28
 * @Description: TODO
 */

public class UserBean {

    public String name;
    public int age;
    public int imgId;
    public String sex;

    public UserBean(String name ,int age){
        this(name,age,0);
    }

    public UserBean(String name ,int age ,int imgId){
        this(name,age,imgId,"男");
    }

    public UserBean(String name ,int age ,int imgId,String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.imgId = imgId;
    }
}
