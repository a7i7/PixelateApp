package com.a7i7Dev.android.com.pixelate;

import android.graphics.Color;

/**
 * Created by Welcome on 14-08-2016.
 */
public class LumenCenter extends PixelEffect
{
    private int midX,midY;

    LumenCenter(int pixelSize)
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.pixelSize = pixelSize;
        this.midX = pixelSize/2;
        this.midY = pixelSize/2;
    }

    public int getPixel(int x,int y)
    {
        float exp = 1.0f;
        int distX = Math.abs(x-midX);
        int distY = Math.abs(y-midY);

        int off = (int)(Math.pow(distX,exp)+Math.pow(distY,exp));
        return Color.rgb(
                Math.min(255,r+off),
                Math.min(255,g+off),
                Math.min(255,b+off));
    }
}

