
package com.kaustubh.medrug;

import org.jetbrains.annotations.NotNull;

public class ExampleItem {
    private int imageResource;
    private String text1;
    private String text2;
    private int qty;

    ExampleItem(int imageResource, String text1, String text2 ,int qty) {
        this.imageResource = imageResource;
        this.text1 = text1;
        this.text2 = text2;
        this.qty=qty;
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

    int getQty(){return qty;}


    @NotNull
    public String toString()
    {
        return (text1+" "+text2);
    }
}
