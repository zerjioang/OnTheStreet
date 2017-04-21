package zerjioang.onthestreet.controller;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.view.View;

import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Contact;
import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 21/04/2017.
 */

public class NewPlaceController extends BaseController{

    // Declare
    private static final int PICK_CONTACT = 1;
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
                if (resultCode == Activity.RESULT_OK) {
                    String phone = "";
                    Uri contactData = data.getData();
                    Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            phone = phones.getString(phones.getColumnIndex("data1"));
                            System.out.println("number is:" + phone);
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        p.addContact(new Contact(name, "", phone));
                    }
                }
                break;
        }
    }

    public boolean isValidPlace(Place p) {
        if(p.getDescription()==null || p.getDescription().trim().length()==0){
            showError("Description needed");
            return false;
        }
        else if(p.getName()==null || p.getName().trim().length()==0){
            showError("Name needed");
            return false;
        }
        else if(p.getPlace()==null || p.getPlace().trim().length()==0){
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

            }
        });
        pop.show();
    }
}
