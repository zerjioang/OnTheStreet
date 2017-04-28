package zerjioang.onthestreet.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Place;
import zerjioang.onthestreet.widget.SimpleWidgetProvider;


/**
 * Created by .local on 23/04/2017.
 */

public class GPSLocationManagerService extends Service implements LocationListener {

    private static final String TAG = "LocationService";
    private static final long MINIMUM_TIME = 1000; //ms update every 1000 ms
    private static final float MINIMUM_DISTANCE = 1;
    private static boolean gpsStatus;

    private LocationManager locationManager;
    private Location lastBestLocation;

    public GPSLocationManagerService() {
    }

    public void build(Context c) {
        locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsStatus){
            buildAlertMessageNoGps(c);
        }
    }

    public void start() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else {
            try{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME, MINIMUM_DISTANCE, this);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            try{
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MINIMUM_TIME, MINIMUM_DISTANCE, this);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void buildAlertMessageNoGps(final Context c) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(R.string.dialog_gps_off_message)
                .setTitle(R.string.dialog_gps_off_title)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        c.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //open gps settings
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    // getters and setters


    public Context getContext() {
        return getBaseContext();
    }

    // LocationListener methods

    @Override
    public void onLocationChanged(Location location) {
        lastBestLocation = getLastBestLocation();
        Toast.makeText(getContext(), "Location changed: Lat: " + location.getLatitude() + " Lng: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + location.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + location.getLatitude();
        Log.v(TAG, latitude);

        /*------- To get city name from coordinates -------- */
        String cityName = null;
        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses.size() > 0) {
                Log.d(TAG, addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;
        DataManager.getInstance().setLatitude(location.getLatitude());
        DataManager.getInstance().setLongitude(location.getLongitude());
        DataManager.getInstance().setUserLocationName(cityName);
        DataManager.getInstance().updatePlacesDistances();
        //show nearest place notification
        Place p = DataManager.getInstance().getNearestPlace();
        if(p!=null){
            showNotification("Nearest place", p.getName());
        }
        //notify changes to widgets
        SimpleWidgetProvider.onDemandWidgetRefreshRequest(getContext());
        Log.d(TAG, s);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    /**
     * @return the last know best location
     */
    private Location getLastBestLocation() {
        //dont worry about de warnings, permissions are already been granted previously
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long gpsLocationTime = 0;
        if (null != locationGPS) { gpsLocationTime = locationGPS.getTime(); }

        long netLocationTime = 0;

        if (null != locationNet) {
            netLocationTime = locationNet.getTime();
        }

        if ( 0 < gpsLocationTime - netLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }

    private void showNotification(String title, String msg){
    //https://stackoverflow.com/questions/1207269/sending-a-notification-from-a-service-in-android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            // prepare intent which is triggered if the
            // notification is selected
            //Intent intent = new Intent(this, NotificationReceiver.class);
            //PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

            // build notification
            // the addAction re-use the same intent to keep the example short
            Notification n = null;
            n = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //.setContentIntent(pIntent)
                    .setAutoCancel(true).build();

                        /*.addAction(R.drawable.icon, "Call", pIntent)
                        .addAction(R.drawable.icon, "More", pIntent)
                        .addAction(R.drawable.icon, "And more", pIntent).build()*/


            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // Code to execute when the service is first created
        DataManager.getInstance().setLocationStatus(getContext(), true);
        this.build(getContext());
        this.start();
    }

    @Override
    public void onDestroy() {
        //this.stop();
        DataManager.getInstance().setLocationStatus(getContext(), false);
    }
}
