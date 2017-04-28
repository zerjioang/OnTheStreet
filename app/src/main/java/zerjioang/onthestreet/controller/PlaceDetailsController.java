package zerjioang.onthestreet.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.base.AbstractBaseController;
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

public class PlaceDetailsController extends AbstractBaseController {

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    private LinearLayoutManager mLinearLayoutManager;
    private Place lastSelectedPlace;
    private ImageAdapter gridAdapter;


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
                    getActivity().startActivityForResult(intent, SELECT_FILE);
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
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "OnTheStreet\nCheck out this awesome place:\n\nPlace:\t" + p.getName() + "\n\tDescription:" + p.getDescription());
        return sharingIntent;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    addImageFromCamera(data);
                    gridAdapter.notifyDataSetChanged();
                    DataManager.getInstance().savePlaceList(getActivity());
                }
                break;
            case SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    addImageFromGallery(data);
                    gridAdapter.notifyDataSetChanged();
                    DataManager.getInstance().savePlaceList(getActivity());
                }
                break;
        }
    }

    private void addImageFromGallery(Intent data) {
        Uri pickedImage = data.getData();
        // Let's read picked image path using content resolver
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
        linkImageToPlace(new File(imagePath));
    }

    private void addImageFromCamera(Intent data) {
         Bitmap photo = (Bitmap) data.getExtras().get("data");
        if (photo != null) {
            //save image on disk
            saveImageOnDisk(photo);
        }
    }

    private void saveImageOnDisk(Bitmap photo) {
        File f = saveBitmapOnStorage(photo);
        linkImageToPlace(f);
    }

    private void linkImageToPlace(File f) {
        this.lastSelectedPlace.addImage(f);
    }

    private File saveBitmapOnStorage(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        String filename = "photo_" + System.currentTimeMillis() + ".png";
        // String temp = null;
        File file = new File(extStorageDirectory, filename);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename);
        }
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public void initGridView() {
        GridView gridview = (GridView) getActivity().findViewById(R.id.gridview);
        lastSelectedPlace = DataManager.getInstance().getLastViewedPlace();
        gridAdapter = new ImageAdapter(getActivity(), lastSelectedPlace);
        gridview.setAdapter(gridAdapter);

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
