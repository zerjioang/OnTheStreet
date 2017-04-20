package zerjioang.onthestreet.model.pojox;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.model.adapter.IAbstractView;

/**
 * Created by .local on 21/03/2017.
 */

public class Place implements IAbstractView, Serializable {

    private String name, place, description;
    private ArrayList<Contact> listContanct;

    public Place(String name, String place, String description) {
        this.name = name;
        this.place = place;
        this.description = description;
        this.listContanct = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public View getView() {
        return null;
    }

    @Override
    public void initView(View v) {

    }

    @Override
    public int getLayout() {
        return R.layout.recyclerview_places_item;
    }

    public ArrayList<Contact> getListContanct() {
        return listContanct;
    }

    public void setListContanct(ArrayList<Contact> listContanct) {
        this.listContanct = listContanct;
    }
}
