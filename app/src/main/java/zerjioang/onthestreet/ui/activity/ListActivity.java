package zerjioang.onthestreet.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.ListActivityController;
import zerjioang.onthestreet.model.adapter.RecyclerViewClickListener;

public class ListActivity extends AbstractBaseActivity implements RecyclerViewClickListener{

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
        ((ListActivityController)controller).initFloatingActionButtonEvent(fab);
        ((ListActivityController)controller).initRecyclerView(placesRecyclerView, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((ListActivityController)controller).reloadRecyclerView();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }
}
