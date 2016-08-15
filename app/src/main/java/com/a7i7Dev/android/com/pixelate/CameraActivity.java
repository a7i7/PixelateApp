package com.a7i7Dev.android.com.pixelate;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    static ContentResolver cr;
    protected static ProgressBar mProgress;
    private Handler mHandler = new Handler();
    Pixelator pixelator = null;
    private ImageView imageView = null;
    public void startEffects(View view)
    {

        Intent intent = getIntent();
        Uri imageUri = (Uri) intent.getParcelableExtra("IMAGE_URI");
        if(imageUri==null)
            return;
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(cr, imageUri);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        if(!bitmap.isMutable())
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        final int pixelSize = 50;

        pixelator = new Pixelator(bitmap, new LumenCenter(pixelSize),pixelSize);
        new Thread(new Runnable() {
            public void run() {
                pixelator.pixelateImage();
            }
        }
        ).start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        imageView = (ImageView) findViewById(R.id.inputImageView);

        new Thread(new Runnable() {
            public void run() {
                    while(pixelator==null || pixelator.getPercentComplete()<100) {
                        if (pixelator == null)
                            continue;
                        Log.i("FUCK", pixelator.getPercentComplete() + "");
                        mProgress.setProgress(pixelator.getPercentComplete());
                    }

                    CameraActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(pixelator.getPixelatedImage());
                        }
                    });
                }
            }
        ).start();
//        imageView.setImageBitmap(pixelator.getPixelatedImage());



    }
}
