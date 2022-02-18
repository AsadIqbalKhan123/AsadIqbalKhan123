package com.shashank.expensermanager;

import java.io.Serializable;

public class UserData implements Serializable {

    String name;
    int imgName;

//    public UserData(String name, int imgName) {
//        this.name = name;
//        this.imgName = imgName;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgName() {
        return imgName;
    }

    public void setImgName(int imgName) {
        this.imgName = imgName;
    }


}
