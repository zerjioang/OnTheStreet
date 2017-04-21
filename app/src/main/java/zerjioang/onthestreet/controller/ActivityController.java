package zerjioang.onthestreet.controller;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.holder.PlaceHolderAdapter;
import zerjioang.onthestreet.model.pojox.Place;
import zerjioang.onthestreet.ui.activity.ListActivity;
import zerjioang.onthestreet.ui.activity.NewPlaceActivity;
import zerjioang.onthestreet.ui.activity.PlaceDetailsActivity;

/**
 * Created by .local on 21/03/2017.
 */

public class ActivityController extends BaseController {

    private final ListActivity listActivity;
    private LinearLayoutManager mLinearLayoutManager;
    private PlaceHolderAdapter recyclerAdapter;

    public ActivityController(ListActivity listActivity) {
        super(listActivity);
        this.listActivity = listActivity;
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
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(getActivity(), PlaceDetailsActivity.class);
                getActivity().startActivity(t);
            }
        };
        //set adapter for data
        recyclerAdapter = new PlaceHolderAdapter(placeList, listener);
        placesRecyclerView.setAdapter(recyclerAdapter);
    }

    public void reloadRecyclerView() {
        recyclerAdapter.notifyDataSetChanged();
    }
}
