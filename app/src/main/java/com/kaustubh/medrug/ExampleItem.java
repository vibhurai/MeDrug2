
package com.kaustubh.medrug;

import org.jetbrains.annotations.NotNull;

public class ExampleItem {
    private int imageResource;
    private String text1;
    private String text2;

    ExampleItem(int imageResource, String text1, String text2) {
        this.imageResource = imageResource;
        this.text1 = text1;
        this.text2 = text2;
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


    @NotNull
    public String toString()
    {
        return (text1+" "+text2);
    }
}
