package zerjioang.onthestreet.data;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 20/04/2017.
 */

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private Place lastViewedPlace;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private ArrayList<Place> placeList;

    private DataManager() {
        placeList = new ArrayList<>();
    }

    public void addPlace(Context c, Place p) {
        this.placeList.add(p);
        //save places
        savePlaceList(c);
    }

    public void savePlaceList(Context context) {
        try {
            File f = new File(context.getFilesDir(), "place.data");
            ObjectOutputStream fos = new ObjectOutputStream(new FileOutputStream(f));
            fos.writeObject(this.placeList);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList readPlaceList(Context context) {
        try {
            File f = new File(context.getFilesDir(), "place.data");
            ObjectInputStream fos = new ObjectInputStream(new FileInputStream(f));
            try {
                this.placeList = (ArrayList) fos.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                this.placeList = new ArrayList<>();
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            this.placeList = new ArrayList<>();
        }
        return this.placeList;
    }

    public ArrayList<Place> getPlaceList(){
        return this.placeList;
    }

    public void setLastViewedPlace(Place lastViewedPlace) {
        this.lastViewedPlace = lastViewedPlace;
    }

    public Place getLastViewedPlace() {
        return lastViewedPlace;
    }
}
