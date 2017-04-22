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

    private String name, location, description;
    private ArrayList<Contact> listaContact;
    private double lat, lon;

    public Place(String name, String place, String description) {
        this.name = name;
        this.location = place;
        this.description = description;
        this.listaContact = new ArrayList<>();
    }

    public Place() {
        this.name = "";
        this.description = "";
        this.location = "";
        this.listaContact = new ArrayList<>();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public View getView() {
        return null;
    }

    @Override
    public void initView(View v) {

    }

    @Override
    public int getLayout() {
        return R.layout.item_recyclerview_place;
    }

    public ArrayList<Contact> getListaContact() {
        return listaContact;
    }

    public void setListaContact(ArrayList<Contact> listaContact) {
        this.listaContact = listaContact;
    }

    public void addContact(Contact contact) {
        this.listaContact.add(contact);
    }

    public int getContactCount() {
        return this.listaContact.size();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
