package com.kaustubh.medrug;

import org.jetbrains.annotations.NotNull;

public class DocItem {
    private int imageResource;
    private String text1;
    private String text2;
    private String text3;
    private String text4;

     public  DocItem(int imageResource, String text1, String text2, String text3, String text4) {
        this.imageResource = imageResource;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
    }

    int getImageResource() {
        return imageResource;
    }

    String getText1() {
        return text1;
    }

    String getText2() {
        return text2;
    }

    String getText3() {
        return text3;
    }

    String getText4() {
        return text4;
    }


    @NotNull
    public String toString()
    {
        return (text1+" "+text2);
    }
 }
