package zerjioang.onthestreet.model.holder.place;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.adapter.RecyclerViewClickListener;
import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 21/03/2017.
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.PlaceHolder> {

    private final List<Place> list;
    private final RecyclerViewClickListener  listener;

    public PlaceListAdapter(List<Place> list, RecyclerViewClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_place, parent, false);
        return new PlaceHolder(v , listener);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        Place place = list.get(position);
        holder.bind(place, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PlaceHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TextView placeName;
        private TextView placeLocation;
        private TextView placeContactNumber;
        private final TextView itemPlaceDistanceTextView;
        private final RecyclerViewClickListener listener;

        public PlaceHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            placeName = (TextView) itemView.findViewById(R.id.itemPlaceNameTextView);
            placeLocation = (TextView) itemView.findViewById(R.id.itemPlaceLocationTextView);
            placeContactNumber = (TextView) itemView.findViewById(R.id.itemPlaceContactNumberTextView);
            itemPlaceDistanceTextView = (TextView) itemView.findViewById(R.id.itemPlaceDistanceTextView);
            this.listener = listener;
        }

        public void bind(final Place place, RecyclerViewClickListener listener) {
            this.placeName.setText(place.getName());
            this.placeLocation.setText(place.getLocation());
            this.placeContactNumber.setText("Contacts: " + place.getContactCount());
            this.itemPlaceDistanceTextView.setText(place.getDistanceString()+" km");
            if (listener != null) {
                DataManager.getInstance().setLastViewedPlace(place);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            listener.recyclerViewListClicked(view, getAdapterPosition());
        }
    }
}
