package com.a7i7Dev.android.com.pixelate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    protected static ProgressBar mProgress;
    private Pixelator pixelator;
    private ImageView imageView;
    private SeekBar pixelSizeBar;
    private Bitmap inputBitmap;

    private Thread pixelatorThread;
    private Thread progressBarThread;
    private Spinner effectSpinner;
    private static String[] effectNames = {"Plain Fill", "Mushy", "Lumen Center", "Lumen Side"};
    public void savePixelatedImage(View view)
    {
        if(pixelator!=null && pixelator.getStatus()==Pixelator.FINISHED) {
            try {

                String path = Environment.getExternalStorageDirectory().toString()+"/Pixelator";
                File dir = new File(path);
                if(!dir.exists())
                    dir.mkdirs();

                long timestamp = new Date().getTime();
                String filename = "pixelated_"+timestamp+".jpg";
                Toast.makeText(getApplicationContext(), "Saving image as "+filename, Toast.LENGTH_LONG).show();
                File file = new File(path, filename);
                OutputStream fOut = new FileOutputStream(file);
                Bitmap pixelatedBitmap = pixelator.getPixelatedImage();
                pixelatedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();
            }
            catch(Exception e) {
                Toast.makeText(getApplicationContext(), "Error while saving", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        else
            Toast.makeText(getApplicationContext(),"Result not yet ready",Toast.LENGTH_LONG).show();
    }

    public void startEffects(View view)
    {
        if(pixelator!=null && pixelator.getStatus()==Pixelator.RUNNING)
        {
            pixelator.stopRunning();    //stops pixelatorThread
            pixelator=null;             //stops progressBarThread
            mProgress.setProgress(0);
        }

        final int pixelSize = pixelSizeBar.getProgress() + 1;
        PixelEffect pixelEffect = null;
        String chosenEffect = effectSpinner.getSelectedItem().toString();

        if(chosenEffect.equals("Plain Fill"))
            pixelEffect = new PlainFill(pixelSize);
        else if(chosenEffect.equals("Mushy"))
            pixelEffect = new Mushy(pixelSize);
        else if(chosenEffect.equals("Lumen Center"))
            pixelEffect = new LumenCenter(pixelSize);
        else
            pixelEffect = new LumenSide(pixelSize);

        pixelator = new Pixelator(inputBitmap, pixelEffect, pixelSize);

        pixelatorThread = new Thread(new Runnable() {
            public void run() {
                pixelator.pixelateImage();
            }
        });
        pixelatorThread.start();

        progressBarThread = new Thread(new Runnable() {
            public void run() {

                while(pixelator==null || pixelator.getPercentComplete()<100) {
                    if (pixelator == null)
                        continue;
                    mProgress.setProgress(pixelator.getPercentComplete());
                }
                mProgress.setProgress(0);

                CameraActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(pixelator.getPixelatedImage());
                    }
                });
            }
        });
        progressBarThread.start();
    }

    public void addEffectsOnSpinner()
    {
        effectSpinner = (Spinner) findViewById(R.id.effectSpinner);
        List<String> listOfEffects = Arrays.asList(effectNames);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfEffects);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        effectSpinner.setAdapter(dataAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        addEffectsOnSpinner();
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        imageView = (ImageView) findViewById(R.id.inputImageView);
        pixelSizeBar = (SeekBar) findViewById(R.id.seekBar);

        Intent intent = getIntent();
        Uri imageUri = (Uri) intent.getParcelableExtra("IMAGE_URI");
        if(imageUri==null)
            return;
        try {
            inputBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        if(!inputBitmap.isMutable())
            inputBitmap = inputBitmap.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(inputBitmap);
    }
}
