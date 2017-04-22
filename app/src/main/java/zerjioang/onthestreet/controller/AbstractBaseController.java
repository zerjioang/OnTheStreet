package zerjioang.onthestreet.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import zerjioang.onthestreet.ui.activity.settings.SettingsActivity;

/**
 * Created by .local on 19/04/2017.
 */

public abstract class AbstractBaseController {

    private Activity activity;

    public AbstractBaseController(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public View getRootView() {
        return getActivity().findViewById(android.R.id.content);
    }


    public void showSettings() {
        Intent t = new Intent(getActivity(), SettingsActivity.class);
        getActivity().startActivity(t);
    }

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
