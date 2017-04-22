package zerjioang.onthestreet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.PlaceDetailsController;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Place;

public class PlaceDetailsActivity extends AbstractBaseActivity {

    private ShareActionProvider mShareActionProvider;
    private PlaceDetailsController controller;
    private MenuItem shareItemMenu;

    private TextView textViewDetailsTitle;
    private TextView textViewDetailsDescription;
    private TextView textViewDetailsLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        controller = new PlaceDetailsController(this);
        controller.initGridView();

        //get place data
        controller.setLastSelectedPlace(DataManager.getInstance().getLastViewedPlace());

        //get ui elements
        textViewDetailsTitle = (TextView) findViewById(R.id.textViewDetailsTitle);
        textViewDetailsDescription = (TextView) findViewById(R.id.textViewDetailsDescription);
        textViewDetailsLocation = (TextView) findViewById(R.id.textViewDetailsLocation);

        controller.initContactList();

        //init data
        Place selectedPlace = controller.getLastSelectedPlace();
        if(selectedPlace!=null){
            textViewDetailsTitle.setText(selectedPlace.getName());
            textViewDetailsDescription.setText(selectedPlace.getDescription());
            textViewDetailsLocation.setText("LOCATION: "+selectedPlace.getLocation());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_place, menu);

        // Locate MenuItem with ShareActionProvider
        shareItemMenu = menu.findItem(R.id.menu_item_share);
        // Fetch and store ShareActionProvider
        mShareActionProvider = new ShareActionProvider(this);
        MenuItemCompat.setActionProvider(shareItemMenu, mShareActionProvider);
        mShareActionProvider.setShareIntent(controller.getShareIntent());

        mShareActionProvider.setShareHistoryFileName(
                ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_camera:
                controller.pickImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.onActivityResult(requestCode, resultCode, data);
    }
}
