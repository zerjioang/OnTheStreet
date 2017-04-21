package zerjioang.onthestreet.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 21/03/2017.
 */

public class PlaceHolder extends RecyclerView.ViewHolder{

    private TextView placeName;
    private TextView placeLocation;
    private TextView placeContactNumber;

    public PlaceHolder(View itemView) {
        super(itemView);
        placeName = (TextView)itemView.findViewById(R.id.itemPlaceNameTextView);
        placeLocation = (TextView)itemView.findViewById(R.id.itemPlaceLocationTextView);
        placeContactNumber = (TextView)itemView.findViewById(R.id.itemPlaceContactNumberTextView);
    }

    public void bind(Place place) {
        this.placeName.setText(place.getName());
        this.placeLocation.setText(place.getPlace());
        this.placeContactNumber.setText("Contacts: "+place.getContactCount());
    }
}
