package com.huson.generic;

import org.junit.Test;

import java.util.ArrayList;

public class PointTest {

    @Test
    public void test1()
    {
        Point<Integer> integerPoint = new Point<>();
        integerPoint.setX(23);

        Point<Float> floatPoint = new Point<>();
        floatPoint.setX(2.2F);

        Point<String> stringPoint = new Point<>();
        stringPoint.setX("abc");

        System.out.println("integerPoint.get()=" + integerPoint.getX());
        System.out.println("floatPoint.get()=" + floatPoint.getX());
        System.out.println("stringPoint.get()=" + stringPoint.getX());

        ArrayList<String> arrayList = new ArrayList<String>();
    }
}
