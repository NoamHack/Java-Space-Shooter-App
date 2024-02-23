package com.sandipbhattacharya.spaceshooter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int batteryScale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPercentage = (int) ((float) batteryLevel / (float) batteryScale * 100f);

        Log.d("BatteryReceiver", "Battery level: " + batteryPercentage);

        if (batteryPercentage <= 20) {
            Toast.makeText(context, "Battery is low (" + batteryPercentage + "%)", Toast.LENGTH_SHORT).show();
        }
    }
}
