package zerjioang.onthestreet.model.holder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 21/03/2017.
 */

public class PlaceHolderAdapter extends RecyclerView.Adapter<PlaceHolder> {

    private final List<Place> list;
    private final View.OnClickListener listener;

    public PlaceHolderAdapter(List<Place> list, View.OnClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_places_item, parent, false);
        return new PlaceHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
