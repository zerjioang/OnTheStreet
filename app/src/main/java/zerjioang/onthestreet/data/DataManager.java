package zerjioang.onthestreet.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import zerjioang.onthestreet.model.holder.place.PlaceListAdapter;
import zerjioang.onthestreet.model.pojox.Place;
import zerjioang.onthestreet.service.GPSLocationManagerService;
import zerjioang.onthestreet.util.Util;

/**
 * Created by .local on 20/04/2017.
 */

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private static final double NO_DATA = -1;
    private Place lastViewedPlace;
    private int lastViewedPlacePosition;
    private double longitude;
    private double latitude;
    private String userLocationName;
    private PlaceListAdapter placeListAdapter;
    private Place nearestPlace;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private ArrayList<Place> placeList;

    private DataManager() {
        placeList = new ArrayList<>();
        this.latitude = NO_DATA;
        this.longitude = NO_DATA;
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
                this.placeList = generateDefaultPlaceList();
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            this.placeList = generateDefaultPlaceList();
        }
        return this.placeList;
    }

    private ArrayList<Place> generateDefaultPlaceList() {
        ArrayList<Place> places = new ArrayList<>();
        places.add(new Place(
                "Universidad de Deusto",
                "Deusto, Bilbao",
                "La Universidad de Deusto (en euskera Deustuko Unibertsitatea) es una universidad privada regida por la Compañía de Jesús, con dos campus en el distrito de Deusto de la ciudad de Bilbao y en San Sebastián, País Vasco (España), además de una sede en Madrid. Actualmente su rector es José María Guibert Ucín.1 Es la universidad privada española más antigua,2 y una de las más prestigiosas y conocidas.3 Según el estudio \"U-Ranking\" elaborado por el Instituto Valenciano de Investigaciones Económicas (IVIE) y la Fundación BBVA en 2015, la Universidad de Deusto es el líder en el ámbito de la docencia entre las principales universidades españolas.",
                43.271632, -2.939673
        ));
        places.add(new Place(
                "Euskal Herriko Unibertsitatea",
                "Leioa, Bilbao",
                "La Universidad del País Vasco (en euskera: Euskal Herriko Unibertsitatea; UPV/EHU) es la universidad pública de la comunidad autónoma del País Vasco (España), articulada en tres campus situados en cada una de las tres provincias de la comunidad: Vizcaya, Guipúzcoa y Álava. Heredera de la Universidad de Bilbao, inicialmente estaba compuesta compuesta por la Facultad de Ciencias Económicas y Empresariales de Sarriko (1955), Medicina (1968) y Ciencias (1968), se le unieron con la Ley General de Educación la Escuela Naútica (1784), la Escuela Universitaria de Estudios Empresariales de Bilbao (1818) y así como las Escuelas Técnicas de Ingenieros, hasta llegar la treintena de centros que la componen en la actualidad.",
                43.330863, -2.968068
        ));
        places.add(new Place(
                "Mondragon Unibertsitatea",
                "Mondragon, Gipuzkua",
                "La Universidad de Mondragón (en euskera y oficialmente Mondragon Unibertsitatea) es una universidad privada de iniciativa social sin ánimo de lucro perteneciente a la corporación Mondragón ubicada en Mondragón, Guipúzcoa (España).\n" +
                        "Ha sido declarada de utilidad pública. Su pertenencia a la Corporación Mondragón le permite mantenerse cercana al mundo de la empresa facilitando a sus alumnos el contacto con el mercado laboral. Actualmente tiene en torno a 7.000 alumnos cursando 22 titulaciones de grado,1 13 másteres2 y cursos de experto universitario.",
                43.062885, -2.506989
        ));
        return places;
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

    public Place getPlaceAt(int position) {
        return this.placeList.get(position);
    }

    public void deletePlace(Activity activity, int position) {
        this.placeList.remove(position);
        this.savePlaceList(activity);
    }

    public void replaceAt(Activity activity, int position, Place p) {
        this.placeList.remove(position);
        this.placeList.add(position, p);
        this.savePlaceList(activity);
    }

    public int getLastViewedPlacePosition() {
        return lastViewedPlacePosition;
    }

    public void setLastViewedPlacePosition(int lastViewedPlacePosition) {
        this.lastViewedPlacePosition = lastViewedPlacePosition;
    }

    public ArrayList<Place> getPlacesByName(String nameToFilter) {
        if(nameToFilter.trim().length()==0){
            return this.placeList; //return original list if empty
        }
        else{
            //run filter
            ArrayList<Place> matches = new ArrayList<>();
            for(Place p : placeList){
                if(p.getName().toLowerCase().contains(nameToFilter.toLowerCase())){
                    matches.add(p);
                }
            }
            return matches;
        }
    }

    public void enabledServices(final Context c) {
        c.startService(new Intent(c, GPSLocationManagerService.class));
        /*
        if(GPSLocationManagerService.isGPSEnabled(c)){
            c.startService(new Intent(c, GPSLocationManagerService.class));
        }
        else{
            GPSLocationManagerService.buildAlertMessageNoGps(c, new AbstractCode() {
                @Override
                public void execute(Object o) {
                    c.startService(new Intent(c, GPSLocationManagerService.class));
                }
            });
        }
        */
    }

    public SharedPreferences getSharedPreferences(Context c) {
        return c.getSharedPreferences("prefereces.onthestreet", Context.MODE_PRIVATE);
    }

    public void setLocationStatus(Context c, boolean status) {
        getSharedPreferences(c).edit().putBoolean("location.running", status).apply();
    }

    public boolean getLocationStatus(Context c) {
        return getSharedPreferences(c).getBoolean("location.running", false);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setUserLocationName(String userLocationName) {
        this.userLocationName = userLocationName;
    }

    public String getUserLocationName() {
        return userLocationName;
    }

    public void updatePlacesDistances() {
        double minDistance = Double.MAX_VALUE;
        if(getLatitude()!= NO_DATA && getLongitude()!= NO_DATA){
            for(Place p :placeList){
                double distanceToTarget = Util.getDistanceFromLatLonInKm(
                        latitude,
                        longitude,
                        p.getLat(),
                        p.getLon()
                );
                if(distanceToTarget <= minDistance){
                    nearestPlace = p;
                    minDistance = distanceToTarget;
                }
                p.setDistance(
                        distanceToTarget
                );
            }
        }
        //notify changes to places list
        if(this.placeListAdapter!=null){
            this.placeListAdapter.notifyDataSetChanged();
        }
    }

    public void setPlaceListAdapter(PlaceListAdapter placeListAdapter) {
        this.placeListAdapter = placeListAdapter;
    }

    public PlaceListAdapter getPlaceListAdapter() {
        return placeListAdapter;
    }

    public Place getNearestPlace() {
        return nearestPlace;
    }

    public void setNearestPlace(Place nearestPlace) {
        this.nearestPlace = nearestPlace;
    }
}
