package com.a7i7Dev.android.com.pixelate;

import android.graphics.Color;


public class LumenCenter extends PixelEffect
{
    private int midX,midY;

    LumenCenter(int pixelSize)
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.pixelSize = pixelSize;
        this.midX = pixelSize>>1;
        this.midY = pixelSize>>1;
    }

    @Override
    public int getPixel(int x,int y)
    {
        float exp = 1.0f;
        int distX = Math.abs(x-midX);
        int distY = Math.abs(y-midY);

        int off = (int)(Math.pow(distX,exp)+Math.pow(distY,exp));
        return Color.rgb(
                safeAdd(r,off),
                safeAdd(g,off),
                safeAdd(b,off));
    }
}

