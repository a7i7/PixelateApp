package com.a7i7Dev.android.com.pixelate;
import android.graphics.Color;

public class PlainFill extends PixelEffect
{

    PlainFill(int pixelSize)
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.pixelSize = pixelSize;
    }

    @Override
    public int getPixel(int x,int y) {
        return Color.rgb(r,g,b);
    }

}
