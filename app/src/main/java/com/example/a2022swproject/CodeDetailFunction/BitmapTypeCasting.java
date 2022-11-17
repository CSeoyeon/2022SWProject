package com.example.a2022swproject.CodeDetailFunction;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class BitmapTypeCasting {

    public BitmapTypeCasting(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String bitmapToString(Bitmap bitmap){
        String img = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        img = Base64.getEncoder().encodeToString(bytes);
        return img;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap stringToBitmap(String imgString) {
        Bitmap bitmap = null;
        byte[] bytes = Base64.getDecoder().decode(imgString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }


}
