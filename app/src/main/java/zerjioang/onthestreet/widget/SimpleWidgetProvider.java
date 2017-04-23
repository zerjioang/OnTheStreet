package zerjioang.onthestreet.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 23/04/2017.
 */

public class SimpleWidgetProvider extends AppWidgetProvider {

    private static final String REFRESH_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        //iterate over all user widgets
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);

            updateWidgetValues(remoteViews);

            Intent intent = new Intent(context, SimpleWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            //pending intent is done to make an on demand update
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }
    }

    private static void updateWidgetValues(RemoteViews remoteViews) {
        Place nearest = DataManager.getInstance().getNearestPlace();
        if(nearest!=null){
            remoteViews.setTextViewText(R.id.textViewWidgetPlaceTitle, nearest.getName());
            remoteViews.setTextViewText(R.id.textViewWidgetDistance, nearest.getDistanceString()+" KM");
        }
        else{
            remoteViews.setTextViewText(R.id.textViewWidgetPlaceTitle, "No data");
            remoteViews.setTextViewText(R.id.textViewWidgetDistance, "0 KM");
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(REFRESH_ACTION)) {
            // refresh all your widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, SimpleWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetLayout);
        }
        super.onReceive(context, intent);
    }

    public static void sendRefreshBroadcast(Context context) {
        /*Intent intent = new Intent(REFRESH_ACTION);
        intent.setComponent(new ComponentName(context, SimpleWidgetProvider.class));
        context.sendBroadcast(intent);*/
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
        ComponentName thisWidget = new ComponentName(context, SimpleWidgetProvider.class);
        updateWidgetValues(remoteViews);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
