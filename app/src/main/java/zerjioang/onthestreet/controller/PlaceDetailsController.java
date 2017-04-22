package zerjioang.onthestreet.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.holder.contact.ContactListAdapter;
import zerjioang.onthestreet.model.holder.placeImage.ImageAdapter;
import zerjioang.onthestreet.model.pojox.Contact;
import zerjioang.onthestreet.model.pojox.Place;
import zerjioang.onthestreet.ui.activity.PlaceDetailsActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by .local on 22/04/2017.
 */

public class PlaceDetailsController extends BaseController {

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private LinearLayoutManager mLinearLayoutManager;
    private Place lastSelectedPlace;


    public PlaceDetailsController(PlaceDetailsActivity placeDetailsActivity) {
        super(placeDetailsActivity);
    }

    public void pickImage() {
        showChooserDialog();
    }

    private void showChooserDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(items[0])) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals(items[1])) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(intent,SELECT_FILE);
                } else if (items[item].equals(items[2])) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Intent getShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        Place p = DataManager.getInstance().getLastViewedPlace();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, p.getName());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "OnTheStreet\nCheck out this awesome place:\n\nPlace:\t"+p.getName()+"\n\tDescription:"+p.getDescription());
        return sharingIntent;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CAMERA:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    //imageview.setImageURI(selectedImage);
                    Log.d(getClass().getSimpleName(), selectedImage.toString());
                }

                break;
            case SELECT_FILE:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    //imageview.setImageURI(selectedImage);
                    Log.d(getClass().getSimpleName(), selectedImage.toString());
                }
                break;
        }
    }

    public void initGridView() {
        GridView gridview = (GridView) getActivity().findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));

        //gridview listener
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initContactList() {
        RecyclerView listviewContact = (RecyclerView) getActivity().findViewById(R.id.listviewContact);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        listviewContact.setLayoutManager(mLinearLayoutManager);

        //read data from file
        ArrayList<Contact> contactList = lastSelectedPlace.getListaContact();

        //create item click listener
        /*
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooserDialog();
            }
        };
        */

        //populate list view
        ContactListAdapter recyclerAdapter = new ContactListAdapter(contactList, null);
        listviewContact.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    public void setLastSelectedPlace(Place lastSelectedPlace) {
        this.lastSelectedPlace = lastSelectedPlace;
    }

    public Place getLastSelectedPlace() {
        return lastSelectedPlace;
    }
}
