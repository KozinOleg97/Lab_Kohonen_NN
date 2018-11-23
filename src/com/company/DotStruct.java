package com.company;

import java.awt.*;

public class DotStruct {

    Integer[] vecInteger;
    Color color = Color.blue;

    DotStruct(Integer xMaxSize, Integer yMaxSize, Boolean rnd) {
        vecInteger = new Integer[2];
        vecInteger[0] =(int) (Math.random() * xMaxSize);
        vecInteger[1] =(int) (Math.random() * yMaxSize);
    }

    DotStruct(Integer x, Integer y) {
        vecInteger = new Integer[2];
        vecInteger[0] =x;
        vecInteger[1] =y;
    }
    public void setColor(Color color){
        this.color = color;
    }

}
