package com.a7i7Dev.android.com.pixelate;
import android.graphics.Bitmap;
import android.graphics.Color;

public class Pixelator {

    private Bitmap inputImage;
    private Bitmap outputImage;
    private int pixelSize;
    private PixelEffect pixelEffect;
    private int status;
    public int percentComplete;

    public static final int NOT_STARTED = 0;
    public static final int RUNNING = 1;
    public static final int FINISHED = 2;

    private boolean keepRunning;

    Pixelator(Bitmap inputImage,PixelEffect pixelEffect,int pixelSize) {
        this.inputImage = inputImage;
        this.pixelEffect = pixelEffect;
        this.outputImage = inputImage.copy(Bitmap.Config.ARGB_8888, true);
        this.pixelSize = pixelSize;
        this.percentComplete = 0;
        this.status = NOT_STARTED;
        this.keepRunning = true;
    }

    public void stopRunning()
    {
        keepRunning = false;
    }

    public int getPercentComplete() {
        return percentComplete;
    }

    public Bitmap pixelateImage() {
        this.status = RUNNING;
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        for(int x=0; x<height && keepRunning; x+=pixelSize)
        {
            for(int y=0; y<width && keepRunning; y+=pixelSize)
            {
                percentComplete = (100*(x*width+y))/(height*width);
                int x1 = x, y1 = y;
                int x2 = Math.min(height, x+pixelSize), y2 = Math.min(width, y+pixelSize);
                int rAvg = 0, gAvg = 0, bAvg = 0;
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

                int r = rAvg;
                int g = gAvg;
                int b = bAvg;

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
        this.status = FINISHED;
        return outputImage;
    }

    public int getStatus()
    {
        return status;
    }

    public Bitmap getPixelatedImage() {
        if(status==FINISHED)
            return outputImage;
        return null;
    }

}
