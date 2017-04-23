package zerjioang.onthestreet.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.AbstractCode;
import zerjioang.onthestreet.controller.ListActivityController;

public class ListActivity extends AbstractBaseActivity{

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private RecyclerView placesRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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
