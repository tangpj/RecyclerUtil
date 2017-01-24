package com.tangpj.recyclerdemo;

/**
 * @ClassName: User
 * @author create by Tang
 * @date date 17/1/23 上午11:47
 * @Description: TODO
 */

public enum  User {

    USER1("USER1",16),
    USER2("USER2",16),
    USER3("USER3",16),
    USER4("USER4",16),
    USER5("USER5",16),
    USER6("USER6",16),
    USER7("USER7",16),
    USER8("USER8",16),
    USER9("USER9",16),
    USER10("USER10",16),
    USER11("USER11",16),
    USER12("USER12",16),
    USER13("USER13",16),
    USER14("USER14",16),
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
