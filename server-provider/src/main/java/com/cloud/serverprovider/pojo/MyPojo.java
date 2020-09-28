package com.cloud.serverprovider.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

//@ConfigurationProperties(prefix = "myPojo")
//@Component
public class MyPojo implements Serializable {

    private String name;
    private int age;
    private String like;
    private String addre;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getAddre() {
        return addre;
    }

    public void setAddre(String addre) {
        this.addre = addre;
    }

    @Override
    public String toString() {
        return "MyPojo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", like='" + like + '\'' +
                ", addre='" + addre + '\'' +
                '}';
    }
}
