package zerjioang.onthestreet.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.base.AbstractCode;
import zerjioang.onthestreet.controller.ListActivityController;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.ui.activity.base.AbstractBaseActivity;

public class ListActivity extends AbstractBaseActivity {

    private static final int GPS_PERMISSION_REQUEST = 0;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView placesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        //check for runtime permissions
        checkPermissions();

        //get ui elements
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        placesRecyclerView = (RecyclerView) findViewById(R.id.placesRecyclerView);

        setSupportActionBar(toolbar);

        //create activity controller
        controller = new ListActivityController(this);
        getThisController().initFloatingActionButtonEvent(fab);
        getThisController().initRecyclerView(placesRecyclerView, this);
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                //request permissions
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        GPS_PERMISSION_REQUEST);
            }
        }

        else{
            //enable background services
            DataManager.getInstance().enabledServices(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length == 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    //enable background services
                    DataManager.getInstance().enabledServices(this);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Location features disabled", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public boolean showSearchIcon() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getThisController().reloadRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                getThisController().search(new AbstractCode() {
                    @Override
                    public void execute(Object o) {
                        String nameToFilter = (String) o;
                        Toast.makeText(getActivity(), nameToFilter, Toast.LENGTH_SHORT).show();
                        getThisController().filterByName(nameToFilter);
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private ListActivityController getThisController() {
        return (ListActivityController)controller;
    }
}
