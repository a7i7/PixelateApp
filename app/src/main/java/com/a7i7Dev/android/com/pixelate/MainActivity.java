package com.a7i7Dev.android.com.pixelate;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private int CAPTURE_IMAGE = 1;
    private int PICK_IMAGE = 2;
    private Uri imageUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap inputPhoto = null;

        if(resultCode!=RESULT_OK || (requestCode!=PICK_IMAGE && requestCode!=CAPTURE_IMAGE))
            return;

        if ( requestCode == PICK_IMAGE && data != null && data.getData() != null)
            imageUri = data.getData();

        try
        {
            inputPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        if(inputPhoto!=null)
        {
            Intent i = new Intent(this,CameraActivity.class);
            i.putExtra("IMAGE_URI",imageUri);
            startActivity(i);
        }
    }

    public void onClickGalleryButton(View view)
    {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    public void onClickCameraButton(View view) throws IOException
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
