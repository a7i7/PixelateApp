package com.sunshine.android.com.pixelate;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    static ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Intent intent = getIntent();
        Uri imageUri = (Uri) intent.getParcelableExtra("IMAGE_URI");
        if(imageUri==null)
            return;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, imageUri);
            ImageView imageView = (ImageView) findViewById(R.id.inputImageView);
            imageView.setImageBitmap(bitmap);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
