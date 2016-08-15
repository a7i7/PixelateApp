package com.a7i7Dev.android.com.pixelate;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Welcome on 14-08-2016.
 */
public class Pixelator {

    private Bitmap inputImage;
    private Bitmap outputImage;
    private int pixelSize;
    private PixelEffect pixelEffect;
    public int percentComplete;

    Pixelator(Bitmap inputImage,PixelEffect pixelEffect,int pixelSize)
    {
        this.inputImage = inputImage;
        this.pixelEffect = pixelEffect;
        this.outputImage = inputImage.copy(Bitmap.Config.ARGB_8888, true);
        this.pixelSize = pixelSize;
        this.percentComplete = 0;
    }

    public int getPercentComplete()
    {
        return percentComplete;
    }

    public Bitmap pixelateImage()
    {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for(int x=0;x<inputImage.getHeight();x+=pixelSize)
        {
            percentComplete = (x*100)/height;
            for(int y=0;y<inputImage.getWidth();y+=pixelSize)
            {
                int x1 = x, y1 = y;
                int x2 = Math.min(height, x+pixelSize), y2 = Math.min(width, y+pixelSize);
                float rAvg = 0.0f, gAvg = 0.0f, bAvg = 0.0f;
                int numPixels = (x2-x1)*(y2-y1);

                for(int p=x1;p<x2;p++)
                {
                    for(int q=y1;q<y2;q++)
                    {
                        int color = inputImage.getPixel(q,p);
                        rAvg += Color.red(color);
                        gAvg += Color.green(color);
                        bAvg += Color.blue(color);
                    }
                }
                rAvg/=numPixels;
                gAvg/=numPixels;
                bAvg/=numPixels;

                int r = Math.round(rAvg);
                int g = Math.round(gAvg);
                int b = Math.round(bAvg);

                pixelEffect.refresh(r,g,b);

                for(int p=0;p<x2-x1;p++)
                {
                    for(int q=0;q<y2-y1;q++)
                    {
                        int pixelColor = pixelEffect.getPixel(p,q);
                        outputImage.setPixel(y+q,x+p,pixelColor);
                    }
                }
            }
        }
        percentComplete = 100;
        return outputImage;
    }

    public Bitmap getPixelatedImage()
    {
        return outputImage;
    }

}
