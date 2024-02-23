package com.sandipbhattacharya.spaceshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Arrays;
import java.util.List;

public class PowerShot extends Shot {
    Shot shot2;
    Shot shot3;

    public PowerShot(Context context, int shx, int shy) {
        super(context, shx, shy);
        // Create a new Shot object to the right of the original
        this.shot2 = new Shot(context, shx, shy);

        // Create a new Shot object to the left of the original
        this.shot3 = new Shot(context, shx, shy);
    }

    @Override
    public Bitmap getShot() {
        Bitmap combinedShot = Bitmap.createBitmap(shot2.getShot().getWidth()  + shot3.getShot().getWidth()  + super.getShot().getWidth(), super.getShot().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(combinedShot);
        canvas.drawBitmap(super.getShot(), super.getShot().getWidth() + shot3.getShot().getWidth(), 0, null);
        canvas.drawBitmap(shot2.getShot(), super.getShot().getWidth() , 120, null);
        canvas.drawBitmap(shot3.getShot(), 0, 0, null);
        return combinedShot;
    }
}