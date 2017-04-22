package zerjioang.onthestreet.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.PlaceDetailsController;

public class PlaceDetailsActivity extends AbstractBaseActivity {

    private ShareActionProvider mShareActionProvider;
    private MenuItem shareItemMenu;
    private PlaceDetailsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        controller = new PlaceDetailsController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //https://stackoverflow.com/questions/19358510/why-menuitemcompat-getactionprovider-returns-null
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
}
