package zerjioang.onthestreet.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;

import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Place;
import zerjioang.onthestreet.ui.activity.PlaceDetailsActivity;

/**
 * Created by .local on 22/04/2017.
 */

public class PlaceDetailsController extends BaseController {

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;


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
}
