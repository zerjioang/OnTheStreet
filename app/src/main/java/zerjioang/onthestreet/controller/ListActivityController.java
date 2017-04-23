package zerjioang.onthestreet.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.adapter.RecyclerViewClickListener;
import zerjioang.onthestreet.model.holder.place.PlaceListAdapter;
import zerjioang.onthestreet.model.pojox.Place;
import zerjioang.onthestreet.ui.activity.ListActivity;
import zerjioang.onthestreet.ui.activity.NewPlaceActivity;
import zerjioang.onthestreet.ui.activity.PlaceDetailsActivity;

/**
 * Created by .local on 21/03/2017.
 */

public class ListActivityController extends AbstractBaseController {

    private final ListActivity listActivity;
    private LinearLayoutManager mLinearLayoutManager;
    private PlaceListAdapter recyclerAdapter;

    private StreetLocationManager locator;
    
    public ListActivityController(ListActivity listActivity) {
        super(listActivity);
        this.listActivity = listActivity;
        this.locator = new StreetLocationManager(listActivity);
    }

    public void initFloatingActionButtonEvent(FloatingActionButton fab) {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(listActivity, NewPlaceActivity.class);
                listActivity.startActivity(t);
                //animate
                listActivity.overridePendingTransition(R.anim.scale_up, R.anim.bottom_down);
            }
        });
    }

    public void initRecyclerView(RecyclerView placesRecyclerView, Context c) {
        mLinearLayoutManager = new LinearLayoutManager(c);
        placesRecyclerView.setLayoutManager(mLinearLayoutManager);
        //read data from file
        ArrayList<Place> placeList = DataManager.getInstance().readPlaceList(listActivity);
        //create item click listener
        RecyclerViewClickListener  listener = new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                DataManager manager = DataManager.getInstance();
                manager.setLastViewedPlace(manager.getPlaceAt(position));
                manager.setLastViewedPlacePosition(position);
                showChooserDialog(position);
            }
        };
        //set adapter for data
        recyclerAdapter = new PlaceListAdapter(placeList, listener);
        placesRecyclerView.setAdapter(recyclerAdapter);
    }

    public void reloadRecyclerView() {
        recyclerAdapter.notifyDataSetChanged();
    }

    private void showChooserDialog(final int position) {
        final CharSequence[] items = {"View", "Edit", "Delete", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Place");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(items[0])) {
                    viewPlace();
                } else if (items[item].equals(items[1])) {
                    editPlace();
                } else if (items[item].equals(items[2])) {
                    deletePlace(position);
                } else if (items[item].equals(items[3])) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void editPlace() {
        Intent t = new Intent(getActivity(), NewPlaceActivity.class);
        t.putExtra("editmode", true);
        getActivity().startActivity(t);
    }

    private void viewPlace() {
        Intent t = new Intent(getActivity(), PlaceDetailsActivity.class);
        getActivity().startActivity(t);
    }

    private void deletePlace(int position) {
        DataManager.getInstance().deletePlace(getActivity(), position);
        recyclerAdapter.notifyDataSetChanged();
    }
}
