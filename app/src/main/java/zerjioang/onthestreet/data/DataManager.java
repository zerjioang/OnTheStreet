package zerjioang.onthestreet.data;

import android.app.Activity;
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
    private int lastViewedPlacePosition;

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
                "La Universidad de Deusto (en euskera Deustuko Unibertsitatea) es una universidad privada regida por la Compañía de Jesús, con dos campus en el distrito de Deusto de la ciudad de Bilbao y en San Sebastián, País Vasco (España), además de una sede en Madrid. Actualmente su rector es José María Guibert Ucín.1 Es la universidad privada española más antigua,2 y una de las más prestigiosas y conocidas.3 Según el estudio \"U-Ranking\" elaborado por el Instituto Valenciano de Investigaciones Económicas (IVIE) y la Fundación BBVA en 2015, la Universidad de Deusto es el líder en el ámbito de la docencia entre las principales universidades españolas."
        ));
        places.add(new Place(
                "Euskal Herriko Unibertsitatea",
                "Leioa, Bilbao",
                "La Universidad del País Vasco (en euskera: Euskal Herriko Unibertsitatea; UPV/EHU) es la universidad pública de la comunidad autónoma del País Vasco (España), articulada en tres campus situados en cada una de las tres provincias de la comunidad: Vizcaya, Guipúzcoa y Álava. Heredera de la Universidad de Bilbao, inicialmente estaba compuesta compuesta por la Facultad de Ciencias Económicas y Empresariales de Sarriko (1955), Medicina (1968) y Ciencias (1968), se le unieron con la Ley General de Educación la Escuela Naútica (1784), la Escuela Universitaria de Estudios Empresariales de Bilbao (1818) y así como las Escuelas Técnicas de Ingenieros, hasta llegar la treintena de centros que la componen en la actualidad."
        ));
        places.add(new Place(
                "Mondragon Unibertsitatea",
                "Mondragon, Gipuzkua",
                "La Universidad de Mondragón (en euskera y oficialmente Mondragon Unibertsitatea) es una universidad privada de iniciativa social sin ánimo de lucro perteneciente a la corporación Mondragón ubicada en Mondragón, Guipúzcoa (España).\n" +
                        "Ha sido declarada de utilidad pública. Su pertenencia a la Corporación Mondragón le permite mantenerse cercana al mundo de la empresa facilitando a sus alumnos el contacto con el mercado laboral. Actualmente tiene en torno a 7.000 alumnos cursando 22 titulaciones de grado,1 13 másteres2 y cursos de experto universitario."
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
}
