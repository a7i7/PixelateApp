package com.a7i7Dev.android.com.pixelate;

import android.graphics.Color;

/**
 * Created by Welcome on 14-08-2016.
 */
public abstract class PixelEffect {

    protected int r,g,b,pixelSize;

    public void refresh(int r,int g,int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public abstract int getPixel(int x,int y);

}

