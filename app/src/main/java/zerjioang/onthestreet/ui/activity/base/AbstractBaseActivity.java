package zerjioang.onthestreet.ui.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.base.AbstractBaseController;

/**
 * Created by .local on 19/04/2017.
 */

public abstract class AbstractBaseActivity extends AppCompatActivity {

    protected AbstractBaseController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(showSearchIcon()){
            inflater.inflate(R.menu.menu_main_with_search, menu);
        }
        else{
            inflater.inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                controller.showSettings();
                return true;
            case R.id.menu_item_about:
                controller.showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected Activity getActivity() {
        return this;
    }

    protected boolean getFromExtras(String id, boolean defaultVal) {
        Intent t = getIntent();
        if(t!=null){
            Bundle extras = t.getExtras();
            if (extras != null) {
                return t.getBooleanExtra(id, defaultVal);
            }
        }
        return false;
    }

    public abstract boolean showSearchIcon();
}
