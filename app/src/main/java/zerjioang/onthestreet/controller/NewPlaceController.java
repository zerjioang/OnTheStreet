package zerjioang.onthestreet.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import zerjioang.onthestreet.controller.base.AbstractBaseController;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Contact;
import zerjioang.onthestreet.model.pojox.Place;
import zerjioang.onthestreet.ui.activity.NewPlaceActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by .local on 21/04/2017.
 */

public class NewPlaceController extends AbstractBaseController {

    // Declare
    private static final int PICK_CONTACT = 1;
    private static final int PLACE_PICKER_REQUEST = 2;
    private Place p;

    public NewPlaceController(Activity activity) {
        super(activity);
    }

    public void cancel() {
        getActivity().finish();
    }

    public void save(Place p) {
        saveNewPlace(p);
        getActivity().finish();
    }

    private void saveNewPlace(Place p) {
        if(this.p!=null){
            p.setListaContact(this.p.getListaContact());
        }
        this.p = p;
        DataManager.getInstance().addPlace(getActivity(), p);
    }

    public void addContact(Place p) {
        this.p = p;
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        getActivity().startActivityForResult(intent, PICK_CONTACT);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == RESULT_OK) {
                    String phone = "";
                    Uri contactData = data.getData();
                    Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        p.addContact(new Contact(name, "", phone));
                    }
                }
                break;
            case PLACE_PICKER_REQUEST: {
                if (resultCode == RESULT_OK) {
                    com.google.android.gms.location.places.Place place = PlacePicker.getPlace(getActivity(), data);
                    Toast.makeText(getActivity(), "Place selected", Toast.LENGTH_LONG).show();
                    ((NewPlaceActivity)getActivity()).renderPlaceData(place);
                }
            }
        }
    }

    public boolean isValidPlace(Place p) {
        if(p.getName()==null || p.getName().trim().length()==0){
            showError("Name needed");
            return false;
        }
        else if(p.getDescription()==null || p.getDescription().trim().length()==0){
            showError("Description needed");
            return false;
        }
        else if(p.getLocation()==null || p.getLocation().trim().length()==0){
            showError("Location needed");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        Snackbar pop = Snackbar.make(getRootView(), message, Snackbar.LENGTH_LONG);
        pop.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(getClass().getSimpleName(), "clicked");
            }
        });
        pop.show();
    }

    public void updateAndSave(Activity activity, int position, Place p) {
        DataManager.getInstance().replaceAt(activity, position, p);
        getActivity().finish();
    }

    public void selectPlace(){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            getActivity().startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }
}
