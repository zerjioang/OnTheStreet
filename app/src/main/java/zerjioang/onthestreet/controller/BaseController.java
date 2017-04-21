package zerjioang.onthestreet.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by .local on 19/04/2017.
 */

public class BaseController extends AbstractBaseController {

    public BaseController(Activity activity) {
        super(activity);
    }

    @Override
    public void showSettings() {

    }

    @Override
    public void showAbout() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("On the street app")
                .setTitle("Alpha version")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
