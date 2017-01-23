package com.tangpj.recyclerdemo;

/**
 * @ClassName: User
 * @author create by Tang
 * @date date 17/1/23 上午11:47
 * @Description: TODO
 */

public enum  User {

    USER1("user1",16),
    USER2("user2",16),
    USER3("user3",16),
    USER4("user4",16),
    USER5("user5",16),
    USER6("user6",16),
    ;

    public String name;
    public int age;
    public String sex;

    User(String name ,int age){
        this(name,age,"男");
    }

    User(String name ,int age ,String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
