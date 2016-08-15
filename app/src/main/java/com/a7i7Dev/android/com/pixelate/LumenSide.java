package com.a7i7Dev.android.com.pixelate;

import android.graphics.Color;

/**
 * Created by Welcome on 14-08-2016.
 */
public class LumenSide extends PixelEffect
{
    LumenSide(int pixelSize)
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.pixelSize = pixelSize;
    }

    public int getPixel(int x,int y)
    {
        float exp = 0.5f;
        int off = (int)(Math.pow(x,exp)+Math.pow(y,exp));
        return Color.rgb(
                Math.min(255,r+off),
                Math.min(255,g+off),
                Math.min(255,b+off));
    }
}
