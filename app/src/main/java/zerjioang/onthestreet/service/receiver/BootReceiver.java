package zerjioang.onthestreet.service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import zerjioang.onthestreet.service.BackgroundService;

/**
 * Created by .local on 22/04/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BackgroundService.class));
    }
}