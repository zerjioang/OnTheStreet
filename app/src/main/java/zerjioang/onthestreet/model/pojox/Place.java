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
    private ArrayList<Contact> listaContact;

    public Place(String name, String place, String description) {
        this.name = name;
        this.place = place;
        this.description = description;
        this.listaContact = new ArrayList<>();
    }

    public Place() {
        this.name = "";
        this.description = "";
        this.place = "";
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
}
