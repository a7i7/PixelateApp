package com.a7i7Dev.android.com.pixelate;
import android.graphics.Color;

public abstract class PixelEffect {

    protected int r,g,b,pixelSize;

    protected int safeAdd(int color,int offset)
    {
        int result = color + offset;
        result = Math.max(0,result);
        result = Math.min(255,result);
        return result;
    }
    public void refresh(int r,int g,int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public abstract int getPixel(int x,int y);

}

