package zerjioang.onthestreet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import zerjioang.onthestreet.service.GPSLocationManagerService;

/**
 * Created by .local on 22/04/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //start background service on boot
        context.startService(new Intent(context, GPSLocationManagerService.class));
    }
}