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
import zerjioang.onthestreet.ui.activity.PlaceDetailsActivity;

/**
 * Created by .local on 23/04/2017.
 */

public class SimpleWidgetProvider extends AppWidgetProvider {

    private static final String REFRESH_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";

    private static void updateWidgetValues(AppWidgetManager appWidgetManager, Context context, RemoteViews remoteViews) {

        Intent inet = new Intent(context, PlaceDetailsActivity.class);
        inet.putExtra("widget", true);
        inet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pend = PendingIntent.getActivity(context, 2, inet, PendingIntent.FLAG_ONE_SHOT);
        remoteViews.setOnClickPendingIntent(R.id.widgetBtn, pend);


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
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;
        //iterate over all user widgets
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);

            updateWidgetValues(appWidgetManager, context, remoteViews);
            openActivityFromWidget(context);

            /*Intent intent = new Intent(context, SimpleWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            //pending intent is done to make an on demand update
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            */

        }
    }

    private void openActivityFromWidget(Context context) {
        Intent intent = new Intent(context, PlaceDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Get the layout for the App Widget and attach an on-click listener to the button
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.simple_widget);
        views.setOnClickPendingIntent(R.id.widgetBtn, pendingIntent);
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

    public static void onDemandWidgetRefreshRequest(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);
        ComponentName thisWidget = new ComponentName(context, SimpleWidgetProvider.class);
        updateWidgetValues(appWidgetManager, context, remoteViews);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
