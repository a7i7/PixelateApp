package com.a7i7Dev.android.com.pixelate;
import android.graphics.Color;

public class LumenSide extends PixelEffect {

    LumenSide(int pixelSize) {
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.pixelSize = pixelSize;
    }

    @Override
    public int getPixel(int x,int y) {
        float exp = 1.0f;
        int off = (int)(Math.pow(x,exp)+Math.pow(y,exp));
        return Color.rgb(
                safeAdd(r,off),
                safeAdd(g,off),
                safeAdd(b,off));
    }
}
