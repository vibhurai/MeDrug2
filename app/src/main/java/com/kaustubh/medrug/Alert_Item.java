package com.kaustubh.medrug;

public class Alert_Item {
    String altext;
    int imageres;

    public Alert_Item(String text,int imageres)
    {
        this.imageres=imageres;
        this.altext=text;

    }
    public String getaltext(){return altext;}
    public int getImageres(){
        return imageres;
    }

}
