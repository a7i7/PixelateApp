package com.a7i7Dev.android.com.pixelate;

import android.graphics.Color;

/**
 * Created by Welcome on 14-08-2016.
 */

public class PlainFill extends PixelEffect
{

    PlainFill(int pixelSize)
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.pixelSize = pixelSize;
    }

    public int getPixel(int x,int y)
    {
        return Color.rgb(r,g,b);
    }

}
