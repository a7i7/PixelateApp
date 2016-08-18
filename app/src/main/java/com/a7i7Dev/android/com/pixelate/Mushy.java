package com.a7i7Dev.android.com.pixelate;

import android.graphics.Color;

public class Mushy extends PixelEffect{

    private final int DEVIATION = 50;

    Mushy(int pixelSize)
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.pixelSize = pixelSize;
    }

    @Override
    public int getPixel(int x,int y)
    {
        int redOffset = (int) (Math.random()*DEVIATION);
        int greenOffset = (int) (Math.random()*DEVIATION);
        int blueOffset = (int) (Math.random()*DEVIATION);

        return Color.rgb(
                safeAdd(r,redOffset),
                safeAdd(g,greenOffset),
                safeAdd(b,blueOffset));
    }
}
